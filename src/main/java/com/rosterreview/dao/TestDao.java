package com.rosterreview.dao;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TestDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void clearPlayerTestData() {
        String hql = "DELETE FROM Player";

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);

        query.executeUpdate();
    }
}
