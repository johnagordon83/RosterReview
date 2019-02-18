package com.rosterreview.data;

/**
 * Each implementer of this interface provides a mechanism to
 * acquire player data from a specific external data source.
 */
public interface DataUpdater {

    /**
     * Updates the data store with recent player data retrieved from a
     * configured external source.
     */
    public void updateData();
}
