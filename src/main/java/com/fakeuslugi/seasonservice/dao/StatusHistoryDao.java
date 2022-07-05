package com.fakeuslugi.seasonservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Slf4j
public class StatusHistoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    public StatusHistory createStatusHistory(StatusHistory statusHistory) {
        sessionFactory.getCurrentSession().save(statusHistory);
        log.debug("StatusHistory entity created: " + statusHistory.toString());
        return statusHistory;
    }
}
