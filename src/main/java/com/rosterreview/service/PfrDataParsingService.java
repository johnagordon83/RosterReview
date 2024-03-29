package com.rosterreview.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.rosterreview.data.PersonName;
import com.rosterreview.entity.DraftPick;
import com.rosterreview.entity.Player;
import com.rosterreview.entity.PlayerPosition;
import com.rosterreview.entity.PlayerSeason;
import com.rosterreview.entity.Position;
import com.rosterreview.entity.Team;
import com.rosterreview.utils.WebScrapingUtils;

/**
 * This {@link Service} scrapes, parses, and stores demographic and statistical
 * data for football players from <a href="https://www.pro-football-reference.com">
 * https://www.pro-football-reference.com</a>.
 * <p>
 * To ingest player data, consumers of this service must pass a <code>String</code>
 * representation of the URL for a player's Pro-Football Reference profile to the
 * {@link #parseAndPersistPlayerDataFromUrl(String) parseAndPersistPlayerDataFromUrl}
 * method.
 */
@Service
public class PfrDataParsingService {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TeamService teamService;

    private static final Logger LOG = LoggerFactory.getLogger(PfrDataParsingService.class);

    /**
     * The base URL for the Pro Football Reference website.
     */
    public static final String PFR_URL = "https://www.pro-football-reference.com";

    /**
     * Scrapes, parses, and persists demographic and statistical data for a
     * football player from <a href="https://www.pro-football-reference.com">
     * https://www.pro-football-reference.com</a>.
     * <p>
     * Parsed data is used to construct and persist a {@link Player} entity.
     *
     * @param playerUrl  the URL for the player's PFR profile page
     */
    @Transactional
    public void parseAndPersistPlayerDataFromUrl(String playerUrl) {

        try (WebClient webClient = WebScrapingUtils.getConfiguredWebClient()) {
            // Retrieve the web page corresponding to the passed url
            HtmlPage page = webClient.getPage(playerUrl);

            // The base xpath for player personal data
            String personXPath = "//div[@itemtype='https://schema.org/Person']";

            // Scrape raw data from web page
            String nickname = WebScrapingUtils.getElementText(page,
                    personXPath.concat("/h1[@itemprop='name']"));
            String fullName = WebScrapingUtils.getElementText(page,
                    personXPath.concat("/p[1]/strong[not(contains(text(), 'Position'))]"));
            String positionRawData = WebScrapingUtils.getElementText(page,
                    personXPath.concat("/p[strong[text()='Position']]"));
            String heightRawData = WebScrapingUtils.getElementText(page,
                    personXPath.concat("/p/span[@itemprop='height']"));
            String weightRawData = WebScrapingUtils.getElementText(page,
                    personXPath.concat("/p/span[@itemprop='weight']"));
            String birthDateRawData = WebScrapingUtils.getElementAttribute(page,
                    personXPath.concat("/p/span[@itemprop='birthDate']"), "data-birth");
            String collegeRawData = WebScrapingUtils.getElementText(page,
                    personXPath.concat("/p[strong[text()='College']]"));
            String draftRawData = WebScrapingUtils.getElementText(page,
                    personXPath.concat("/p[strong[text()='Draft']]"));
            String hofYearRawData = WebScrapingUtils.getElementText(page,
                    personXPath.concat("/p[strong[text()='Hall of Fame']]/a[1]"));

            // Parse player's name data
            PersonName personName = parsePlayerName(fullName, nickname);

            String pfrId = playerUrl.substring(playerUrl.lastIndexOf('/') + 1,
                    playerUrl.lastIndexOf('.'));

            Player player = playerService.getPlayerByPfrId(pfrId);

            if (player == null) {
                player = playerService.createPlayer(personName.getFirstName(),
                        personName.getLastName());
            }

            // Set player PFR id
            player.setPfrId(pfrId);

            // Set player name data
            player.setNickname(personName.getNickname());
            player.setFirstName(personName.getFirstName());
            player.setMiddleName(personName.getMiddleName());
            player.setLastName(personName.getLastName());
            player.setSuffix(personName.getSuffix());

            // Parse and set player's college data
            player.setCollege(parseCollege(collegeRawData));

            // Parse and set player's height
            player.setHeight(parseHeight(player.getId(), heightRawData));

            // Parse and set player's weight
            player.setWeight(parseWeight(player.getId(), weightRawData));

            // Parse and set player's birth date.
            player.setBirthDate(parseBirthDate(player.getId(), birthDateRawData));

            // Parse and set player's hall of fame year.
            player.setHofYear(parseHofYear(player.getId(), hofYearRawData));

            // Parse and set player's position data
            parsePlayerPositions(player, positionRawData);

            // Parse and set player's draft pick data
            parseDraftPicks(player, draftRawData);

            // Parse and set player's statistics
            parsePlayerStatistics(page, player);

        } catch (Exception ex) {
            LOG.error("An exception occurred while parsing player data from url: {}.",
                    playerUrl, ex);
        }
    }

