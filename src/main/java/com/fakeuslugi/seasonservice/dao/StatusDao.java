package com.fakeuslugi.seasonservice.dao;

import com.fakeuslugi.security.dao.Customer;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// TODO Make template method for all DAOs
@Repository
@Transactional
@Slf4j
public class StatusDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Value("${default_initial_status}")
    private String DEFAULT_INITIAL_STATUS;

    public Status tryCreateStatus(String statusName) {
        Status statusChecked = findByName(statusName);
        if (statusChecked == null) {
            Status status = new Status(statusName);
            sessionFactory.getCurrentSession().save(status);
            log.info("New status to database added: " + status.toString());
            return status;
        }
        return statusChecked;
    }

    public Status findByName(String statusRequest) {
        Query<Status> query = sessionFactory.getCurrentSession().createQuery("from Status as s where s.name = :statusRequest");
        query.setParameter("statusRequest", statusRequest);
        Status result = query.uniqueResult();
        return result;
    }

    public Status getInitialStatus() {
        return findByName(DEFAULT_INITIAL_STATUS);
    }

}
