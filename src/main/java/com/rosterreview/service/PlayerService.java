package com.rosterreview.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosterreview.dao.PlayerDao;
import com.rosterreview.entity.Player;

/**
 * A {@link Service} for {@link Player} data and operations.
 */

@Service
public class PlayerService {

    @Autowired
    private PlayerDao playerDao;

    protected static Logger log = LoggerFactory.getLogger(PlayerService.class);

    /**
     * Retrieves the {@link Player} with the specified id.
     *
     * @param id    the id that uniquely identifies the requested player
     * @return      a Player object or <code>null</code> if a matching player could
     *              not be found
     */
    public Player getPlayer(String id) {
        return playerDao.getPlayer(id);
    }

    /**
     * Retrieves the {@link Player} with the specified pfrId.
     *
     * @param pfrId  the pfrId that uniquely identifies the requested player
     * @return       a Player object or <code>null</code> if a matching player could
     *               not be found.
     */
    public Player getPlayerByPfrId(String pfrId) {
        return playerDao.getPlayerByPfrId(pfrId);
    }

    /**
     * Persists the specified {@link Player}.
     *
     * @param player  the Player to persist
     */
    public void persistPlayer(Player player) {
        playerDao.persistPlayer(player);
    }

    /**
     * Create a new persistant {@link Player} entity with a unique id generated
     * from the player's name.
     *
     * @param firstName  the player's first name
     * @param lastName   the player's last name
     * @return           the Player
     */
    public Player createPlayer(String firstName, String lastName) {
        String id = generateNewPlayerId(firstName, lastName);
        Player player = new Player(id);
        persistPlayer(player);

        return player;
    }

    /**
     * Generates a new unique id for a new {@link Player} entity.
     * <p>
     * Ids are composed of an alphabetic prefix followed by a two digit postfix.
     * The prefix is constructed from the first six letters of the last name, followed
     * by the first two letters of the first name.  The numeric postfix will be the
     * smallest positive integer that creates a unique id for the player.
     *
     * @param firstName  the player's first name
     * @param lastName   the player's last name
     * @return           the player's id
     */
    private String generateNewPlayerId(String firstName, String lastName) {
        String truncLast = lastName.substring(0, Math.min(lastName.length(), 6));
        String truncFirst = firstName.substring(0, Math.min(firstName.length(), 2));
        String idPrefix = truncLast.concat(truncFirst).toLowerCase();
        String id = "";
        int idPostFix = 1;

        List<String> playerIds = playerDao.getPlayerIdsWithMatchingPrefix(idPrefix);

        do {
            id = idPrefix.concat(String.format("%02d", idPostFix));
            idPostFix++;
        } while (playerIds.contains(id));

        return id;
    }
}