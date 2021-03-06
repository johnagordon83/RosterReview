package com.rosterreview.data;

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
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.rosterreview.entity.DraftPick;
import com.rosterreview.entity.Player;
import com.rosterreview.entity.PlayerPosition;
import com.rosterreview.entity.PlayerSeason;
import com.rosterreview.entity.Position;
import com.rosterreview.entity.Team;
import com.rosterreview.service.PlayerService;
import com.rosterreview.service.TeamService;
import com.rosterreview.utils.RosterReviewException;
import com.rosterreview.utils.WebScrapingUtils;

/**
 * Scrapes player data from https://www.pro-football-reference.com.
 */
@Component
public class PfrDataUpdater {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TeamService teamService;

    private static Logger log = LoggerFactory.getLogger(PfrDataUpdater.class);

    public static final String PFR_URL = "https://www.pro-football-reference.com";

    /**
     * Parse and persist player data from the player's PFR page at the passed url.
     *
     * @param playerUrl  The PFR url for the player's data
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
            String pfrId = playerUrl.substring(playerUrl.lastIndexOf('/') + 1, playerUrl.lastIndexOf('.'));
            Player player = playerService.getPlayerByPfrId(pfrId);

            if (player == null) {
                player = new Player();
                String id = playerService.generateNewPlayerId(personName.getFirstName(), personName.getLastName());
                player.setId(id);
                player.setPfrId(pfrId);
                player.setPositions(new HashSet<PlayerPosition>());
                player.setDraftPicks(new HashSet<DraftPick>());
                player.setStatistics(new ArrayList<PlayerSeason>());
                playerService.persistPlayer(player);
            }

            // Set player name data
            player.setNickname(personName.getNickname());
            player.setFirstName(personName.getFirstName());
            player.setMiddleName(personName.getMiddleName());
            player.setLastName(personName.getLastName());
            player.setSuffix(personName.getSuffix());

            // Parse and set player's position data
            parsePlayerPositions(player, positionRawData);

            // Parse and set player's draft pick data
            parseDraftPicks(player, draftRawData);

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

            // Parse and set player's statistics
            parsePlayerStatistics(page, player);

        } catch (Exception ex) {
            log.error("An exception occurred while parsing player data from url: {}.", playerUrl, ex);
        }
    }

    /**
     * Parse raw player name data strings.
     *
     * @param fullName  The raw full name string
     * @param nickname  The raw nickname string
     * @return A PersonName containing the parsed name data
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
            /* Determine if name component is part of the last name
             * Start at the end of the full name, consider each component part of last name if:
             *    1.  It is not the first name component (index = 0) AND
             *    2a. It is the last name component that isn't a suffix OR
             *    2b. It is a recognized last name prefix AND this name occurs at the same index
             *        in both the fullName and nickname
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
     * Determine if argument is a recognized last name prefix
     *
     * @param name The name to test
     * @return true if the argument is a last name prefix, false otherwise
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
     * Parse player profile position data
     *
     * @param player  The player whose position data should be parsed
     * @param positionRawData  The raw position data string to parse
     */
    private void parsePlayerPositions(Player player, String positionRawData) {

        String positionData = positionRawData.replaceFirst("Position: ", "");
        if (positionData.contains("Throws")) {
            positionData = positionData.substring(0, positionData.indexOf("Throws"));
        }
        positionData = positionData.replaceAll(":;/,\\\\", "-");
        positionData = positionData.trim();

        Set<Position> positions = new HashSet<>();
        for (String pos : positionData.split("-")) {
            try {
                positions.add(Position.getPositionByAlias(pos));
            } catch (IllegalArgumentException iae) {
                log.warn("Unsupported player position found ('{}') for player with id: {}.", pos, player.getId());
            }
        }

        // Need to limit the number of positions to a maximum of 4.
        // If there are more than 4, replace specific positions with their more generic counterparts.
        if (positions.size() > 4) {
            Set<Position> olPos = new HashSet<>(Arrays.asList(Position.T, Position.G, Position.C));
            generalizePosition(positions, Position.OL, olPos);
        }

        if (positions.size() > 4) {
            Set<Position> dlPos = new HashSet<>(Arrays.asList(Position.DT, Position.NT, Position.DE));
            generalizePosition(positions, Position.DL, dlPos);
        }

        if (positions.size() > 4) {
            Set<Position> lbPos = new HashSet<>(Arrays.asList(Position.ILB, Position.MLB, Position.OLB));
            generalizePosition(positions, Position.LB, lbPos);
        }

        if (positions.size() > 4) {
            Set<Position> sfPos = new HashSet<>(Arrays.asList(Position.FS, Position.SS));
            generalizePosition(positions, Position.S, sfPos);
        }

        if (positions.size() > 4) {
            Set<Position> dbPos = new HashSet<>(Arrays.asList(Position.CB, Position.S, Position.SS, Position.SS));
            generalizePosition(positions, Position.DB, dbPos);
        }

        if (positions.size() > 4) {
            Set<Position> rbPos = new HashSet<>(Arrays.asList(Position.FB));
            generalizePosition(positions, Position.RB, rbPos);
        }

       if (positions.size() > 4) {
            log.warn("Unable to consolidate parsed position data ('{}') for player with id: {} "
                    + "to four or fewer positions.",
                    player.getId(), positionRawData);
        }

        // Add parsed positions to the player object.  If there are still more than 4, only 4 will be retained.
        // There are no known cases where position data would be dropped in this manner
        Set<PlayerPosition> playerPositions = new HashSet<>();
        Iterator<Position> iter = positions.iterator();
        while (iter.hasNext() && playerPositions.size() <= 4) {
            playerPositions.add(new PlayerPosition(player.getId(), iter.next()));
        }

        player.getPositions().retainAll(playerPositions);
        player.getPositions().addAll(playerPositions);
    }

