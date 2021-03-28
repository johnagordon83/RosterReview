package com.rosterreview.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosterreview.dao.TeamDao;
import com.rosterreview.entity.Team;

/**
 * A service class for {@Link Team} data and operations.
 */

@Service
public class TeamService {

    @Autowired
    private TeamDao teamDao;

    public Team getTeam(Integer season, String league, String location, String name) {
        return teamDao.getTeam(season, league, location, name);
    }

    public Team getTeamWithPfrAbbrev(String pfrTeamAbbrev, Integer season) {
        return teamDao.getTeamWithPfrAbbrev(pfrTeamAbbrev, season);
    }

    public List<String> getNflLocations() {
        List<String> locations = teamDao.getNflLocations();
        Collections.sort(locations);

        return locations;
    }
}