    /**
     * Parses player name from data strings.
     *
     * @param fullName  the raw full name string
     * @param nickname  the raw nickname string
     * @return          the parsed player name data
     */
    private PersonName parsePlayerName(String fullName, String nickname) {

        // Remove nicknames (surrounded by parentheses) from full name
        fullName = fullName.replaceAll("\\(.*\\)", "");
        fullName = StringUtils.strip(fullName, " \n\t\u00A0\u2007\u202F");

        // Split names into component parts by white space
        String[] fullNameArr = fullName.split("\\s+");
        String[] nicknameArr = nickname.split("\\s+");
        int fullNameIndex = fullNameArr.length - 1;
        int nicknameIndex = nicknameArr.length - 1;

        // Handle the case where the full name is an empty string
        if (fullNameArr.length == 1 && StringUtils.isBlank(fullNameArr[0])) {
            fullNameArr = nicknameArr;
            fullNameIndex = nicknameIndex;
        }

        // Sorted list of all recognized suffixes
        final String[] suffixes =
            { "I", "I.", "II", "II.", "III", "III.", "IV", "IV.",
              "JR", "JR.", "Jr", "Jr.", "Junior", "SR", "SR.", "Senior", "Sr", "Sr.",
              "V", "V.", "VI", "VI.", "VII", "VII.", "VIII", "VIII." };
        Arrays.sort(suffixes);

        // Parse name suffix
        int suffixIndex = Arrays.binarySearch(suffixes, fullNameArr[fullNameIndex]);
        String suffix = "";
        if (suffixIndex >= 0) {
            suffix = fullNameArr[fullNameIndex];
            fullNameIndex--;
            if (nicknameArr[nicknameIndex].equals(suffix)) {
                nicknameIndex--;
            }
        }

        // Parse the last name
        ArrayList<String> lastNameComponents = new ArrayList<>();
        int suffixCount = BooleanUtils.toInteger(suffixIndex >= 0);

        // handle case where only the last name is present
        if (fullNameArr.length == (suffixCount + 1)) {
            lastNameComponents.add(fullNameArr[0]);
        } else {
            /*
             * Determine if name component is part of the last name
             * Start at the end of the full name, consider each component part
             * of last name if:
             *    1.  It is not the first name component (index = 0) AND
             *    2a. It is the last element in the array that isn't a suffix OR
             *    2b. It is a recognized last name prefix AND this name occurs at
             *        the same index in both the fullName and nickname
             */
            while (fullNameIndex > 0 && nicknameIndex > 0 &&
                    ((fullNameIndex == (fullNameArr.length - 1 - suffixCount)) ||
                    (isLastNamePrefix(fullNameArr[fullNameIndex]) &&
                    (fullNameArr[fullNameIndex].equals(nicknameArr[nicknameIndex]))))) {
                lastNameComponents.add(fullNameArr[fullNameIndex]);
                fullNameIndex--;
                nicknameIndex--;
            }
            Collections.reverse(lastNameComponents);
        }

        String lastName = StringUtils.join(lastNameComponents, ' ');

        // Parse the middle name
        ArrayList<String> middleNameComponents = new ArrayList<>();
        while (fullNameIndex > 0) {
            middleNameComponents.add(fullNameArr[fullNameIndex]);
            fullNameIndex--;
        }

        Collections.reverse(middleNameComponents);
        String middleName = StringUtils.join(middleNameComponents, ' ');

        // Parse the first name
        ArrayList<String> firstNameComponents = new ArrayList<>();
        while (fullNameIndex >= 0) {
            firstNameComponents.add(fullNameArr[fullNameIndex]);
            fullNameIndex--;
        }

        Collections.reverse(firstNameComponents);
        String firstName = StringUtils.join(firstNameComponents, ' ');

        // Parse the nickname
        ArrayList<String> nicknameComponents = new ArrayList<>();
        while (nicknameIndex >= 0) {
            nicknameComponents.add(nicknameArr[nicknameIndex]);
            nicknameIndex--;
        }

        Collections.reverse(nicknameComponents);
        nickname = StringUtils.join(nicknameComponents, ' ');

        return new PersonName(firstName, nickname, middleName, lastName, suffix);
    }

    /**
     * Determines if a <code>String</code> is a recognized last name prefix.
     *
     * @param name  the <code>String</code> to test
     * @return      <code>true</code> if the argument is a last name prefix,
     *              <code>false</code> otherwise
     */
    private boolean isLastNamePrefix(String name) {
        switch (name.toLowerCase()) {
            case "ah" :
            case "dar" :
            case "de" :
            case "der" :
            case "deer" :
            case "del" :
            case "den" :
            case "des" :
            case "dell" :
            case "la" :
            case "little" :
            case "lone" :
            case "randle" :
            case "st." :
            case "van" :
            case "vanden" :
            case "vander" :
            case "vant" :
            case "von" :
            case "vont" :
            return true;
        default :
            return false;
        }
    }

    /**
     * Parses a player's primary college name from a raw data <code>String</code>.
     *
     * @param collegeRawData  the raw college data to be parsed
     * @return                the name of the player's primary college or
     *                        <code>null</code> if the player did not attend
     */
    private String parseCollege(String collegeRawData) {

        String collegeData = collegeRawData.replaceFirst("College: ", "");
        if (collegeData.equals("") || collegeData.contains("none")) {
            return null;
        }

        String[] collegeDataArr = StringUtils.split(collegeData,";|,");

        /*
         * If there are multiple colleges listed, select the one with the
         * 'College Stats' link.
         * If no link is present, default to the last college listed.
         */
        for (String college : collegeDataArr) {
            collegeData = college;
            if (collegeData.contains("(College Stats)")) {
                collegeData = collegeData.replaceAll("\\(College Stats\\)", "");
                break;
            }
        }

        return StringUtils.strip(collegeData, " \n\t\u00A0\u2007\u202F");
    }

    /**
     * Parses a player's height (inches).
     *
     * @param playerId       the id of the player whose height data will be parsed
     * @param heightRawData  the raw height data to be parsed
     * @return               the height of the player in inches
     */
    private Integer parseHeight(String playerId, String heightRawData) {
        Integer height = null;

        try {
            String heightData = StringUtils.trim(heightRawData);
            String[] heightDataArr = StringUtils.split(heightData, "-");
            int feetToInches = Integer.parseInt(heightDataArr[0]) * 12;
            int inches = Integer.parseInt(heightDataArr[1]);
            height = feetToInches + inches;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            LOG.warn("Unable to parse height data ('{}') for player with id: {}.",
                    heightRawData, playerId);
        }

        return height;
    }

    /**
     * Parses a player's weight (lbs).
     *
     * @param playerId       the id of the player whose weight data will be parsed
     * @param weightRawData  the raw weight data to be parsed
     * @return               the weight of the player in lbs
     */
    private Integer parseWeight(String playerId, String weightRawData) {
        Integer weight = null;

        try {
            String weightData = StringUtils.replace(weightRawData, "lb", "");
            weightData = StringUtils.trim(weightData);
            weight = Integer.valueOf(weightData);
        } catch (NumberFormatException nfe) {
            LOG.warn("Unable to parse weight data ('{}') for player with id: {}.",
                    weightRawData, playerId);
        }

        return weight;
    }

    /**
     * Parses a player's birth date.
     *
     * @param playerId          the id of the player whose birth date will be parsed
     * @param birthDateRawData  the raw birth date data to be parsed
     * @return                  the birth date of the player
     */
    private LocalDate parseBirthDate(String playerId, String birthDateRawData) {
        LocalDate birthDate = null;

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            birthDate = LocalDate.parse(birthDateRawData, dateFormatter);
        } catch (DateTimeParseException pe) {
            LOG.warn("Unable to parse birth date ('{}') for player with id: {}.",
                    birthDateRawData, playerId);
        }

