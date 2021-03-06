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
 * PlayerController is an annotated Spring Controller class that handles
 * requests for player data.
 */

@RequestMapping("/player")
@RestController
public class PlayerController {

    protected static Logger log = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    private PlayerService playerService;

    /**
     * Request profile information for a specified {@Link Player}.
     *
     * @param id  A string identifier corresponding to the requested Player.
     * @return    A JSON representation of the requested Player.
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Player getPlayer(@PathVariable("id") String id) {
        Player player = playerService.getPlayer(id);

        return player;
    }
}