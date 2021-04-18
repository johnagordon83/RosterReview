package com.rosterreview.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rosterreview.entity.Player;

/**
 * A {@link Repository} for {@link Player} data.
 */

@Repository
public class PlayerDao {

    @Autowired
    private SessionFactory sessionFactory;

    protected static Logger log = LoggerFactory.getLogger(PlayerDao.class);

    /**
     * Retrieves the {@link Player} with the specified id.
     *
     * @param id  the id that uniquely identifies the requested player
     * @return    a Player object or <code>null</code> if a matching player could
     *            not be found
     */
    @Transactional
    public Player getPlayer(String id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Player.class, id);
    }

    /**
     * Retrieves the {@link Player} with the specified pfrId.
     *
     * @param pfrId  the pfrId that uniquely identifies the requested player
     * @return       a Player object or <code>null</code> if a matching player could
     *               not be found.
     */
    public Player getPlayerByPfrId(String pfrId) {
        String hql = "FROM Player p WHERE p.pfrId = :pfrId";

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Player> query = session.createQuery(hql, Player.class);
        query.setParameter("pfrId", pfrId);

        List<Player> results = query.getResultList();
        Player player = null;
        if (!results.isEmpty()) {
            player = results.get(0);
        }

        return player;
    }

    /**
     * Persists the specified {@link Player}.
     *
     * @param player  the Player to persist
     */
    public void persistPlayer(Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(player);
    }

    /**
     * Retrieves all {@link Player Players} that have ids that being with
     * the indicated prefix.
     *
     * @param idPrefix  the prefix to match player ids against
     * @return          all players with matching id prefixes
     */
    public List<String> getPlayerIdsWithMatchingPrefix(String idPrefix) {
        String hql = "SELECT p.id FROM Player p WHERE p.id like :idPrefix";

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<String> query = session.createQuery(hql, String.class);
        query.setParameter("idPrefix", idPrefix + '%');

        return query.getResultList();
    }
}