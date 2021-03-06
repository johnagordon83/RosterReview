package com.rosterreview.data;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Registers a scheduled event that periodically updates the data store
 * with recent data from an external source.
 */

@Component
public class DataUpdateScheduler {

    @Autowired
    protected PfrDataUpdater pfrDataUpdater;

    protected static Logger log = LoggerFactory.getLogger(PfrDataUpdater.class);

    /**
     * Schedules a recurring event that updates the data store with recent
     * player data from a configured external source.
     */

    /**
     * Updates the data store with recent player data retrieved from
     * https://www.pro-football-reference.com.
     */
    @Scheduled(cron = "${player.data.update.schedule}", zone = "${player.data.update.timezone}")
    public void schedulePlayerDataUpdate() {
        log.info("Scheduled player data update is starting.");

        WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);

        for (char letter = 'A'; letter <= 'Z'; letter++) {
            try {
                String playersByLetterUrl = PfrDataUpdater.PFR_URL + "/players/" + letter;

                HtmlPage page = webClient.getPage(playersByLetterUrl);
                List<DomNode> activePlayerNodes = page.getByXPath("div[@id='div_players']/b/a");

                for (DomNode node : activePlayerNodes) {
                    String playerUrl = PfrDataUpdater.PFR_URL + ((DomElement) node).getAttribute("href");
                    pfrDataUpdater.parseAndPersistPlayerDataFromUrl(playerUrl);
                }
            } catch (IOException iox) {
                log.error("There was an error while updating player data.", iox);
            }
        }
        webClient.close();

        log.info("Scheduled player data update has completed.");
    }
}