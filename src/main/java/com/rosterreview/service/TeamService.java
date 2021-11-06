package com.rosterreview.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosterreview.dao.TeamDao;
import com.rosterreview.entity.Team;

/**
 * A {@link Service} for {@link Team} data and operations.
 */

@Service
public class TeamService {

    @Autowired
    private TeamDao teamDao;

    /**
     * Retrieves the {@link Team} with attributes that matches the indicated
     * parameters.
     *
     * @param season    the season the team competed in
     * @param league    the league the team belonged to
     * @param location  the location of the team
     * @param name      the team's name
     * @return          the Team that uniquely matches the indicated parameters
     */
    public Team getTeam(Integer season, String league, String location, String name) {
        return teamDao.getTeam(season, league, location, name);
    }

    /**
     * Retrieves the {@link Team} from the specified season that has the
     * indicated pfrTeamAbbrev.
     *
     * @param pfrTeamAbbrev  the pfr team abbreviation to match against
     * @param season         the season the team competed in
     * @return               the Team that uniquely matches the indicated parameters
     */
    public Team getTeamWithPfrAbbrev(String pfrTeamAbbrev, Integer season) {
        return teamDao.getTeamWithPfrAbbrev(pfrTeamAbbrev, season);
    }

    /**
     * Retrieves a sorted (ascending) list of all unique football team locations
     * throughout history.
     *
     * @return  a list of all football team locations
     */
    public List<String> getTeamLocations() {
        List<String> locations = teamDao.getTeamLocations();
        Collections.sort(locations);

        return locations;
    }
}
