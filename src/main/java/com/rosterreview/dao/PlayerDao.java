package com.rosterreview.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rosterreview.entity.Player;

/**
 * A data access class for {@Link Player} data.
 */

@Transactional
@Repository
public class PlayerDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieve {@Link Player} profile information from the
     * {@Link DataSource}.
     *
     * @param id  The id corresponding to the requested player.
     * @return    A Player containing profile information or
     *            <code>null</code> if a player matching the id argument
     *            could not be found.
     */
    public Player getPlayer(String id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Player.class, id);
    }
}