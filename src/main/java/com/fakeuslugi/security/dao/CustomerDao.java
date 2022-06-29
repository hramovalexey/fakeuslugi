package com.fakeuslugi.security.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Slf4j
public class CustomerDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Customer createCustomer(Customer newCustomer) {


        sessionFactory.getCurrentSession().save(newCustomer);
        log.debug("New user created " + newCustomer.toString());
        return newCustomer;
    }

    public Customer findByEmail(String emailRequest) {
        Query<Customer> query = sessionFactory.getCurrentSession().createQuery("from Customer as u where u.email = :emailRequest");
        query.setParameter("emailRequest", emailRequest);
        Customer result = query.uniqueResult();
        return result;
    }
}
