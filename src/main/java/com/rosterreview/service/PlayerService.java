package com.rosterreview.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosterreview.dao.PlayerDao;
import com.rosterreview.entity.Player;

/**
 * A {@link Service} for {@link Player} data operations.
 */

@Service
public class PlayerService {

    @Autowired
    private PlayerDao playerDao;

    private static final Logger LOG = LoggerFactory.getLogger(PlayerService.class);

    /**
     * Retrieves the {@link Player} with the specified id.
     *
     * @param id  the id that uniquely identifies the requested player
     * @return    a fully initialized Player object or <code>null</code> if a
     *            matching player could not be found
     */
    public Player getPlayer(String id) {
        return playerDao.getPlayer(id);
    }

    /**
     * Retrieves the {@link Player} with the specified Pro-Football-Reference
     * id (pfrId).
     *
     * @param pfrId  the pfrId that uniquely identifies the requested player
     * @return       an uninitialized Player object or <code>null</code> if a
     *               matching player could not be found.
     */
    public Player getPlayerByPfrId(String pfrId) {
        return playerDao.getPlayerByPfrId(pfrId);
    }

    /**
     * Retrieves {@link Player Players} with names similar to the passed 'name'
     * argument.
     * <p>
     * Player data will be returned in order of best match first. The maximum
     * number of players returned is constrained by the optional limit argument.
     * If the number of players returned must be truncated, the lowest scored
     * matches will be discarded first. If one and only one player's name exactly
     * matches the passed name, only that player will be returned in the result
     * list.
     *
     * @param name   the user provided name to compare player records against
     * @param limit  the maximum number of players to return in the result
     * @return       a list of uninitialized players with names similar to the
     *               argument
     */
    public List<Player> findPlayersWithNameLike(String name, Integer limit) {
        List<Player> playerList = playerDao.findPlayersWithNameLike(name);

        /*
         * if one or fewer matches are returned from the respository, return the
         * results immediately as there is no need to evaluate and compare match
         * quality.
         */
        if (playerList.size() <= 1) {
            return playerList;
        }

        /*
         * We don't know if the user sent the player's full name, first name, last
         * name, etc. Therefore, calculate similarity index separately for each
         * possibility and use the score for the best match (lowest value). Add
         * weightings to slightly bias the results in favor of full name matches
         * over last name matches, and last name matches over first (or nickname)
         * matches.
         */
        Map<Player, Double> sortScores = new HashMap<Player, Double>();
        LevenshteinDistance ld = new LevenshteinDistance();
        for (Player player : playerList) {
            String fullName = player.getFirstName().concat(" ")
                    .concat(player.getLastName());
            String fullNickname = player.getNickname().concat(" ")
                    .concat(player.getLastName());

            double fullNameScore = ld.apply(name, fullName);
            double fullNicknameScore = ld.apply(name, fullNickname);
            double firstNameScore = ld.apply(name, player.getFirstName()) + 1.5;
            double nicknameScore = ld.apply(name, player.getNickname()) + 1.5;
            double lastNameScore = ld.apply(name, player.getLastName()) + 1.0;

            double[] scores = { fullNameScore, fullNicknameScore, firstNameScore,
                    nicknameScore, lastNameScore };
            double matchScore = Arrays.stream(scores).min().getAsDouble();

            sortScores.put(player, matchScore);
        }

        /*
         * Sort the matches best to worst (lowest to highest)
         * If the user provided a valid limit (non-null and > 0) to the number
         * of matches to return, trim the excess matches
         */
        limit = (limit != null && limit > 0) ? limit : Integer.MAX_VALUE;
        LinkedHashMap<Player, Double> topMatches = sortScores.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(limit).collect(Collectors.toMap(
                      Map.Entry::getKey,
                      Map.Entry::getValue,
                      (e1, e2) -> e1, LinkedHashMap::new));
        /*
         * If the best match is a perfect match and the second best is not,
         * return only the first match. Otherwise, return all remaining matches.
         */
        List<Map.Entry<Player, Double>> entries = new ArrayList<>(topMatches.entrySet());
        if (entries.get(0).getValue() == 0 && entries.get(1).getValue() > 0) {
            return new ArrayList<Player>(Arrays.asList(entries.get(0).getKey()));
        }

        return new ArrayList<Player> (topMatches.keySet());
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
     * @return           the newly created Player
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
     * Ids are composed of an alphabetic prefix followed by a two digit suffix.
     * The prefix is constructed from the first six letters of the last name,
     * followed by the first two letters of the first name.  The numeric suffix
     * will be the smallest positive integer that creates a unique id for the
     * player.
     *
     * @param firstName  the player's first name
     * @param lastName   the player's last name
     * @return           the player's id
     */
    private String generateNewPlayerId(String firstName, String lastName) {
        String truncLast = lastName.substring(0, Math.min(lastName.length(), 6));
        String truncFirst = firstName.substring(0, Math.min(firstName.length(), 2));
        String idPrefix = truncLast.concat(truncFirst).toLowerCase();
        String id = StringUtils.EMPTY;
        int idSuffix = 1;

        List<String> playerIds = playerDao.getPlayerIdsWithMatchingPrefix(idPrefix);

        do {
            id = idPrefix.concat(String.format("%02d", idSuffix));
            idSuffix++;
        } while (playerIds.contains(id));

        return id;
    }
}