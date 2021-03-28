package com.rosterreview.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosterreview.config.AppConfig;
import com.rosterreview.entity.DraftPick;
import com.rosterreview.entity.Player;
import com.rosterreview.entity.PlayerSeason;
import com.rosterreview.entity.Team;
import com.rosterreview.service.PfrDataParsingService;
import com.rosterreview.service.PlayerService;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = { AppConfig.class })
public class PfrDataUpdaterTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PfrDataParsingService parsingService;

    @Autowired
    private PlayerService playerService;

    private static final String PLAYER_TEST_DATA_DIR = "/playerdata/";

    private static final String PFR_PLAYER_URL = "https://www.pro-football-reference.com/players/";

    private static Logger log = LoggerFactory.getLogger(PfrDataUpdaterTest.class);

    @BeforeAll
    public static void setUp() throws Exception {
        log.info("Testing starting.");
    }

    @AfterAll
    public static void tearDown() throws Exception {
        log.info("Testing complete.");
    }

    @Test
    public void testParseAndrewLuckData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "aluck.json";
        String playerUrl = PFR_PLAYER_URL + "L/LuckAn00.htm";
        String playerId = "luckan01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseBobAglerData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "bagler.json";
        String playerUrl = PFR_PLAYER_URL + "A/AgleBo20.htm";
        String playerId = "aglerro01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseBrianUrlacherData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "burlacher.json";
        String playerUrl = PFR_PLAYER_URL + "U/UrlaBr00.htm";
        String playerId = "urlachbr01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseBrettFavreData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "bfavre.json";
        String playerUrl = PFR_PLAYER_URL + "F/FavrBr00.htm";
        String playerId = "favrebr01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseCharlesWoodsonData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "cwoodson.json";
        String playerUrl = PFR_PLAYER_URL + "W/WoodCh00.htm";
        String playerId = "woodsoch01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseDanDierdorfData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "ddierdorf.json";
        String playerUrl = PFR_PLAYER_URL + "D/DierDa00.htm";
        String playerId = "dierdoda01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseDomanickWilliamsData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "ddavis.json";
        String playerUrl = PFR_PLAYER_URL + "D/DaviDo01.htm";
        String playerId = "davisdo01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseEricDickersonData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "edickerson.json";
        String playerUrl = PFR_PLAYER_URL + "D/DickEr00.htm";
        String playerId = "dickerer01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseFrankAbruzzinoData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "fabruzzino.json";
        String playerUrl = PFR_PLAYER_URL + "A/AbruFr20.htm";
        String playerId = "abruzzfr01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseSammieHillData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "shill.json";
        String playerUrl = PFR_PLAYER_URL + "H/HillSa99.htm";
        String playerId = "hillsa01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseJamalWilliamsData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "jwilliams.json";
        String playerUrl = PFR_PLAYER_URL + "W/WillJa99.htm";
        String playerId = "williaja01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseJerryRiceData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "jrice.json";
        String playerUrl = PFR_PLAYER_URL + "R/RiceJe00.htm";
        String playerId = "riceje01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseJasonTaylorData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "jtaylor.json";
        String playerUrl = PFR_PLAYER_URL + "T/TaylJa03.htm";
        String playerId = "taylorja01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseJuniorSeauData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "jseau.json";
        String playerUrl = PFR_PLAYER_URL + "S/SeauJu00.htm";
        String playerId = "seauti01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseLarryAllenData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "lallen.json";
        String playerUrl = PFR_PLAYER_URL + "A/AlleLa00.htm";
        String playerId = "allenla01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseLaDainianTomlinsonData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "ltomlinson.json";
        String playerUrl = PFR_PLAYER_URL + "T/TomlLa00.htm";
        String playerId = "tomlinla01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseMarkVanderPoelData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "mvanderpoel.json";
        String playerUrl = PFR_PLAYER_URL + "V/VandMa20.htm";
        String playerId = "vanderjo01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParsePJDanielsData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "pdaniels.json";
        String playerUrl = PFR_PLAYER_URL + "D/DaniP.00.htm";
        String playerId = "danielp.01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseSebastianJanikowskiData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "sjanikowski.json";
        String playerUrl = PFR_PLAYER_URL + "J/janikseb01.htm";
        String playerId = "janikose01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseShaneLechlerData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "slechler.json";
        String playerUrl = PFR_PLAYER_URL + "L/LechSh20.htm";
        String playerId = "lechleed01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseTerrelDavisData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "tdavis.json";
        String playerUrl = PFR_PLAYER_URL + "D/DaviTe00.htm";
        String playerId = "daviste01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseTonyGonzalezData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "tgonzalez.json";
        String playerUrl = PFR_PLAYER_URL + "G/GonzTo00.htm";
        String playerId = "gonzalan01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testParseWillieWoodData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "wwood.json";
        String playerUrl = PFR_PLAYER_URL + "W/WoodWi00.htm";
        String playerId = "woodwi01";

        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    @Test
    public void testUpdateExistingPlayerData() throws IOException {
        String playerFileName = PLAYER_TEST_DATA_DIR + "rmathews.json";
        String preUpdatePlayerFileName = PLAYER_TEST_DATA_DIR + "rmathews_pre-update.json";
        String playerUrl = PFR_PLAYER_URL + "M/MathRy00.htm";
        String playerId = "mathewry01";

        // Store "pre-update" player data in db
        Resource resource = new ClassPathResource(preUpdatePlayerFileName);
        Player preUpdatePlayer = objectMapper.readValue(resource.getInputStream(), Player.class);
        playerService.persistPlayer(preUpdatePlayer);

        // Parse, persist and validate the latest data for this player.
        // Pre-update data should be replaced with latest.
        validatePlayerInfo(playerFileName, playerUrl, playerId);
    }

    private void validatePlayerInfo(String playerFileName, String playerUrl, String playerId) throws IOException {

        Resource resource = new ClassPathResource(playerFileName);
        Player refData = objectMapper.readValue(resource.getInputStream(), Player.class);
        parsingService.parseAndPersistPlayerDataFromUrl(playerUrl);
        Player player = playerService.getPlayer(playerId);

        // Validate Ids
        assertEquals(refData.getId(), player.getId());
        assertEquals(refData.getPfrId(), player.getPfrId());

        // Validate player name
        assertEquals(refData.getNickname(), player.getNickname());
        assertEquals(refData.getFirstName(), player.getFirstName());
        assertEquals(refData.getMiddleName(), player.getMiddleName());
        assertEquals(refData.getLastName(), player.getLastName());
        assertEquals(refData.getSuffix(), player.getSuffix());

        // Validate height & weight
        assertEquals(refData.getHeight(), player.getHeight());
        assertEquals(refData.getWeight(), player.getWeight());

        // Validate birth date
        assertEquals(refData.getBirthDate(), player.getBirthDate());

        // Validate college
        assertEquals(refData.getCollege(), player.getCollege());

        // Validate Hall of Fame year/status
        assertEquals(refData.getHofYear(), player.getHofYear());

        // Validate player position
        assertEquals(refData.getPositions(), player.getPositions());

        validatePlayerDraftHistory(player, refData);
        validatePlayerStatistics(player, refData);
    }

    private void validatePlayerDraftHistory(Player player, Player refData) {

        assertEquals(refData.getDraftPicks().size(), player.getDraftPicks().size());

        for (DraftPick refPick : refData.getDraftPicks()) {

            DraftPick draftPick = player.getDraftPicks().stream().filter(dp ->
                dp.equals(refPick)).findFirst().orElse(null);

            assertNotNull(draftPick);
            assertEquals(refPick.getLeague(), draftPick.getLeague());
            assertEquals(refPick.getRound(), draftPick.getRound());
            assertEquals(refPick.getSlot(), draftPick.getSlot());
            assertEquals(refPick.getYear(), draftPick.getYear());

            validateTeam(draftPick.getTeam(), refPick.getTeam());
        }
    }

    private void validatePlayerStatistics(Player player, Player refData) {

        assertEquals(refData.getStatistics().size(), player.getStatistics().size());

        for (PlayerSeason refSeason : refData.getStatistics()) {
            PlayerSeason season = player.getStatistics().stream().filter(dp ->
                 dp.equals(refSeason)).findFirst().orElse(null);
            validateTeam(season.getTeam(), refSeason.getTeam());
            assertEquals(refSeason.getAge(), season.getAge());
            assertEquals(refSeason.getPosition(), season.getPosition());
            assertEquals(refSeason.getJerseyNumber(), season.getJerseyNumber());
            assertEquals(refSeason.getGamesPlayed(), season.getGamesPlayed());
            assertEquals(refSeason.getGamesStarted(), season.getGamesStarted());
            assertEquals(refSeason.getPassComp(), season.getPassComp());
            assertEquals(refSeason.getPassAtt(), season.getPassAtt());
            assertEquals(refSeason.getPassYds(), season.getPassYds());
            assertEquals(refSeason.getPassTds(), season.getPassTds());
            assertEquals(refSeason.getPassInts(), season.getPassInts());
            assertEquals(refSeason.getPassLong(), season.getPassLong());
            assertEquals(refSeason.getPasserRating(), season.getPasserRating(), 0.0);
            assertEquals(refSeason.getSacked(), season.getSacked());
            assertEquals(refSeason.getSackedYds(), season.getSackedYds());
            assertEquals(refSeason.getRushAtt(), season.getRushAtt());
            assertEquals(refSeason.getRushYds(), season.getRushYds());
            assertEquals(refSeason.getRushTds(), season.getRushTds());
            assertEquals(refSeason.getRushLong(), season.getRushLong());
            assertEquals(refSeason.getTargets(), season.getTargets());
            assertEquals(refSeason.getReceptions(), season.getReceptions());
            assertEquals(refSeason.getRecYds(), season.getRecYds());
            assertEquals(refSeason.getRecTds(), season.getRecTds());
            assertEquals(refSeason.getRecLong(), season.getRecLong());
            assertEquals(refSeason.getFumbles(), season.getFumbles());
            assertEquals(refSeason.getInterceptions(), season.getInterceptions());
            assertEquals(refSeason.getIntYds(), season.getIntYds());
            assertEquals(refSeason.getIntTds(), season.getIntTds());
            assertEquals(refSeason.getIntLong(), season.getIntLong());
            assertEquals(refSeason.getPassDef(), season.getPassDef());
            assertEquals(refSeason.getFumForced(), season.getFumForced());
            assertEquals(refSeason.getFumRec(), season.getFumRec());
            assertEquals(refSeason.getFumRecYds(), season.getFumRecYds());
            assertEquals(refSeason.getFumRecTds(), season.getFumRecTds());
            assertEquals(refSeason.getSacks(), season.getSacks());
            assertEquals(refSeason.getTackles(), season.getTackles());
            assertEquals(refSeason.getAssists(), season.getAssists());
            assertEquals(refSeason.getSafeties(), season.getSafeties());
            assertEquals(refSeason.getFgaTeens(), season.getFgaTeens());
            assertEquals(refSeason.getFgmTeens(), season.getFgmTeens());
            assertEquals(refSeason.getFgaTwenties(), season.getFgaTwenties());
            assertEquals(refSeason.getFgmTwenties(), season.getFgmTwenties());
            assertEquals(refSeason.getFgaThirties(), season.getFgaThirties());
            assertEquals(refSeason.getFgmThirties(), season.getFgmThirties());
            assertEquals(refSeason.getFgaFourties(), season.getFgaFourties());
            assertEquals(refSeason.getFgmFourties(), season.getFgmFourties());
            assertEquals(refSeason.getFgaFiftyPlus(), season.getFgaFiftyPlus());
            assertEquals(refSeason.getFgmFiftyPlus(), season.getFgmFiftyPlus());
            assertEquals(refSeason.getFgaTotal(), season.getFgaTotal());
            assertEquals(refSeason.getFgmTotal(), season.getFgmTotal());
            assertEquals(refSeason.getFgLong(), season.getFgLong());
            assertEquals(refSeason.getXpa(), season.getXpa());
            assertEquals(refSeason.getXpm(), season.getXpm());
            assertEquals(refSeason.getPunts(), season.getPunts());
            assertEquals(refSeason.getPuntYds(), season.getPuntYds());
            assertEquals(refSeason.getPuntLong(), season.getPuntLong());
            assertEquals(refSeason.getPuntBlocked(), season.getPuntBlocked());
            assertEquals(refSeason.getPuntRet(), season.getPuntRet());
            assertEquals(refSeason.getPuntRetYds(), season.getPuntRetYds());
            assertEquals(refSeason.getPuntRetTds(), season.getPuntRetTds());
            assertEquals(refSeason.getPuntRetLong(), season.getPuntRetLong());
            assertEquals(refSeason.getKickRet(), season.getKickRet());
            assertEquals(refSeason.getKickRetYds(), season.getKickRetYds());
            assertEquals(refSeason.getKickRetTds(), season.getKickRetTds());
            assertEquals(refSeason.getKickRetLong(), season.getKickRetLong());
            assertEquals(refSeason.getOtherTds(), season.getOtherTds());
            assertEquals(refSeason.getAllTds(), season.getAllTds());
            assertEquals(refSeason.getTwoPointConv(), season.getTwoPointConv());
        }
    }

    private void validateTeam(Team team, Team refTeam) {
        assertEquals(refTeam, team);
        assertEquals(refTeam.getLocation(), team.getLocation());
        assertEquals(refTeam.getName(), team.getName());
        assertEquals(refTeam.getPfrAbbrev(), team.getPfrAbbrev());
        assertEquals(refTeam.getPfrId(), team.getPfrId());
        assertEquals(refTeam.getSeason(), team.getSeason());
    }
}