        return birthDate;
    }

    /**
     * Parses a player's Hall of Fame year.
     *
     * @param playerId        the id of the player whose Hall of Fame year
     *                        will be parsed
     * @param hofYearRawData  the raw Hall of Fame year data to be parsed
     * @return                the year the player was inducted into the
     *                        Hall of Fame, or <code>null</code> if the player
     *                        has not been inducted
     */
    private Integer parseHofYear(String playerId, String hofYearRawData) {
        Integer hofYear = null;

        try {
            hofYear = Integer.valueOf(StringUtils.trim(hofYearRawData));
        } catch (NumberFormatException nfe) {
            LOG.warn("Unable to parse Hall of Fame year data ('{}') for "
                    + "player with id: {}.", hofYearRawData, playerId);
        }

        return hofYear;
    }

    /**
     * Parses a player's profile position data.
     * <p>
     * Updates the {@link Player} argument with the parsed data.
     *
     * @param player           the player whose position data will be parsed
     * @param positionRawData  the raw position data <code>String</code> to parse
     * @see                    Position
     */
    private void parsePlayerPositions(Player player, String positionRawData) {

        String positionData = positionRawData.replaceFirst("Position: ", "");
        if (positionData.contains("Throws")) {
            positionData = positionData.substring(0, positionData.indexOf("Throws"));
        }
        positionData = positionData.replaceAll(":;/,\\\\", "-").trim();

        Set<Position> positions = new HashSet<>();
        for (String pos : positionData.split("-")) {
            try {
                positions.add(Position.getPositionByAlias(pos));
            } catch (IllegalArgumentException iae) {
                LOG.warn("Unsupported player position found ('{}') for player "
                        + "with id: {}.", pos, player.getId());
            }
        }

        /*
         * Need to limit the number of positions to a maximum of 4.
         * If there are more than 4, replace specific positions with their more
         * generic counterparts.
         */
        if (positions.size() > 4) {
            Set<Position> olPos = new HashSet<>(Arrays.asList(Position.T,
                    Position.G, Position.C));
            generalizePosition(positions, Position.OL, olPos);
        }

        if (positions.size() > 4) {
            Set<Position> dlPos = new HashSet<>(Arrays.asList(Position.DT,
                    Position.NT, Position.DE));
            generalizePosition(positions, Position.DL, dlPos);
        }

        if (positions.size() > 4) {
            Set<Position> lbPos = new HashSet<>(Arrays.asList(Position.ILB,
                    Position.MLB, Position.OLB));
            generalizePosition(positions, Position.LB, lbPos);
        }

        if (positions.size() > 4) {
            Set<Position> sfPos = new HashSet<>(Arrays.asList(Position.FS, Position.SS));
            generalizePosition(positions, Position.S, sfPos);
        }

        if (positions.size() > 4) {
            Set<Position> dbPos = new HashSet<>(Arrays.asList(Position.CB,
                    Position.S, Position.SS, Position.SS));
            generalizePosition(positions, Position.DB, dbPos);
        }

        if (positions.size() > 4) {
            Set<Position> rbPos = new HashSet<>(Arrays.asList(Position.FB));
            generalizePosition(positions, Position.RB, rbPos);
        }

       if (positions.size() > 4) {
            LOG.warn("Unable to consolidate parsed position data ('{}') for "
                    + "player with id: {} to four or fewer positions.",
                    player.getId(), positionRawData);
        }

        /*
         * Add parsed positions to the player object.
         * If there are still more than 4, only 4 will be retained.
         * There are no known cases where position data would be dropped
         * in this manner.
         */
        Set<PlayerPosition> playerPositions = new HashSet<>();
        Iterator<Position> iter = positions.iterator();
        while (iter.hasNext() && playerPositions.size() <= 4) {
            playerPositions.add(new PlayerPosition(player.getId(), iter.next()));
        }

        player.getPositions().retainAll(playerPositions);
        player.getPositions().addAll(playerPositions);
    }

    /**
     * Reduces the number of profile {@link Position Positions} associated
     * with a {@link Player} by replacing specific positions with their more
     * generic counterpart.
     * <p>
     * This function may reduce the size of the positions set by replacing any
     * elements within it that are listed in specificPositions with a single
     * instance of the genericPosition argument.
     *
     * @param positions          the player's profile positions
     * @param genericPosition    a generic position
     * @param specificPositions  the specific positions that should be replaced
     */
     private void generalizePosition(Set<Position> positions, Position genericPosition,
             Set<Position> specificPositions) {

        Set<Position> intersection = new HashSet<>(positions);
        intersection.retainAll(specificPositions);
        if (positions.contains(genericPosition) || intersection.size() > 1) {
            intersection.forEach(position -> positions.remove(position));
            positions.add(genericPosition);
        }
    }

    /**
     * Parses a player's draft pick data.
     * <p>
     * Updates the {@link Player} argument with the parsed
     * {@link DraftPick DraftPicks}.
     *
     * @param player        the player whose draft data will be parsed
     * @param draftRawData  the raw draft pick data <code>String</code>
     */
    private void parseDraftPicks(Player player, String draftRawData) {

        HashSet<DraftPick> draftPicks = new HashSet<>();

        try {
            List<String> nflLocations = teamService.getTeamLocations();
            String[] draftDataArr = StringUtils.split(draftRawData.replaceFirst(
                    "Draft: ", ""),";");

            for (String draftData : draftDataArr) {
                draftData = draftData.trim();

                // Parse the round the player was drafted in.
                String ordinalSuffixes = "(?<=\\d)(rd|st|nd|th)\\b";
                int teamDataEndIndex = draftData.indexOf(" in the");
                int roundStartIndex = teamDataEndIndex + 8;
                int roundEndIndex = draftData.indexOf(" round");
                Integer round = Integer.valueOf(draftData.substring(roundStartIndex,
                        roundEndIndex).replaceAll(ordinalSuffixes, ""));

                // Parse the slot the player was drafted at.
                int slotStartIndex = draftData.indexOf('(') + 1;
                int slotEndIndex = draftData.indexOf(" overall");
                Integer slot = Integer.valueOf(draftData.substring(slotStartIndex,
                        slotEndIndex).replaceAll(ordinalSuffixes, ""));

                // Parse draft year.
                int yearStartIndex = draftData.indexOf("of the") + 7;
                Integer year = Integer.valueOf(draftData.substring(yearStartIndex,
                        yearStartIndex + 4));

                // Determine if draft was supplemental or not.
                boolean supplemental = draftData.contains("Supplemental");

                // Parse league data.
                int leagueStartIndex = yearStartIndex + 5;
                int leagueEndIndex = supplemental ? draftData.lastIndexOf(" Supplemental")
                        : draftData.lastIndexOf(" Draft.");
                String league = draftData.substring(leagueStartIndex, leagueEndIndex);

                // Parse drafting team's name and location.
                String teamData = draftData.substring(0, teamDataEndIndex);
                String[] teamDataArr = teamData.split("\\s+");
                StringJoiner locationJoiner = new StringJoiner(" ");
                StringJoiner teamNameJoiner = new StringJoiner(" ");
                boolean locationFound = false;

                for (String nameSegment : teamDataArr) {
                    if (locationFound) {
                        teamNameJoiner.add(nameSegment);
                    } else {
                        locationJoiner.add(nameSegment);
                        // Determine if current location value is an actual NFL location.
                        locationFound = Collections.binarySearch(nflLocations,
                                locationJoiner.toString()) > -1;
                    }
                }

                String teamName = teamNameJoiner.toString();
                String location = locationJoiner.toString();

                // Get the drafting team from the data store, based on parsed information.
                Team team = teamService.getTeam(year, league, location, teamName);

                if (team != null) {
                    DraftPick newDraftPick = new DraftPick(player.getId(), league,
                            null, null, null, null, null);
                    DraftPick draftPick = player.getDraftPicks().stream()
                            .filter(dp -> dp.equals(newDraftPick)).findFirst()
                            .orElse(newDraftPick);

                    draftPick.setFanchiseId(team.getFranchiseId());
                    draftPick.setYear(year);
                    draftPick.setRound(round);
                    draftPick.setSlot(slot);
                    draftPick.setSupplemental(supplemental);
                    draftPick.setTeam(team);
                    draftPicks.add(draftPick);
                } else {
                    LOG.warn("Could not identify a team that drafted a player "
                            + "with id: {}: (year: {}, league: {}, location: {}, "
                            + "teamName: {}).",
                            player.getId(), year, league, location, teamName);
                }
            }
        } catch (NumberFormatException | IndexOutOfBoundsException |
                NullPointerException ex) {
            LOG.warn("Unable to parse draft data ('{}') for player with id: {}.",
                    draftRawData, player.getId());
        }

        player.getDraftPicks().clear();
        player.getDraftPicks().addAll(draftPicks);
    }

    /**
     * Parses a player's season statistics.
     * Updates the {@link Player} argument with the parsed data.
     *
     * @param page   the html page to parse
     * @param player the player whose data will be parsed
     */
    private void parsePlayerStatistics(HtmlPage page, Player player) {

        List<PlayerSeason> playerStatistics = new ArrayList<>();
        HashMap<Integer, List<Position>> seasonPositions = new HashMap<>();

        String xpath = "//table[contains(@class, 'per_match_toggle') and "
                + "contains(@class, 'stats_table')]";
        List<HtmlTable> statTables = page.getByXPath(xpath);

        for (HtmlTable statTable : statTables) {
            // parse the current table
            int numHeaderRows = statTable.getHeader().getRows().size();
            List<HtmlTableCell> tableHeader = statTable.getHeader().getRows()
                    .get(numHeaderRows - 1).getCells();
            HtmlTableBody tableBody = statTable.getBodies().get(0);

            // Variables for players who switched teams mid-season
            Integer season = null;
            Integer age = null;

            for (HtmlTableRow row : tableBody.getRows()) {
                String yearCellContent = row.getCell(0).getTextContent();
                if (yearCellContent.length() >= 4) {
                    season = Integer.valueOf(yearCellContent.substring(0, 4));
                    age = WebScrapingUtils.parseIntegerWithDefault(row.getCell(1)
                            .getTextContent(), null);
                }

                String teamAbbrev = row.getCell(2).getTextContent();
                /*
                 * If player played for more than 1 team in a season, teamAbbrev
                 * might have a value like '2TM'
                 */
                if (!Character.isDigit(teamAbbrev.charAt(0))) {
                    Team team = teamService.getTeamWithPfrAbbrev(teamAbbrev, season);
                    PlayerSeason searchPs = new PlayerSeason();
                    searchPs.setPlayerId(player.getId());
                    searchPs.setFranchiseId(team.getFranchiseId());
                    searchPs.setSeason(season);
                    searchPs.setSeasonType(PlayerSeason.SeasonType.REGULAR);
                    PlayerSeason playerSeason = player.getStatistics().stream()
                            .filter(ps -> ps.equals(searchPs))
                            .findFirst().orElse(searchPs);

                    /*
                     * Post-season data tables do not include jersey number or
                     * average value data, so copy it from the corresponding
                     * regular season if it exists.
                     */
                    if (statTable.getId().contains("playoffs")) {
                        Integer avgValue = playerSeason.getAvgValue();
                        Integer jerseyNum = playerSeason.getJerseyNumber();
                        searchPs.setSeasonType(PlayerSeason.SeasonType.POST);
                        playerSeason = player.getStatistics().stream()
                                .filter(ps -> ps.equals(searchPs))
                                .findFirst().orElse(searchPs);
                        playerSeason.setJerseyNumber(jerseyNum);
                        playerSeason.setAvgValue(avgValue);
                    }

                    playerSeason.setAge(age);
                    playerSeason.setTeam(team);
                    playerSeason.setProbowl(yearCellContent.contains("*"));
                    playerSeason.setAllPro(yearCellContent.contains("+"));

                    if (!playerStatistics.contains(playerSeason)) {
                        playerStatistics.add(playerSeason);
                        if (!player.getStatistics().contains(playerSeason)) {
                            player.getStatistics().add(playerSeason);
                        }
                    }

                    switch (statTable.getId()) {
                        case "passing":
                        case "passing_playoffs":
                            parsePassingStatistics(playerSeason, seasonPositions,
                                    tableHeader, row);
                            break;
                        case "rushing_and_receiving":
                        case "receiving_and_rushing":
                        case "rushing_and_receiving_playoffs":
                        case "receiving_and_rushing_playoffs":
                            parseRushingAndReceivingStatistics(playerSeason,
                                    seasonPositions, tableHeader, row);
                            break;
                        case "defense":
                        case "defense_playoffs":
                            parseDefensiveStatistics(playerSeason, seasonPositions,
                                    tableHeader, row);
                            break;
                        case "kicking":
                        case "kicking_playoffs":
                            parseKickingStatistics(playerSeason, seasonPositions,
                                    tableHeader, row);
                            break;
                        case "returns":
                        case "returns_playoffs":
                            parseReturnStatistics(playerSeason, seasonPositions,
                                    tableHeader, row);
                            break;
                        case "scoring":
                        case "scoring_playoffs":
                            parseScoringStatistics(playerSeason, seasonPositions,
                                    tableHeader, row);
                            break;
                        case "games_played":
                            parseGamesPlayedStatistics(playerSeason, seasonPositions,
                                    tableHeader, row);
                            break;
                        default: // Do nothing, don't need data from all tables
                    }
                }
            }
        }

        /*
         * Calculate which position should be associated with each season
         * in playerStatistics
         */
        calculateSeasonPositions(seasonPositions, player.getPositions(),
                playerStatistics);

        player.getStatistics().retainAll(playerStatistics);
    }

    /**
     * Updates each {@link PlayerSeason} in a player's career with a primary
     * {@link Position}.
     * <p>
     * Positions are determined by examining:
     * <ul>
     * <li>positions associated with the player's profile</li>
     * <li>positions associated with all seasons in the players career</li>
     * <li>the player's statistics</li>
     * <li>the player's jersey number</li>
     * </ul>
     *
     * @param seasonPositions  a map of the years in which a player played to a
     *                         list of the positions they played that year
     * @param careerPositions  the positions associated with a player's profile
     * @param statistics       a player's seasonal statistics
     */
    private void calculateSeasonPositions(HashMap<Integer, List<Position>> seasonPositions,
            Set<PlayerPosition> careerPositions, List<PlayerSeason> statistics) {

        // Set the position to associate with each season.
        for (PlayerSeason season : statistics) {
            List<Position> positions = seasonPositions.get(season.getSeason());
            /*
             * If only one position is associated with this season choose it.
             * Prefer not to select PR or KR however
             */
            if (positions.size() == 1 && !(positions.get(0).equals(Position.KR) ||
                positions.get(0).equals(Position.PR))) {
                season.setPosition(positions.get(0));
            } else {
                /*
                 * Create a list of all of the season positions across all
                 * years (include duplicates)
                 */
                List<Position> allPositions = seasonPositions.values().stream()
                        .flatMap(List::stream).collect(Collectors.toList());
                /*
                 * Use career positions, and all season positions to deduce what
                 * position should be associated with this season.
                 */
                season.setPosition(consolidate(positions, careerPositions,
                        allPositions, season));
            }
        }
    }

    /**
     * Calculates a single {@link Position} that best describes a player's
     * role during the indicated {@link PlayerSeason}.
     * <p>
     * The position is determined by examining:
     * <ul>
     * <li>positions associated with the player's profile</li>
     * <li>positions associated with all seasons in the players career</li>
     * <li>the player's statistics</li>
     * <li>the player's jersey number</li>
     * </ul>
     *
     * @param seasonPositions  the positions associated with the season argument
     * @param careerPositions  the positions associated with the player's profile
     * @param allPositions     the positions associated with seasons from the
     *                         player's entire career
     * @param season           the season for which a position is being calculated
     * @return                 a position
     */
    private Position consolidate(List<Position> seasonPositions,
            Set<PlayerPosition> careerPositions,List<Position> allPositions,
            PlayerSeason season) {

        // Assign weightings to each position associated with the season
        Map<Position, Double> positionWeightings = new EnumMap<>(Position.class);
        calculatePositionWeightings(seasonPositions, positionWeightings, 1.0, 0.9, 0.8);

        /*
         * Sort the current position weightings largest to smallest (need to
         * determine largest two)
         */
        ArrayList<Double> weightingValues = new ArrayList<>(positionWeightings.values());
        Collections.sort(weightingValues, Collections.reverseOrder());

        /*
         * If any of the following are true, we need to consider career
         * positions and positions associated with other seasons to decide what
         * position should be associated with this season.
         *
         * 1.) There are no positions associated with this season
         * 2.) There is one position associated with this season, but it is
         *     either KR or PR
         * 3.) There are two or more positions associated with this season, but
         *     there is a tie for the highest weighted position.
         */
        if ((seasonPositions.size() < 2) || (weightingValues.size() > 1 &&
                weightingValues.get(0).equals(weightingValues.get(1)))) {
            List<Position> careerPosList = careerPositions.stream()
                    .map(PlayerPosition::getPosition)
                    .collect(Collectors.toList());
            calculatePositionWeightings(careerPosList, positionWeightings, 0.5, 0.4, 0.3);
            calculatePositionWeightings(allPositions, positionWeightings, 0.1, 0.05, 0.025);
        }

        /*
         * Also consider the player's statistics and jersey number for
         * calculating the season position
         */
        calculateStatisticAndJerseyNumberWeightings(season, positionWeightings);

        // Identify the position with the highest calculated weighting and return it
        Entry<Position, Double> maxEntry = Collections.max(positionWeightings.entrySet(),
                (Entry<Position, Double> e1, Entry<Position, Double> e2) -> e1.getValue()
                .compareTo(e2.getValue()));

        return maxEntry.getKey();
    }

    /**
     * Calculates weightings for each {@link Position} associated with a
     * player's season. Positions specified by PFR receive the primary weighting.
     * If the PFR position is a specific position, include it's generic with a
     * lesser weighting.
     * <p>
     * Example: The position {@link Position#FS} is a specific position and would
     * be assigned a primary weighting.  The first level generic for this position
     * would be {@link Position#S} which would recieve a secondaryweighting. This
     * position also has a second level generic {@link Position#DB} which would
     * recieve a tertiary weighting.
     *
     * @param seasonPositions     the positions associated with a given season
     * @param positionWeightings  a mapping of positions to their weightings
     * @param primaryWeighting    the weighting to be assigned to the original PFR
     *                            positions
     * @param secondaryWeighting  the weighting to be applied to first-level generic
     *                            forms of specific positions
     * @param tertiaryWeighting   the weighting to be applied to second-level generic
     *                            forms of specific positions
     */
    private void calculatePositionWeightings(List<Position> seasonPositions,
            Map<Position, Double> positionWeightings, double primaryWeighting,
            double secondaryWeighting, double tertiaryWeighting) {

        for (Position pos : seasonPositions) {
            /*
             * Assign a primary weighting for positions that were listed for the
             * given season
             */
            Double primaryVal = positionWeightings.getOrDefault(pos, 0.0);
            if (pos.equals(Position.KR) || pos.equals(Position.PR)) {
                /*
                 * Assign a lower weighting for KR and PR, as these should
                 * be deemphasized.
                 */
                positionWeightings.put(pos, primaryVal + 0.1);
            } else {
                positionWeightings.put(pos, primaryVal + primaryWeighting);
            }

            /*
             * If position is a specific position, apply a secondary or tertiary
             * weighting for the positions more generic counterparts.
             */
            switch (pos) {
                case HB:
                case FB:
                    Double rbVal = positionWeightings.getOrDefault(Position.RB, 0.0);
                    positionWeightings.put(Position.RB, rbVal + secondaryWeighting);
                    break;
                case T:
                case G:
                case C:
                    Double olVal = positionWeightings.getOrDefault(Position.OL, 0.0);
                    positionWeightings.put(Position.OL, olVal + secondaryWeighting);
                    break;
                case DE:
                case DT:
                case NT:
                    Double dlVal = positionWeightings.getOrDefault(Position.DL, 0.0);
                    positionWeightings.put(Position.DL, dlVal + secondaryWeighting);
                    break;
                case OLB:
                case ILB:
                case MLB:
                    Double lbVal = positionWeightings.getOrDefault(Position.LB, 0.0);
                    positionWeightings.put(Position.LB, lbVal + secondaryWeighting);
                    break;
                case FS:
                case SS:
                    Double sVal = positionWeightings.getOrDefault(Position.S, 0.0);
                    positionWeightings.put(Position.S, sVal + secondaryWeighting);

                    Double dbVal = positionWeightings.getOrDefault(Position.DB, 0.0);
                    positionWeightings.put(Position.DB, dbVal + tertiaryWeighting);
                    break;
                case S:
                    Double dbVal2 = positionWeightings.getOrDefault(Position.DB, 0.0);
                    positionWeightings.put(Position.DB, dbVal2 + secondaryWeighting);
                    break;
                case CB:
                    Double dbVal3 = positionWeightings.getOrDefault(Position.DB, 0.0);
                    positionWeightings.put(Position.DB, dbVal3 + secondaryWeighting);
                    break;
                default: // do nothing
            }
        }
    }

    /**
     * Updates {@link Position} weightings based upon the statistics and jersey
     * number stored within the indicated {@link PlayerSeason}.
     *
     * @param season      the player's season statistics
     * @param weightings  a mapping of positions associated with a player's season
     *                    to their weightings
     */
    private void calculateStatisticAndJerseyNumberWeightings(PlayerSeason season,
            Map<Position, Double> weightings) {

        Integer passAtt = season.getPassAtt();
        Integer jerseyNum = season.getJerseyNumber();
        Integer rushAtt = season.getRushAtt();
        Integer rec = season.getReceptions();
        double jerseyVal;

        /*
         *  Any player with 20 or more pass attempts in a season should be regarded
         *  as a QB
         */
        if (passAtt >= 20) {
            weightings.put(Position.QB, Double.MAX_VALUE);
            return;
        }
        if (weightings.get(Position.QB) != null) {
            jerseyVal = jerseyNum < 20 ? 0.1 : 0;
            weightings.put(Position.QB, weightings.get(Position.QB) + jerseyVal +
                    (passAtt * 0.01));
        }
        if (weightings.get(Position.HB) != null) {
            jerseyVal = jerseyNum >= 20 && jerseyNum < 50 ? 0.1 : 0;
            weightings.put(Position.HB, weightings.get(Position.HB) + jerseyVal +
                    (rushAtt * 0.01) + (rec * 0.005));
        }
        if (weightings.get(Position.FB) != null) {
            jerseyVal = jerseyNum >= 20 && jerseyNum < 50 ? 0.1 : 0;
            weightings.put(Position.FB, weightings.get(Position.FB) + jerseyVal +
                    (rushAtt * 0.01) + (rec * 0.005));
        }
        if (weightings.get(Position.RB) != null) {
            jerseyVal = jerseyNum >= 20 && jerseyNum < 50 ? 0.1 : 0;
            weightings.put(Position.RB, weightings.get(Position.RB) + jerseyVal +
                    (rushAtt * 0.01) + (rec * 0.005));
        }
        if (weightings.get(Position.WR) != null) {
            jerseyVal = (jerseyNum >= 80 && jerseyNum < 90) ||
                    (jerseyNum >= 10 && jerseyNum < 20) ? 0.1 : 0;
            weightings.put(Position.WR, weightings.get(Position.WR)
                    + jerseyVal + (rec * 0.01));
        }
        if (weightings.get(Position.TE) != null) {
            jerseyVal = (jerseyNum >= 80 && jerseyNum < 90) ||
                    (jerseyNum >= 40 && jerseyNum < 50) ? 0.1 : 0;
            weightings.put(Position.TE, weightings.get(Position.TE)
                    + jerseyVal + (rec * 0.01));
        }
        if (weightings.get(Position.OL) != null) {
            jerseyVal = jerseyNum >= 50 && jerseyNum < 80 ? 0.1 : 0;
            weightings.put(Position.OL, weightings.get(Position.OL) + jerseyVal);
        }
        if (weightings.get(Position.T) != null) {
            jerseyVal = jerseyNum >= 50 && jerseyNum < 80 ? 0.1 : 0;
            weightings.put(Position.T, weightings.get(Position.T) + jerseyVal);
        }
        if (weightings.get(Position.G) != null) {
            jerseyVal = jerseyNum >= 50 && jerseyNum < 80 ? 0.1 : 0;
            weightings.put(Position.G, weightings.get(Position.G) + jerseyVal);
        }
        if (weightings.get(Position.C) != null) {
            jerseyVal = jerseyNum >= 50 && jerseyNum < 80 ? 0.1 : 0;
            weightings.put(Position.C, weightings.get(Position.C) + jerseyVal);
        }
        if (weightings.get(Position.DL) != null) {
            jerseyVal = (jerseyNum >= 50 && jerseyNum < 60) ||
                    (jerseyNum >= 70 && jerseyNum < 80) || (jerseyNum >= 90) ? 0.1 : 0;
            weightings.put(Position.DL, weightings.get(Position.DL) + jerseyVal);
        }
        if (weightings.get(Position.DE) != null) {
            jerseyVal = (jerseyNum >= 50 && jerseyNum < 60) ||
                    (jerseyNum >= 70 && jerseyNum < 80) || (jerseyNum >= 90) ? 0.1 : 0;
            weightings.put(Position.DE, weightings.get(Position.DE) + jerseyVal);
        }
        if (weightings.get(Position.DT) != null) {
            jerseyVal = (jerseyNum >= 70 && jerseyNum < 80) ||
                    (jerseyNum >= 90) ? 0.1 : 0;
            weightings.put(Position.DT, weightings.get(Position.DT) + jerseyVal);
        }
        if (weightings.get(Position.NT) != null) {
            jerseyVal = (jerseyNum >= 70 && jerseyNum < 80) ||
                    (jerseyNum >= 90) ? 0.1 : 0;
            weightings.put(Position.NT, weightings.get(Position.NT) + jerseyVal);
        }
        if (weightings.get(Position.LB) != null) {
            jerseyVal = (jerseyNum >= 40 && jerseyNum < 60) ||
                    (jerseyNum >= 90) ? 0.1 : 0;
            weightings.put(Position.LB, weightings.get(Position.LB) + jerseyVal);
        }
        if (weightings.get(Position.ILB) != null) {
            jerseyVal = (jerseyNum >= 40 && jerseyNum < 60) ||
                    (jerseyNum >= 90) ? 0.1 : 0;
            weightings.put(Position.ILB, weightings.get(Position.ILB) + jerseyVal);
        }
        if (weightings.get(Position.MLB) != null) {
            jerseyVal = (jerseyNum >= 40 && jerseyNum < 60) ||
                    (jerseyNum >= 90) ? 0.1 : 0;
            weightings.put(Position.MLB, weightings.get(Position.MLB) + jerseyVal);
        }
        if (weightings.get(Position.OLB) != null) {
            jerseyVal = (jerseyNum >= 40 && jerseyNum < 60) ||
                    (jerseyNum >= 90) ? 0.1 : 0;
            weightings.put(Position.OLB, weightings.get(Position.OLB) + jerseyVal);
        }
        if (weightings.get(Position.DB) != null) {
            jerseyVal = (jerseyNum >= 20 && jerseyNum < 50) ? 0.1 : 0;
            weightings.put(Position.DB, weightings.get(Position.DB) + jerseyVal);
        }
        if (weightings.get(Position.CB) != null) {
            jerseyVal = (jerseyNum >= 20 && jerseyNum < 50) ? 0.1 : 0;
            weightings.put(Position.CB, weightings.get(Position.CB) + jerseyVal);
        }
        if (weightings.get(Position.S) != null) {
            jerseyVal = (jerseyNum >= 20 && jerseyNum < 50) ? 0.1 : 0;
            weightings.put(Position.S, weightings.get(Position.S) + jerseyVal);
        }
        if (weightings.get(Position.FS) != null) {
            jerseyVal = (jerseyNum >= 20 && jerseyNum < 50) ? 0.1 : 0;
            weightings.put(Position.FS, weightings.get(Position.FS) + jerseyVal);
        }
        if (weightings.get(Position.SS) != null) {
            jerseyVal = (jerseyNum >= 20 && jerseyNum < 50) ? 0.1 : 0;
            weightings.put(Position.SS, weightings.get(Position.SS) + jerseyVal);
        }
    }

    /**
     * Parses {@link Position} data from a raw data <code>String</code>.
     *
     * @param rawPositionData  raw position data
     * @return                 the parsed positions
     */
    private Set<Position> parsePositions(String rawPositionData) {
        String[] yearData = rawPositionData.split("[///;:,]");
        Set<Position> positions = new HashSet<>();

        for (String pos : yearData) {
            try {
                if (StringUtils.isNotBlank(pos)) {
                    /*
                     *  Special case for 'LS' which means 'Left Saftey' in pre-1970
                     *  data, but 'Long Snapper' in modern data. Note that for long
                     *  snapper season data, the position is always blank.  Therefore,
                     *  if 'LS' appears here, assume 'Left Safety'.
                     */
                    pos = (pos.equals("LS")) ? "S" : pos;
                    positions.add(Position.getPositionByAlias(pos.toUpperCase()));
                }
            } catch (IllegalArgumentException iae) {
                LOG.warn(iae.getMessage(), iae);
            }
        }

        return positions;
    }

    /**
     * Parses a player's passing statistics and stores them in the
     * given {@link PlayerSeason}.
     *
     * @param playerSeason     the season for which statistical data is being parsed
     * @param seasonPositions  a mapping of seasons to positions
     * @param tableHeader      the table header containing the column names
     * @param row              the table row containing the statistics to be parsed
     */
    private void parsePassingStatistics(PlayerSeason playerSeason,
            Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) {

        for (int j = 3; j < row.getCells().size(); j++) {
            String cellData = row.getCell(j).getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(),
                        k -> new ArrayList<Position>()).addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_cmp": playerSeason.setPassComp(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_att": playerSeason.setPassAtt(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_yds": playerSeason.setPassYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_td": playerSeason.setPassTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_int": playerSeason.setPassInts(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_long": playerSeason.setPassLong(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_rating": playerSeason.setPassRating(
                        WebScrapingUtils.parseDoubleWithDefault(cellData, 0.0));
                    break;
                case "pass_sacked": playerSeason.setSacked(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_sacked_yds": playerSeason.setSackedYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "av": playerSeason.setAvgValue(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                default : // Do nothing.
            }
        }
    }

    /**
     * Parses a player's rushing and receiving statistics, and stores them in
     * the given {@link PlayerSeason}.
     *
     * @param playerSeason     the season for which statistical data is being parsed
     * @param seasonPositions  a mapping of seasons to positions
     * @param tableHeader      the table header containing the column names
     * @param row              the table row containing the statistics to be parsed
     */
    private void parseRushingAndReceivingStatistics(PlayerSeason playerSeason,
            Map<Integer, List<Position>> seasonPositions, List<HtmlTableCell> tableHeader,
            HtmlTableRow row) {

        for (int j = 3; j < row.getCells().size(); j++) {
            String cellData = row.getCell(j).getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(),
                        k -> new ArrayList<Position>()).addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rush_att": playerSeason.setRushAtt(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rush_yds": playerSeason.setRushYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rush_td": playerSeason.setRushTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rush_long": playerSeason.setRushLong(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles": playerSeason.setFumbles(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "targets": playerSeason.setTargets(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rec": playerSeason.setReceptions(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rec_yds": playerSeason.setRecYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rec_td": playerSeason.setRecTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rec_long": playerSeason.setRecLong(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "av": playerSeason.setAvgValue(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                default : // Do nothing.
            }
        }
    }

    /**
     * Parses a player's defensive statistics and stores them in the
     * given {@link PlayerSeason}.
     *
     * @param playerSeason     the season for which statistical data is being parsed
     * @param seasonPositions  a mapping of seasons to positions
     * @param tableHeader      the table header containing the column names
     * @param row              the table row containing the statistics to be parsed
     */
    private void parseDefensiveStatistics(PlayerSeason playerSeason, Map<Integer,
            List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) {

        for (int j = 3; j < row.getCells().size(); j++) {
            String cellData = row.getCell(j).getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(),
                        k -> new ArrayList<Position>()).addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "def_int": playerSeason.setInterceptions(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "def_int_yds": playerSeason.setIntYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "def_int_td": playerSeason.setIntTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "def_int_long": playerSeason.setIntLong(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_defended": playerSeason.setPassDef(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles_forced": playerSeason.setFumForced(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles": playerSeason.setFumbles(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles_rec": playerSeason.setFumRec(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles_rec_yds": playerSeason.setFumRecYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles_rec_td": playerSeason.setFumRecTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "sacks": playerSeason.setSacks(
                        WebScrapingUtils.parseDoubleWithDefault(cellData, 0.0));
                    break;
                case "tackles_solo": playerSeason.setTackles(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "tackles_assists": playerSeason.setAssists(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "safety_md": playerSeason.setSafeties(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "av": playerSeason.setAvgValue(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                default : // Do nothing.
            }
        }
    }

    /**
     * Parses a player's kicking statistics and stores them in the
     * given {@link PlayerSeason}.
     *
     * @param playerSeason     the season for which statistical data is being parsed
     * @param seasonPositions  a mapping of seasons to positions
     * @param tableHeader      the table header containing the column names
     * @param row              the table row containing the statistics to be parsed
     */
    private void parseKickingStatistics(PlayerSeason playerSeason, Map<Integer,
            List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) {

        for (int j = 3; j < row.getCells().size(); j++) {
            String cellData = row.getCell(j).getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(),
                        k -> new ArrayList<Position>()).addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga1": playerSeason.setFgaTeens(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm1": playerSeason.setFgmTeens(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga2": playerSeason.setFgaTwenties(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm2": playerSeason.setFgmTwenties(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga3": playerSeason.setFgaThirties(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm3": playerSeason.setFgmThirties(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga4": playerSeason.setFgaFourties(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm4": playerSeason.setFgmFourties(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga5": playerSeason.setFgaFiftyPlus(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm5": playerSeason.setFgmFiftyPlus(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga": playerSeason.setFgaTotal(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm": playerSeason.setFgmTotal(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fg_long": playerSeason.setFgLong(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "xpa": playerSeason.setXpa(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "xpm": playerSeason.setXpm(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt": playerSeason.setPunts(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_yds": playerSeason.setPuntYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_long": playerSeason.setPuntLong(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_blocked": playerSeason.setPuntBlocked(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "av": playerSeason.setAvgValue(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                default : // Do nothing.
            }
        }
    }

    /**
     * Parses a player's return statistics and stores them in the
     * given {@link PlayerSeason}.
     *
     * @param playerSeason     the season for which statistical data is being parsed
     * @param seasonPositions  a mapping of seasons to positions
     * @param tableHeader      the table header containing the column names
     * @param row              the table row containing the statistics to be parsed
     */
    private void parseReturnStatistics(PlayerSeason playerSeason, Map<Integer,
            List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) {

        for (int j = 3; j < row.getCells().size(); j++) {
            String cellData = row.getCell(j).getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(),
                        k -> new ArrayList<Position>()).addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_ret": playerSeason.setPuntRet(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_ret_yds": playerSeason.setPuntRetYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_ret_td": playerSeason.setPuntRetTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_ret_long": playerSeason.setPuntRetLong(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "kick_ret": playerSeason.setKickRet(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "kick_ret_yds": playerSeason.setKickRetYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "kick_ret_td": playerSeason.setKickRetTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "kick_ret_long": playerSeason.setKickRetLong(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                default : // Do nothing.
            }
        }
    }

    /**
     * Parses a player's scoring statistics and stores them in the
     * given {@link PlayerSeason}.
     *
     * @param playerSeason     the season for which statistical data is being parsed
     * @param seasonPositions  a mapping of seasons to positions
     * @param tableHeader      the table header containing the column names
     * @param row              the table row containing the statistics to be parsed
     */
    private void parseScoringStatistics(PlayerSeason playerSeason,
            Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) {
        for (int j = 3; j < row.getCells().size(); j++) {
            String cellData = row.getCell(j).getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(),
                        k -> new ArrayList<Position>()).addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rushtd": playerSeason.setRushTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rectd": playerSeason.setRecTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "prtd": playerSeason.setPuntRetTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "krtd": playerSeason.setKickRetTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "frtd": playerSeason.setFumRecTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "ditd": playerSeason.setIntTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "otd": playerSeason.setOtherTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "alltd": playerSeason.setAllTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "two_pt_md": playerSeason.setTwoPointConv(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "xpm": playerSeason.setXpm(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "xpa": playerSeason.setXpa(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm": playerSeason.setFgmTotal(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga": playerSeason.setFgaTotal(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "safety_md": playerSeason.setSafeties(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                default : // Do nothing.
            }
        }
    }

    /**
     * Parses a player's games played statistics and stores them in the
     * given {@link PlayerSeason}.
     *
     * @param playerSeason     the season for which statistical data is being parsed
     * @param seasonPositions  a mapping of seasons to positions
     * @param tableHeader      the table header containing the column names
     * @param row              the table row containing the statistics to be parsed
     */
    private void parseGamesPlayedStatistics(PlayerSeason playerSeason,
            Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) {
        for (int j = 3; j < row.getCells().size(); j++) {
            String cellData = row.getCell(j).getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(),
                        k -> new ArrayList<Position>()).addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "av": playerSeason.setAvgValue(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                default : // Do nothing.
            }
        }
    }
}