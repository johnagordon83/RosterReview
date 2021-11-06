package com.rosterreview.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rosterreview.entity.Player;

/**
 * A {@link Repository} for {@link Player} data operations.
 */

@Repository
public class PlayerDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger LOG = LoggerFactory.getLogger(PlayerDao.class);

    /**
     * Retrieves the {@link Player} with the specified id from the repository.
     *
     * @param id  the id that uniquely identifies the requested player
     * @return    a fully initialized Player object or <code>null</code> if a
     *            matching player could not be found
     */
    @Transactional
    public Player getPlayer(String id) {
        final String hql = "FROM Player p JOIN FETCH p.positions WHERE p.id = :id";

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Player> query = session.createQuery(hql, Player.class);
        query.setParameter("id", id);

        List<Player> results = query.getResultList();
        Player player = null;

        if (results.size() > 0) {
            player = results.get(0);
            Hibernate.initialize(player.getStatistics());
            Hibernate.initialize(player.getDraftPicks());
        }

        return player;
    }

    /**
     * Retrieves the {@link Player} with the specified Pro-Football-Reference id
     * (pfrId) from the repository.
     *
     * @param pfrId  the PFR id that uniquely identifies the requested player
     * @return       an uninitialized Player object or <code>null</code> if a
     *               matching player could not be found.
     */
    public Player getPlayerByPfrId(String pfrId) {
        final String hql = "FROM Player p WHERE p.pfrId = :pfrId";

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Player> query = session.createQuery(hql, Player.class);
        query.setParameter("pfrId", pfrId);

        List<Player> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Persists the specified {@link Player} to the repository.
     *
     * @param player  the Player to persist
     */
    public void persistPlayer(Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(player);
    }

    /**
     * Retrieves all {@link Player Players} from the repository that have ids
     * that begin with the indicated prefix argument.
     *
     * @param idPrefix  the prefix to compare player ids against
     * @return          all players with matching id prefixes
     */
    public List<String> getPlayerIdsWithMatchingPrefix(String idPrefix) {
        final String hql = "SELECT p.id FROM Player p WHERE p.id like :idPrefix";

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<String> query = session.createQuery(hql, String.class);
        query.setParameter("idPrefix", idPrefix + '%');

        return query.getResultList();
    }

    /**
     * Retrieves all {@link Player Players} from the respository that have a name
     * that is similar to the name argument.
     * <p>
     * The name argument could be a first name, nickname, last name, or full name.
     * Therefore, the name argument is broken into parts (delimited by whitespace)
     * which are then compared to the names of players in the repository. The player
     * will be included in the result if any of the following are true:
     *
     * <ul>
     * <li>The player's first name starts with the first part of the name argument.</li>
     * <li>The player's nickname starts with the first part of the name argument.</li>
     * <li>The player's last name occurs anywhere within the last part of the name argument.
     * </li>
     * </ul>
     *
     * @param name  a user provided name to compare against players persisted in
     *              the repository
     * @return      a list of uninitialized players that have a name similar to
     *              the argument
     */
    @Transactional
    public List<Player> findPlayersWithNameLike(String name) {
        String[] nameComponents = name.split("\\s");

        final String hql = "FROM Player p WHERE p.firstName LIKE CONCAT(:firstName, '%') "
                + "OR p.nickname LIKE CONCAT(:firstName, '%') "
                + "OR p.lastName LIKE CONCAT(:firstName, '%') "
                + "OR p.lastName LIKE CONCAT('%', :lastName, '%')";

        Session session = sessionFactory.getCurrentSession();

        TypedQuery<Player> query = session.createQuery(hql, Player.class);
        query.setParameter("firstName", nameComponents[0]);
        query.setParameter("lastName", nameComponents[nameComponents.length - 1]);

        return query.getResultList();
    }
}