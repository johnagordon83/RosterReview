package com.rosterreview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosterreview.dao.TestDao;

@Service
public class TestService {

    @Autowired
    private TestDao testDao;

    public void clearPlayerTestData() {
        testDao.clearPlayerTestData();
    }
}