    /**
     * Reduce the number of profile positions associated with a player by replacing specific positions with
     * their more generic counterpart.
     *
     * This function may reduce the size of the positions set by replacing any Positions specified in
     * specificPositions that are found in the positions set with a single instance of the genericPosition
     * argument.
     *
     * @param positions  A Set of a player's profile positions
     * @param genericPosition  The position that specific positions should be generalized to
     * @param specificPositions  A set of specific positions that should be generalized to genericPosition
     */
     private void generalizePosition(Set<Position> positions, Position genericPosition,
             Set<Position> specificPositions) {

        Set<Position> intersection = new HashSet<>(positions);
        intersection.retainAll(specificPositions);
        if (positions.contains(genericPosition) || intersection.size() > 1) {
            for (Position position : intersection) {
                positions.remove(position);
            }
            positions.add(genericPosition);
        }
    }

    /**
     * Parse player draft picks
     *
     * @param player  The player whose draft data should be parsed
     * @param draftRawData  The raw draft pick data string to be parsed
     */
    private void parseDraftPicks(Player player, String draftRawData) {

        if (StringUtils.isBlank(draftRawData)) return;

        try {
            ArrayList<DraftPick> newPicks = new ArrayList<>();
            List<String> nflLocations = teamService.getNflLocations();
            String[] draftDataArr = draftRawData.replaceFirst("Draft: ", "").split(";");

            for (String draftData : draftDataArr) {
                draftData = draftData.trim();

                // Parse the round the player was drafted in.
                String ordinalSuffixes = "(?<=\\d)(rd|st|nd|th)\\b";
                int teamDataEndIndex = draftData.indexOf(" in the");
                int roundStartIndex = teamDataEndIndex + 8;
                int roundEndIndex = draftData.indexOf(" round");
                Integer round = Integer.valueOf(draftData.substring(roundStartIndex, roundEndIndex)
                        .replaceAll(ordinalSuffixes, ""));

                // Parse the slot the player was drafted at.
                int slotStartIndex = draftData.indexOf('(') + 1;
                int slotEndIndex = draftData.indexOf(" overall");
                Integer slot = Integer.valueOf(draftData.substring(slotStartIndex, slotEndIndex)
                        .replaceAll(ordinalSuffixes, ""));

                // Parse draft year.
                int yearStartIndex = draftData.indexOf("of the") + 7;
                Integer year = Integer.valueOf(draftData.substring(yearStartIndex, yearStartIndex + 4));

                // Determine if draft was supplemental or not.
                boolean supplemental = draftData.contains("Supplemental");

                // Parse league data.
                int leagueStartIndex = yearStartIndex + 5;
                int leagueEndIndex = supplemental ? draftData.lastIndexOf(" Supplemental") :
                    draftData.lastIndexOf(" Draft.");
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
                        locationFound = Collections.binarySearch(nflLocations, locationJoiner.toString()) > -1;
                    }
                }

                String teamName = teamNameJoiner.toString();
                String location = locationJoiner.toString();

                // Get the drafting team from the data store, based on parsed information.
                Team team = teamService.getTeam(year, league, location, teamName);

                if (team != null) {
                    DraftPick newDraftPick = new DraftPick(player.getId(), league, null, null, null, null, null);
                    DraftPick draftPick = player.getDraftPicks().stream()
                            .filter(dp -> dp.equals(newDraftPick)).findFirst().orElse(newDraftPick);

                    draftPick.setFanchiseId(team.getFranchiseId());
                    draftPick.setYear(year);
                    draftPick.setRound(round);
                    draftPick.setSlot(slot);
                    draftPick.setSupplemental(supplemental);
                    draftPick.setTeam(team);
                    newPicks.add(draftPick);
                } else {
                    log.warn("Could not identify a team that drafted a player with id: {}: "
                            + "(year: {}, league: {}, location: {}, teamName: {}).",
                            player.getId(), year, league, location, teamName);
                }
            }

