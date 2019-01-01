package com.rosterreview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosterreview.dao.PlayerDao;
import com.rosterreview.entity.Player;

/**
 * A service class for {@Link Player} data and operations.
 */

@Service
public class PlayerService {

    @Autowired
    private PlayerDao playerDao;

    /**
     * Request {@Link Player} profile information.
     *
     * @param id    The id corresponding to the requested player.
     * @return      A Player object containing profile information or
     *              <code>null</code> if no player matches the id argument.
     */
    public Player getPlayer(String id) {
        return playerDao.getPlayer(id);
    }
}