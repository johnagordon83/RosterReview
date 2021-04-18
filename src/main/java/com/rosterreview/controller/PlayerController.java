package com.rosterreview.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rosterreview.entity.Player;
import com.rosterreview.service.PlayerService;

/**
 * A {@link RestController} that handles requests for player data.
 */

@RequestMapping("/player")
@RestController
public class PlayerController {

    private static Logger log = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    private PlayerService playerService;

    /**
     * Request profile information for the {@link Player} specified by id.
     *
     * @param id  an identifier corresponding to the requested player.
     * @return    the requested player object or <code>null</code> if a
     *            matching player could not be found
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player getPlayer(@PathVariable("id") String id) {
        return playerService.getPlayer(id);
    }
}