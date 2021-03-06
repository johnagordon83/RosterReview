package com.rosterreview.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosterreview.dao.PlayerDao;
import com.rosterreview.entity.Player;
import com.rosterreview.entity.PlayerSeason;

/**
 * A service class for {@Link Player} data and operations.
 */

@Service
public class PlayerService {

    @Autowired
    private PlayerDao playerDao;

    protected static Logger log = LoggerFactory.getLogger(PlayerService.class);

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

    public Player getPlayerByPfrId(String pfrId) {
        return playerDao.getPlayerByPfrId(pfrId);
    }

    public void persistPlayer(Player player) {
        playerDao.persistPlayer(player);
    }

    public void mergeSeason(PlayerSeason season) {
        playerDao.mergeSeason(season);
    }

    public String generateNewPlayerId(String firstName, String lastName) {
        String truncLast = lastName.substring(0, Math.min(lastName.length(), 6));
        String truncFirst = firstName.substring(0, Math.min(firstName.length(), 2));
        String idPrefix = truncLast.concat(truncFirst).toLowerCase();
        String id = "";
        int idPostFix = 0;

        List<String> playerIds = playerDao.getPlayerIdsWithMatchingPrefix(idPrefix);

        do {
            idPostFix++;
            id = idPrefix.concat(String.format("%02d", idPostFix));
        } while (playerIds.contains(id));

        return id;
    }
}