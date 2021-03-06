package com.rosterreview.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rosterreview.entity.Player;
import com.rosterreview.entity.PlayerSeason;

/**
 * A data access class for {@Link Player} data.
 */

@Repository
public class PlayerDao {

    @Autowired
    private SessionFactory sessionFactory;

    protected static Logger log = LoggerFactory.getLogger(PlayerDao.class);

    /**
     * Retrieve {@Link Player} profile information from the {@Link DataSource}
     * for a player with an id matching the passed argument.
     *
     * @param id  The id that uniquely identifies the requested player.
     * @return    A Player object containing profile information or <code>null</code>
     *            if a player matching the id argument could not be found.
     */
    @Transactional
    public Player getPlayer(String id) {

        Session session = sessionFactory.getCurrentSession();
        //return session.get(Player.class, id);

        Criteria criteria = session.createCriteria(Player.class);
        criteria.add(Expression.eq("id", id));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Player> list = criteria.list();

        /*
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Player> cr = cb.createQuery(Player.class);
        Root<Player> root = cr.from(Player.class);
        cr.select(root).distinct(true);

        Query<Player> query = session.createQuery(cr);
        List<Player> results = query.getResultList();
        */

        Player player = null;
        if (!list.isEmpty()) {
            player = list.get(0);
        }

        return player;
    }

    /**
     * Retrieve {@Link Player} profile information from the {@Link DataSource}
     * for a player with a pfrId matching the passed argument.
     *
     * @param pfrId  The pfrId that uniquely identifies the requested player.
     * @return       A Player object containing profile information or
     *               <code>null</code> if a player matching the pfrId
     *               argument could not be found.
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

    public void persistPlayer(Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(player);
    }

    public void mergeSeason(PlayerSeason season) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(season);
    }

    public List<String> getPlayerIdsWithMatchingPrefix(String idPrefix) {
        String hql = "SELECT p.id FROM Player p WHERE p.id like :idPrefix";

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<String> query = session.createQuery(hql, String.class);
        query.setParameter("idPrefix", idPrefix + '%');

        return query.getResultList();
    }

 /*   public void clearPlayerStatistics(Player player) {
        String hql = "DELETE FROM PlayerSeason s WHERE s.player_id = :playerId";

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<String> query = session.createQuery(hql, String.class);
        query.setParameter("idPrefix", idPrefix + '%');

        query.
    }*/
}