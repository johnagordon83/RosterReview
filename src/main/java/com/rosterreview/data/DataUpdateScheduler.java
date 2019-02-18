package com.rosterreview.data;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Registers a scheduled event that periodically updates the data store
 * with recent player data from an external source.
 */

@Component
public class DataUpdateScheduler {

    private DataUpdater dataUpdater;

    public DataUpdateScheduler() {
        switch("${player.data.source}") {
            case "pfr": dataUpdater = new PfrDataUpdater();
            break;
            default: dataUpdater = new PfrDataUpdater();
        }
    }

    /**
     * Schedules a recurring event that updates the data store with recent
     * player data from a configured external source.
     */
    @Scheduled(cron = "${player.data.update.schedule}", zone = "${player.data.update.timezone}")
    public void schedulePlayerDataUpdate() {
        dataUpdater.updateData();
    }
}