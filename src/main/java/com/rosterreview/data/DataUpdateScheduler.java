package com.rosterreview.data;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rosterreview.service.PfrDataParsingService;
import com.rosterreview.utils.WebScrapingUtils;

/**
 * A {@link Component} class that registers a scheduled event that periodically
 * updates the data store with recent data from an external source.
 */

@Component
public class DataUpdateScheduler {

    @Autowired
    private PfrDataParsingService pfrDataUpdater;

    private static final Logger LOG = LoggerFactory.getLogger(PfrDataParsingService.class);

    /**
     * Updates the data store with recent player data retrieved from
     * https://www.pro-football-reference.com.
     */
    @Scheduled(cron = "${player.data.update.schedule}",
               zone = "${player.data.update.timezone}")
    public void schedulePlayerDataUpdate() {
        LOG.info("Scheduled player data update is starting.");

        WebClient webClient = WebScrapingUtils.getConfiguredWebClient();

        for (char letter = 'A'; letter <= 'Z'; letter++) {
            try {
                String playersByLetterUrl = PfrDataParsingService.PFR_URL
                        + "/players/" + letter;

                HtmlPage page = webClient.getPage(playersByLetterUrl);
                List<DomNode> activePlayerNodes = page.getByXPath("div[@id='div_players']/b/a");

                for (DomNode node : activePlayerNodes) {
                    String playerUrl = PfrDataParsingService.PFR_URL +
                            ((DomElement) node).getAttribute("href");
                    pfrDataUpdater.parseAndPersistPlayerDataFromUrl(playerUrl);
                }
            } catch (IOException iox) {
                LOG.error("There was an error while updating player data.", iox);
            }
        }

        webClient.close();
        LOG.info("Scheduled player data update has completed.");
    }
}