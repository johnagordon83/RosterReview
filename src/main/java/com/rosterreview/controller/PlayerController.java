package com.rosterreview.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rosterreview.entity.Player;
import com.rosterreview.service.PlayerService;

/**
 * A {@link RestController} that handles requests for player data.
 */

@RequestMapping("/api/player")
@RestController
public class PlayerController {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    private PlayerService playerService;

    /**
     * Request profile and statistical data for the {@link Player} specified by id.
     *
     * @param id  an identifier corresponding to the requested player.
     * @return    a player object or <code>null</code> if a matching player could
     *            not be found
     */
    @GetMapping(value = "/data")
    @ResponseStatus(HttpStatus.OK)
    public Player getPlayer(@RequestParam("id") String id) {
        return playerService.getPlayer(id);
    }

    /**
     * Retrieves profile information for {@link Player Players} with names
     * similar to the passed 'name' argument.
     *
     * @param name   the user provided name to compare player records against
     * @param limit  the maximum number of players to return in the result
     * @return       a list of players with names similar to the argument
     */
    @GetMapping(value = "/withNameLike")
    @ResponseStatus(HttpStatus.OK)
    public List<Player> findPlayersWithNameLike(@RequestParam("name") String name,
            @RequestParam(value = "limit", required = false) Integer limit) {
        return playerService.findPlayersWithNameLike(name, limit);
    }
}