package com.rosterreview.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This implementation of the {@link DataUpdater} retrieves data from
 * https://www.pro-football-reference.com.
 */
public class PfrDataUpdater implements DataUpdater {

    private Logger log = LoggerFactory.getLogger(PfrDataUpdater.class);

    /**
     * Updates the data store with recent player data retrieved from
     * https://www.pro-football-reference.com.
     */
    @Override
    public void updateData() {
        // TODO Auto-generated method stub
        log.info("Updating Data...");
    }
}