            player.getDraftPicks().retainAll(newPicks);
            player.getDraftPicks().addAll(newPicks);

        } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException ex) {
            log.warn("Unable to parse draft data ('{}') for player with id: {}.", draftRawData, player.getId());
        }
    }

    /**
     * Parse player's college name.
     *
     * @param collegeRawData  The raw college data to be parsed.
     * @return  The name of the primary college the player played for
     */
    private String parseCollege(String collegeRawData) {

        String college = null;
        if (StringUtils.isBlank(collegeRawData)) {
            return college;
        }

        String collegeData = collegeRawData.replaceFirst("College: ", "");
        String[] collegeDataArr = collegeData.split(";|,");

        // If there are multiple colleges listed, select the one with the 'College Stats' link.
        // If not link present, default to the last college listed.
        for (int i = 0; i < collegeDataArr.length; i++) {
            collegeData = collegeDataArr[i];
            if (collegeDataArr[i].contains("(College Stats)")) {
                collegeData = collegeData.replaceAll("\\(College Stats\\)", "");
                break;
            }
        }

        collegeData = collegeData.replace("none", "");
        college = StringUtils.strip(collegeData, " \n\t\u00A0\u2007\u202F");

        return college;
    }

    /**
     * Parse player's height (in.).
     *
     * @param playerId  The id of the player whose height data should be parsed
     * @param heightRawData  The raw height data to be parsed.
     * @return  The height of the player in inches
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
            log.warn("Unable to parse height data ('{}') for player with id: {}.", heightRawData, playerId);
        }

        return height;
    }

    /**
     * Parse player's weight (lbs.)
     *
     * @param playerId  The id of the player whose weight data should be parsed
     * @param heightRawData  The raw weight data to be parsed.
     * @return  The weight of the player in lbs
     */
    private Integer parseWeight(String playerId, String weightRawData) {
        Integer weight = null;

        try {
            String weightData = StringUtils.replace(weightRawData, "lb", "");
            weightData = StringUtils.trim(weightData);
            weight = Integer.valueOf(weightData);
        } catch (NumberFormatException nfe) {
            log.warn("Unable to parse weight data ('{}') for player with id: {}.", weightRawData, playerId);
        }

        return weight;
    }

    /**
     * Parse player's birth date
     *
     * @param playerId  The id of the player whose birth date should be parsed
     * @param heightRawData  The raw birth date data to be parsed.
     * @return  The birth date of the player
     */
    private LocalDate parseBirthDate(String playerId, String birthDateRawData) {
        LocalDate birthDate = null;

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            birthDate = LocalDate.parse(birthDateRawData, dateFormatter);
        } catch (DateTimeParseException pe) {
            log.warn("Unable to parse birth date ('{}') for player with id: {}.", birthDateRawData, playerId);
        }

        return birthDate;
    }

    /**
     * Parse player's Hall of Fame year
     *
     * @param playerId  The id of the player whose Hall of Fame year should be parsed
     * @param heightRawData  The raw Hall of Fame year data to be parsed.
     * @return  The year the player was inducted into the Hall of Fame (or null)
     */
    private Integer parseHofYear(String playerId, String hofYearRawData) {
        Integer hofYear = null;

        try {
            hofYear = Integer.valueOf(StringUtils.trim(hofYearRawData));
        } catch (NumberFormatException nfe) {
            log.warn("Unable to parse Hall of Fame year data ('{}') for player with id: {}.", hofYearRawData, playerId);
        }

        return hofYear;
    }

    /**
     * Parse a player's season statistics.
     *
     * @param page The HtmlPage to parse
     * @param player The Player whose data will be parsed
     * @throws RosterReviewException
     */
    private void parsePlayerStatistics(HtmlPage page, Player player) throws RosterReviewException {

        Set<PlayerSeason> latestStats = new HashSet<>();
        HashMap<Integer, List<Position>> seasonPositions = new HashMap<>();

        String xpath = "//table[contains(@class, 'per_match_toggle') and contains(@class, 'stats_table')]";
        List<HtmlTable> statTables = page.getByXPath(xpath);

        for (HtmlTable playerStatistics : statTables) {
            // parse the current table
            int numHeaderRows = playerStatistics.getHeader().getRows().size();
            List<HtmlTableCell> tableHeader = playerStatistics.getHeader().getRows().get(numHeaderRows - 1).getCells();
            HtmlTableBody tableBody = playerStatistics.getBodies().get(0);

            // Variables for players who switched teams mid-season
            Integer season = null;
            Integer age = null;

            for (int i = 0; i < tableBody.getRows().size(); i++) {
                HtmlTableRow row = tableBody.getRows().get(i);
                String yearCellContent = row.getCell(0).getTextContent();
                if (yearCellContent.length() >= 4) {
                    season = Integer.valueOf(yearCellContent.substring(0, 4));
                    age = WebScrapingUtils.parseIntegerWithDefault(row.getCell(1).getTextContent(), null);
                }

                String teamAbbrev = row.getCell(2).getTextContent();
                // If player played for more than 1 team in a season, teamAbbrev might have a value like '2TM'
                if (!Character.isDigit(teamAbbrev.charAt(0))) {
                    Team team = teamService.getTeamWithPfrAbbrev(teamAbbrev, season);
                    PlayerSeason searchPs = new PlayerSeason();
                    searchPs.setPlayerId(player.getId());
                    searchPs.setFranchiseId(team.getFranchiseId());
                    searchPs.setSeason(season);
                    PlayerSeason playerSeason = latestStats.stream()
                            .filter(ps -> ps.equals(searchPs)).findFirst().orElse(searchPs);

                    playerSeason.setAge(age);
                    playerSeason.setTeam(team);
                    playerSeason.setProbowl(yearCellContent.contains("*"));
                    playerSeason.setAllPro(yearCellContent.contains("+"));
                    latestStats.add(playerSeason);

                    switch (playerStatistics.getId()) {
                        case "passing":
                            parsePassingStatistics(playerSeason, seasonPositions, tableHeader, row);
                            break;
                        case "passing_playoffs":
                            parsePostseasonPassingStatistics(playerSeason, seasonPositions, tableHeader, row);
                            break;
                        case "rushing_and_receiving":
                        case "receiving_and_rushing":
                            parseRushingAndReceivingStatistics(playerSeason, seasonPositions, tableHeader, row);
                            break;
                        case "rushing_and_receiving_playoffs":
                        case "receiving_and_rushing_playoffs":
                            parsePostseasonRushingAndReceivingStatistics(playerSeason, seasonPositions, tableHeader, row);
                            break;
                        case "defense":
                            parseDefensiveStatistics(playerSeason, seasonPositions, tableHeader, row);
                            break;
                        case "defense_playoffs":
                            parsePostseasonDefensiveStatistics(playerSeason, seasonPositions, tableHeader, row);
                            break;
                        case "kicking":
                            parseKickingStatistics(playerSeason, seasonPositions, tableHeader, row);
                            break;
                        case "kicking_playoffs":
                            parsePostseasonKickingStatistics(playerSeason, seasonPositions, tableHeader, row);
                            break;
                        case "returns":
                            parseReturnStatistics(playerSeason, seasonPositions, tableHeader, row);
                            break;
                        case "returns_playoffs":
                            parsePostseasonReturnStatistics(playerSeason, seasonPositions, tableHeader, row);
                            break;
                        case "scoring":
                            parseScoringStatistics(playerSeason, seasonPositions, tableHeader, row);
                            break;
                        case "scoring_playoffs":
                            parsePostseasonScoringStatistics(playerSeason, tableHeader, row);
                            break;
                        default: // Do nothing, don't need data from all tables
                    }
                }
            }
        }

        calculateSeasonPositions(seasonPositions, player.getPositions(), latestStats);
        player.getStatistics().retainAll(latestStats);

        for (PlayerSeason season : latestStats) {
            if (player.getStatistics().contains(season)) {
                playerService.mergeSeason(season);
            } else {
                player.getStatistics().add(season);
            }
        }
    }

    /**
     * Calculate the Position that should be associated with a player's season.
     *
     * @param seasonPositions  A Map of a player's seasons to a list of the positions they played that year
     * @param careerPositions  A Set of positions associated with a player's profile
     * @param statistics  A Set of a players seasonal statistics
     */
    private void calculateSeasonPositions(HashMap<Integer, List<Position>> seasonPositions,
            Set<PlayerPosition> careerPositions, Set<PlayerSeason> statistics) {

        List<Position> allPositions = new ArrayList<>();
        for (Map.Entry<Integer, List<Position>> entry : seasonPositions.entrySet()) {
            if (entry.getValue() != null) {
                allPositions.addAll(entry.getValue());
            }
        }

        for (PlayerSeason season : statistics) {
            List<Position> positions = seasonPositions.get(season.getSeason());
            if (positions.size() == 1 && !(positions.get(0).equals(Position.KR) ||
                positions.get(0).equals(Position.PR))) {
                season.setPosition(positions.get(0));
            } else {
                season.setPosition(consolidate(positions, careerPositions, allPositions, season));
            }
        }
    }

    /**
     *
     *
     * @param seasonPositions
     * @param careerPositions
     * @param allPositions
     * @param season
     * @return
     */
    private Position consolidate(List<Position> seasonPositions, Set<PlayerPosition> careerPositions,
            List<Position> allPositions, PlayerSeason season) {

        Map<Position, Double> values = new EnumMap<>(Position.class);
        List<Position> careerPosList = careerPositions.stream().map(PlayerPosition::getPosition).collect(Collectors.toList());

        if (seasonPositions != null) {
            calculatePositionWeightings(seasonPositions, values, 1.0, 0.9, 0.8);
            ArrayList<Double> valuesList = new ArrayList<>(values.values());
            Collections.sort(valuesList);
            Collections.reverse(valuesList);

            if ((seasonPositions.size() < 2) || (valuesList.size() > 1 && valuesList.get(0).equals(valuesList.get(1)))) {
                calculatePositionWeightings(careerPosList, values, 0.5, 0.4, 0.3);
                if (allPositions != null) {
                    calculatePositionWeightings(allPositions, values, 0.1, 0.05, 0.025);
                }
            }
        } else {
            calculatePositionWeightings(careerPosList, values, 1.0, 0.9, 0.8);
        }

        calculateStatisticAndJerseyNumberWeightings(season, values);
        ArrayList<Position> result = new ArrayList<>();
        double value = 0;

        for (Map.Entry<Position, Double> entry : values.entrySet()) {
            if (values.get(entry.getKey()) > value) {
                result.clear();
                result.add(entry.getKey());
                value = values.get(entry.getKey());
            } else if (values.get(entry.getKey()) == value) {
                result.add(entry.getKey());
            }
        }

        return result.get(0);
    }

    private void calculatePositionWeightings(List<Position> seasonPositions, Map<Position,
            Double> values, double primaryWeighting, double secondaryWeighting, double tertiaryWeighting) {

        for (Position pos : seasonPositions) {
            Double val = values.get(pos);
            val = (val == null) ? 0.0 : val;
            if (pos.equals(Position.KR) || pos.equals(Position.PR)) {
                values.put(pos, val + 0.1);
            } else {
                values.put(pos, val + primaryWeighting);
            }

            switch (pos) {
                case HB:
                case FB:
                    Double rbVal = values.get(Position.RB);
                    rbVal = rbVal == null ? 0.0 : rbVal;
                    values.put(Position.RB, rbVal + secondaryWeighting);
                    break;
                case T:
                case G:
                case C:
                    Double olVal = values.get(Position.OL);
                    olVal = olVal == null ?  0.0 : olVal;
                    values.put(Position.OL, olVal + secondaryWeighting);
                    break;
                case DE:
                case DT:
                case NT:
                    Double dlVal = values.get(Position.DL);
                    dlVal = dlVal == null ? 0.0 : dlVal;
                    values.put(Position.DL, dlVal + secondaryWeighting);
                    break;
                case OLB:
                case ILB:
                case MLB:
                    Double lbVal = values.get(Position.LB);
                    lbVal = lbVal == null ? 0.0 : lbVal;
                    values.put(Position.LB, lbVal + secondaryWeighting);
                    break;
                case FS:
                case SS:
                    Double sVal = values.get(Position.S);
                    sVal = sVal == null ? 0.0: sVal;
                    values.put(Position.S, sVal + secondaryWeighting);

                    Double dbVal = values.get(Position.DB);
                    dbVal = dbVal == null ? 0.0 : dbVal;
                    values.put(Position.DB, dbVal + tertiaryWeighting);
                    break;
                case S:
                    Double dbVal3 = values.get(Position.DB);
                    dbVal3 = dbVal3 == null ? 0.0 : dbVal3;
                    values.put(Position.DB, dbVal3 + secondaryWeighting);
                    break;
                case CB:
                    Double dbVal2 = values.get(Position.DB);
                    dbVal2 = dbVal2 == null ? 0.0 : dbVal2;
                    values.put(Position.DB, dbVal2 + secondaryWeighting);
                    break;
                default:
            }
        }
    }

    private void calculateStatisticAndJerseyNumberWeightings(PlayerSeason season, Map<Position, Double> values) {

        Integer passAtt = season.getPassAtt();
        Integer jerseyNum = season.getJerseyNumber();
        Integer rushAtt = season.getRushAtt();
        Integer rec = season.getReceptions();

        if (passAtt >= 20) {
            values.put(Position.QB, 1000.0);
            return;
        }

        double jerseyVal;

        if (values.get(Position.QB) != null) {
            jerseyVal = jerseyNum < 20 ? 0.1 : 0;
            values.put(Position.QB, values.get(Position.QB) + jerseyVal + (passAtt * 0.01));
        }
        if (values.get(Position.HB) != null) {
            jerseyVal = jerseyNum >= 20 && jerseyNum < 50 ? 0.1 : 0;
            values.put(Position.HB, values.get(Position.HB) + jerseyVal + (rushAtt * 0.01) + (rec * 0.005));
        }
        if (values.get(Position.FB) != null) {
            jerseyVal = jerseyNum >= 20 && jerseyNum < 50 ? 0.1 : 0;
            values.put(Position.FB, values.get(Position.FB) + jerseyVal + (rushAtt * 0.01) + (rec * 0.005));
        }
        if (values.get(Position.RB) != null) {
            jerseyVal = jerseyNum >= 20 && jerseyNum < 50 ? 0.1 : 0;
            values.put(Position.RB, values.get(Position.RB) + jerseyVal + (rushAtt * 0.01) + (rec * 0.005));
        }
        if (values.get(Position.WR) != null) {
            jerseyVal = ((jerseyNum >= 80 && jerseyNum < 90) || (jerseyNum >= 10 && jerseyNum < 20)) ? 0.1 : 0;
            values.put(Position.WR, values.get(Position.WR) + jerseyVal + (rec * 0.01));
        }
        if (values.get(Position.TE) != null) {
            jerseyVal = ((jerseyNum >= 80 && jerseyNum < 90) || (jerseyNum >= 40 && jerseyNum < 50)) ? 0.1 : 0;
            values.put(Position.TE, values.get(Position.TE) + jerseyVal + (rec * 0.01));
        }
        if (values.get(Position.OL) != null) {
            jerseyVal = jerseyNum >= 50 && jerseyNum < 80 ? 0.1 : 0;
            values.put(Position.OL, values.get(Position.OL) + jerseyVal);
        }
        if (values.get(Position.T) != null) {
            jerseyVal = jerseyNum >= 50 && jerseyNum < 80 ? 0.1 : 0;
            values.put(Position.T, values.get(Position.T) + jerseyVal);
        }
        if (values.get(Position.G) != null) {
            jerseyVal = jerseyNum >= 50 && jerseyNum < 80 ? 0.1 : 0;
            values.put(Position.G, values.get(Position.G) + jerseyVal);
        }
        if (values.get(Position.C) != null) {
            jerseyVal = jerseyNum >= 50 && jerseyNum < 80 ? 0.1 : 0;
            values.put(Position.C, values.get(Position.C) + jerseyVal);
        }
        if (values.get(Position.DL) != null) {
            jerseyVal = ((jerseyNum >= 50 && jerseyNum < 60) || (jerseyNum >= 70 && jerseyNum < 80) ||
                    (jerseyNum >= 90)) ? 0.1 : 0;
            values.put(Position.DL, values.get(Position.DL) + jerseyVal);
        }
        if (values.get(Position.DE) != null) {
            jerseyVal = ((jerseyNum >= 50 && jerseyNum < 60) || (jerseyNum >= 70 && jerseyNum < 80) ||
                    (jerseyNum >= 90)) ? 0.1 : 0;
            values.put(Position.DE, values.get(Position.DE) + jerseyVal);
        }
        if (values.get(Position.DT) != null) {
            jerseyVal = ((jerseyNum >= 70 && jerseyNum < 80) || (jerseyNum >= 90)) ? 0.1 : 0;
            values.put(Position.DT, values.get(Position.DT) + jerseyVal);
        }
        if (values.get(Position.NT) != null) {
            jerseyVal = ((jerseyNum >= 70 && jerseyNum < 80) || (jerseyNum >= 90)) ? 0.1 : 0;
            values.put(Position.NT, values.get(Position.NT) + jerseyVal);
        }
        if (values.get(Position.LB) != null) {
            jerseyVal = ((jerseyNum >= 40 && jerseyNum < 60) || (jerseyNum >= 90)) ? 0.1 : 0;
            values.put(Position.LB, values.get(Position.LB) + jerseyVal);
        }
        if (values.get(Position.ILB) != null) {
            jerseyVal = ((jerseyNum >= 40 && jerseyNum < 60) || (jerseyNum >= 90)) ? 0.1 : 0;
            values.put(Position.ILB, values.get(Position.ILB) + jerseyVal);
        }
        if (values.get(Position.MLB) != null) {
            jerseyVal = ((jerseyNum >= 40 && jerseyNum < 60) || (jerseyNum >= 90)) ? 0.1 : 0;
            values.put(Position.MLB, values.get(Position.MLB) + jerseyVal);
        }
        if (values.get(Position.OLB) != null) {
            jerseyVal = ((jerseyNum >= 40 && jerseyNum < 60) || (jerseyNum >= 90)) ? 0.1 : 0;
            values.put(Position.OLB, values.get(Position.OLB) + jerseyVal);
        }
        if (values.get(Position.DB) != null) {
            jerseyVal = ((jerseyNum >= 20 && jerseyNum < 50)) ? 0.1 : 0;
            values.put(Position.DB, values.get(Position.DB) + jerseyVal);
        }
        if (values.get(Position.CB) != null) {
            jerseyVal = ((jerseyNum >= 20 && jerseyNum < 50)) ? 0.1 : 0;
            values.put(Position.CB, values.get(Position.CB) + jerseyVal);
        }
        if (values.get(Position.S) != null) {
            jerseyVal = ((jerseyNum >= 20 && jerseyNum < 50)) ? 0.1 : 0;
            values.put(Position.S, values.get(Position.S) + jerseyVal);
        }
        if (values.get(Position.FS) != null) {
            jerseyVal = ((jerseyNum >= 20 && jerseyNum < 50)) ? 0.1 : 0;
            values.put(Position.FS, values.get(Position.FS) + jerseyVal);
        }
        if (values.get(Position.SS) != null) {
            jerseyVal = ((jerseyNum >= 20 && jerseyNum < 50)) ? 0.1 : 0;
            values.put(Position.SS, values.get(Position.SS) + jerseyVal);
        }
    }

    private Set<Position> parsePositions(String rawData) {
        String[] yearData = rawData.split("[///;:,]");
        Set<Position> positions = new HashSet<>();

        for (String pos : yearData) {
            try {
                if (StringUtils.isNotBlank(pos)) {
                    positions.add(Position.getPositionByAlias(pos.toUpperCase()));
                }
            } catch (IllegalArgumentException iae) {
                log.warn(iae.getMessage(), iae);
            }
        }

        return positions;
    }

    private void parsePassingStatistics(PlayerSeason playerSeason, Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) throws RosterReviewException {

        for (int j = 3; j < row.getCells().size(); j++) {
            HtmlTableCell cell = row.getCell(j);
            String cellData = cell.getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(),
                        k -> new ArrayList<Position>()).addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_cmp": playerSeason.setPassComp(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_att": playerSeason.setPassAtt(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_yds": playerSeason.setPassYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_td": playerSeason.setPassTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_int": playerSeason.setPassInts(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_long": playerSeason.setPassLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_rating": playerSeason.setPassRating(WebScrapingUtils.parseDoubleWithDefault(cellData, 0.0));
                    break;
                case "pass_sacked": playerSeason.setSacked(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_sacked_yds": playerSeason.setSackedYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "av": playerSeason.setAvgValue(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "": throw new RosterReviewException(
                     "Could not find value for 'data-stat' label on passing data column with index: " + j + ".");
                default : // Do nothing.
            }
        }
    }

    private void parsePostseasonPassingStatistics(PlayerSeason playerSeason, Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) throws RosterReviewException {

        for (int j = 3; j < row.getCells().size(); j++) {
            HtmlTableCell cell = row.getCell(j);
            String cellData = cell.getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setPostseasonGamesPlayed(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setPostseasonGamesStarted(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_cmp": playerSeason.setPostseasonPassComp(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_att": playerSeason.setPostseasonPassAtt(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_yds": playerSeason.setPostseasonPassYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_td": playerSeason.setPostseasonPassTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_int": playerSeason.setPostseasonPassInts(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_long": playerSeason.setPostseasonPassLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_rating": playerSeason.setPostseasonPassRating(
                        WebScrapingUtils.parseDoubleWithDefault(cellData, 0.0));
                    break;
                case "pass_sacked": playerSeason.setPostseasonSacked(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_sacked_yds": playerSeason.setPostseasonSackedYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "": throw new RosterReviewException(
                     "Could not find value for 'data-stat' label on passing data column with index: " + j + ".");
                default : // Do nothing.
            }
        }
    }

    private void parseRushingAndReceivingStatistics(PlayerSeason playerSeason, Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) throws RosterReviewException {

        for (int j = 3; j < row.getCells().size(); j++) {
            HtmlTableCell cell = row.getCell(j);
            String cellData = cell.getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(), k -> new ArrayList<Position>())
                            .addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rush_att": playerSeason.setRushAtt(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rush_yds": playerSeason.setRushYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rush_td": playerSeason.setRushTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rush_long": playerSeason.setRushLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles": playerSeason.setFumbles(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "targets": playerSeason.setTargets(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rec": playerSeason.setReceptions(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rec_yds": playerSeason.setRecYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rec_td": playerSeason.setRecTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rec_long": playerSeason.setRecLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "av": playerSeason.setAvgValue(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "": throw new RosterReviewException(
                     "Could not find value for 'data-stat' label on rushing data column with index: " + j + ".");
                default : // Do nothing.
            }
        }
    }

    private void parsePostseasonRushingAndReceivingStatistics(PlayerSeason playerSeason,
            Map<Integer, List<Position>> seasonPositions, List<HtmlTableCell> tableHeader, HtmlTableRow row)
            throws RosterReviewException {

        for (int j = 3; j < row.getCells().size(); j++) {
            HtmlTableCell cell = row.getCell(j);
            String cellData = cell.getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setPostseasonGamesPlayed(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setPostseasonGamesStarted(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rush_att": playerSeason.setPostseasonRushAtt(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rush_yds": playerSeason.setPostseasonRushYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rush_td": playerSeason.setPostseasonRushTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rush_long": playerSeason.setPostseasonRushLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles": playerSeason.setPostseasonFumbles(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "targets": playerSeason.setPostseasonTargets(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rec": playerSeason.setPostseasonReceptions(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rec_yds": playerSeason.setPostseasonRecYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rec_td": playerSeason.setPostseasonRecTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rec_long": playerSeason.setPostseasonRecLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "": throw new RosterReviewException(
                     "Could not find value for 'data-stat' label on rushing data column with index: " + j + ".");
                default : // Do nothing.
            }
        }
    }

    private void parseDefensiveStatistics(PlayerSeason playerSeason, Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) throws RosterReviewException {

        for (int j = 3; j < row.getCells().size(); j++) {
            HtmlTableCell cell = row.getCell(j);
            String cellData = cell.getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(), k -> new ArrayList<Position>())
                            .addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "def_int": playerSeason.setInterceptions(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "def_int_yds": playerSeason.setIntYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "def_int_td": playerSeason.setIntTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "def_int_long": playerSeason.setIntLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_defended": playerSeason.setPassDef(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles_forced": playerSeason.setFumForced(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles": playerSeason.setFumbles(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles_rec": playerSeason.setFumRec(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles_rec_yds": playerSeason.setFumRecYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles_rec_td": playerSeason.setFumRecTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "sacks": playerSeason.setSacks(WebScrapingUtils.parseDoubleWithDefault(cellData, 0.0));
                    break;
                case "tackles_solo": playerSeason.setTackles(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "tackles_assists": playerSeason.setAssists(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "safety_md": playerSeason.setSafeties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "av": playerSeason.setAvgValue(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "": throw new RosterReviewException(
                     "Could not find value for 'data-stat' label on rushing data column with index: " + j + ".");
                default : // Do nothing.
            }
        }
    }

    private void parsePostseasonDefensiveStatistics(PlayerSeason playerSeason, Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) throws RosterReviewException {

        for (int j = 3; j < row.getCells().size(); j++) {
            HtmlTableCell cell = row.getCell(j);
            String cellData = cell.getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setPostseasonGamesPlayed(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setPostseasonGamesStarted(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "def_int": playerSeason.setPostseasonInterceptions(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "def_int_yds": playerSeason.setPostseasonIntYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "def_int_td": playerSeason.setPostseasonIntTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "def_int_long": playerSeason.setPostseasonIntLong(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "pass_defended": playerSeason.setPostseasonPassDef(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles_forced": playerSeason.setPostseasonFumForced(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles": playerSeason.setPostseasonFumbles(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles_rec": playerSeason.setPostseasonFumRec(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles_rec_yds": playerSeason.setPostseasonFumRecYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fumbles_rec_td": playerSeason.setPostseasonFumRecTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "sacks": playerSeason.setPostseasonSacks(WebScrapingUtils.parseDoubleWithDefault(cellData, 0.0));
                    break;
                case "tackles_solo": playerSeason.setPostseasonTackles(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "tackles_assists": playerSeason.setPostseasonAssists(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "safety_md": playerSeason.setPostseasonSafeties(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "": throw new RosterReviewException(
                     "Could not find value for 'data-stat' label on rushing data column with index: " + j + ".");
                default : // Do nothing.
            }
        }
    }

    private void parseKickingStatistics(PlayerSeason playerSeason, Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) throws RosterReviewException {

        for (int j = 3; j < row.getCells().size(); j++) {
            HtmlTableCell cell = row.getCell(j);
            String cellData = cell.getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(), k -> new ArrayList<Position>())
                            .addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga1": playerSeason.setFgaTeens(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm1": playerSeason.setFgmTeens(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga2": playerSeason.setFgaTwenties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm2": playerSeason.setFgmTwenties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga3": playerSeason.setFgaThirties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm3": playerSeason.setFgmThirties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga4": playerSeason.setFgaFourties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm4": playerSeason.setFgmFourties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga5": playerSeason.setFgaFiftyPlus(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm5": playerSeason.setFgmFiftyPlus(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga": playerSeason.setFgaTotal(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm": playerSeason.setFgmTotal(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fg_long": playerSeason.setFgLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "xpa": playerSeason.setXpa(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "xpm": playerSeason.setXpm(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt": playerSeason.setPunts(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_yds": playerSeason.setPuntYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_long": playerSeason.setPuntLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_blocked": playerSeason.setPuntBlocked(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "av": playerSeason.setAvgValue(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "": throw new RosterReviewException(
                     "Could not find value for 'data-stat' label on rushing data column with index: " + j + ".");
                default : // Do nothing.
            }
        }
    }

    private void parsePostseasonKickingStatistics(PlayerSeason playerSeason, Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) throws RosterReviewException {

        for (int j = 3; j < row.getCells().size(); j++) {
            HtmlTableCell cell = row.getCell(j);
            String cellData = cell.getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setPostseasonGamesPlayed(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setPostseasonGamesStarted(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga1": playerSeason.setPostseasonFgaTeens(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm1": playerSeason.setPostseasonFgmTeens(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga2": playerSeason.setPostseasonFgaTwenties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm2": playerSeason.setPostseasonFgmTwenties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga3": playerSeason.setPostseasonFgaThirties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm3": playerSeason.setPostseasonFgmThirties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga4": playerSeason.setPostseasonFgaFourties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm4": playerSeason.setPostseasonFgmFourties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga5": playerSeason.setPostseasonFgaFiftyPlus(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm5": playerSeason.setPostseasonFgmFiftyPlus(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga": playerSeason.setPostseasonFgaTotal(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm": playerSeason.setPostseasonFgmTotal(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fg_long": playerSeason.setPostseasonFgLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "xpa": playerSeason.setPostseasonXpa(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "xpm": playerSeason.setPostseasonXpm(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt": playerSeason.setPostseasonPunts(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_yds": playerSeason.setPostseasonPuntYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_long": playerSeason.setPostseasonPuntLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_blocked": playerSeason.setPostseasonPuntBlocked(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "": throw new RosterReviewException(
                     "Could not find value for 'data-stat' label on rushing data column with index: " + j + ".");
                default : // Do nothing.
            }
        }
    }

    private void parseReturnStatistics(PlayerSeason playerSeason, Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) throws RosterReviewException {

        for (int j = 3; j < row.getCells().size(); j++) {
            HtmlTableCell cell = row.getCell(j);
            String cellData = cell.getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(), k -> new ArrayList<Position>())
                            .addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_ret": playerSeason.setPuntRet(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_ret_yds": playerSeason.setPuntRetYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_ret_td": playerSeason.setPuntRetTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_ret_long": playerSeason.setPuntRetLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "kick_ret": playerSeason.setKickRet(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "kick_ret_yds": playerSeason.setKickRetYds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "kick_ret_td": playerSeason.setKickRetTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "kick_ret_long": playerSeason.setKickRetLong(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "": throw new RosterReviewException(
                     "Could not find value for 'data-stat' label on rushing data column with index: " + j + ".");
                default : // Do nothing.
            }
        }
    }

    private void parsePostseasonReturnStatistics(PlayerSeason playerSeason, Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) throws RosterReviewException {

        for (int j = 3; j < row.getCells().size(); j++) {
            HtmlTableCell cell = row.getCell(j);
            String cellData = cell.getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(), k -> new ArrayList<Position>())
                            .addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setPostseasonGamesPlayed(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setPostseasonGamesStarted(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_ret": playerSeason.setPostseasonPuntRet(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_ret_yds": playerSeason.setPostseasonPuntRetYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_ret_td": playerSeason.setPostseasonPuntRetTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "punt_ret_long": playerSeason.setPostseasonPuntRetLong(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "kick_ret": playerSeason.setPostseasonKickRet(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "kick_ret_yds": playerSeason.setPostseasonKickRetYds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "kick_ret_td": playerSeason.setPostseasonKickRetTds(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "kick_ret_long": playerSeason.setPostseasonKickRetLong(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "": throw new RosterReviewException(
                     "Could not find value for 'data-stat' label on rushing data column with index: " + j + ".");
                default : // Do nothing.
            }
        }
    }

    private void parseScoringStatistics(PlayerSeason playerSeason, Map<Integer, List<Position>> seasonPositions,
            List<HtmlTableCell> tableHeader, HtmlTableRow row) throws RosterReviewException {
        for (int j = 3; j < row.getCells().size(); j++) {
            HtmlTableCell cell = row.getCell(j);
            String cellData = cell.getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "pos": seasonPositions.computeIfAbsent(playerSeason.getSeason(), k -> new ArrayList<Position>())
                            .addAll(parsePositions(cellData));
                    break;
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setGamesPlayed(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setGamesStarted(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rushtd": playerSeason.setRushTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rectd": playerSeason.setRecTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "prtd": playerSeason.setPuntRetTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "krtd": playerSeason.setKickRetTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "frtd": playerSeason.setFumRecTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "ditd": playerSeason.setIntTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "otd": playerSeason.setOtherTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "alltd": playerSeason.setAllTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "two_pt_md": playerSeason.setTwoPointConv(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "xpm": playerSeason.setXpm(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "xpa": playerSeason.setXpa(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm": playerSeason.setFgmTotal(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga": playerSeason.setFgaTotal(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "safety_md": playerSeason.setSafeties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "": throw new RosterReviewException(
                     "Could not find value for 'data-stat' label on rushing data column with index: " + j + ".");
                default : // Do nothing.
            }
        }
    }

    /**
     * Parse the 'Post-season Scoring' statistics table.
     *
     * @param playerSeason
     * @param tableHeader  An ordered list of column titles
     * @param row
     */
    private void parsePostseasonScoringStatistics(PlayerSeason playerSeason, List<HtmlTableCell> tableHeader,
            HtmlTableRow row) {
        for (int j = 3; j < row.getCells().size(); j++) {
            HtmlTableCell cell = row.getCell(j);
            String cellData = cell.getTextContent();

            switch (tableHeader.get(j).getAttribute("data-stat")) {
                case "uniform_number": playerSeason.setJerseyNumber(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, null));
                    break;
                case "g": playerSeason.setPostseasonGamesPlayed(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "gs": playerSeason.setPostseasonGamesStarted(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rushtd": playerSeason.setPostseasonRushTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "rectd": playerSeason.setPostseasonRecTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "prtd": playerSeason.setPostseasonPuntRetTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "krtd": playerSeason.setPostseasonKickRetTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "frtd": playerSeason.setPostseasonFumRecTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "ditd": playerSeason.setPostseasonIntTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "otd": playerSeason.setPostseasonOtherTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "alltd": playerSeason.setPostseasonAllTds(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "two_pt_md": playerSeason.setPostseasonTwoPointConv(
                        WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "xpm": playerSeason.setPostseasonXpm(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "xpa": playerSeason.setPostseasonXpa(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fgm": playerSeason.setPostseasonFgmTotal(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "fga": playerSeason.setPostseasonFgaTotal(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                    break;
                case "safety_md": playerSeason.setPostseasonSafeties(WebScrapingUtils.parseIntegerWithDefault(cellData, 0));
                default : // Do nothing.
            }
        }
    }
} // 1530