package com.fakeuslugi.seasonservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Repository
@Transactional
@Slf4j
public class StatusHistoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private StatusDao statusDao;

    public StatusHistory createStatusHistory(StatusHistory statusHistory) {
        sessionFactory.getCurrentSession().save(statusHistory);
        return statusHistory;
    }

    /*public StatusHistory createStatusHistory(Status status, String executorComment) {

        // StatusHistory statusHistory = new StatusHistory(ZonedDateTime.now(), status);
        statusHistory.setExecutorComment(executorComment);
        sessionFactory.getCurrentSession().save(statusHistory);
        return statusHistory;
    }*/


}
