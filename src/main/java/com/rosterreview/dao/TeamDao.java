package com.rosterreview.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rosterreview.entity.Team;

/**
 * A {@link Repository} for {@link Team} data.
 */
@Repository
public class TeamDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieves the {@link Team} with attributes that matches the indicated parameters.
     *
     * @param season    the season the team competed in
     * @param league    the league the team belonged to
     * @param location  the location of the team
     * @param name      the team's name
     * @return          the Team that uniquely matches the indicated parameters
     */
    public Team getTeam(Integer season, String league, String location, String name) {
        String hql = "FROM Team t WHERE t.season = :season AND t.league = :league AND "
                + "t.location = :location AND t.name = :name";

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Team> query = session.createQuery(hql, Team.class);
        query.setParameter("season", season);
        query.setParameter("league", league);
        query.setParameter("location", location);
        query.setParameter("name", name);

        List<Team> results = query.getResultList();
        Team team = null;
        if (!results.isEmpty()) {
            team = results.get(0);
        }

        return team;
    }

    /**
     * Retrieves the {@link Team} from the specified season that has the indicated pfrTeamAbbrev.
     *
     * @param pfrTeamAbbrev  the pfr team abbreviation to match against
     * @param season         the season the team competed in
     * @return               the Team that uniquely matches the indicated parameters
     */
    public Team getTeamWithPfrAbbrev(String pfrTeamAbbrev, Integer season) {
        String hql = "FROM Team t WHERE t.pfrAbbrev = :pfrTeamAbbrev AND t.season = :season";

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Team> query = session.createQuery(hql, Team.class);
        query.setParameter("pfrTeamAbbrev", pfrTeamAbbrev);
        query.setParameter("season", season);

        List<Team> results = query.getResultList();
        Team team = null;
        if (!results.isEmpty()) {
            team = results.get(0);
        }

        return team;
    }

    /**
     * Retrieves all unique football team locations throughout history.
     *
     * @return  a list of all football team locations
     */
    public List<String> getTeamLocations() {
        String hql = "SELECT DISTINCT t.location FROM Team t";

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<String> query = session.createQuery(hql, String.class);

        return query.getResultList();
    }
}
