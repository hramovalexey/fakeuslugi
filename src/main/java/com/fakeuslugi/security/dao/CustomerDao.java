package com.fakeuslugi.security.dao;

import com.fakeuslugi.daoutil.QueryFromWhereUniqueProcessor;
import com.fakeuslugi.seasonservice.dao.SeasonService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Repository
@Transactional
@Slf4j
public class CustomerDao {
    @Autowired
    private SessionFactory sessionFactory;

    private QueryFromWhereUniqueProcessor<Customer, String> simpleQueryCustomerStringUnique;

    @PostConstruct
    private void postConstruct() {
        this.simpleQueryCustomerStringUnique = new QueryFromWhereUniqueProcessor<>(sessionFactory);
    }

    public Customer createCustomer(Customer newCustomer) {
        sessionFactory.getCurrentSession().save(newCustomer);
        log.debug("New customer created " + newCustomer.toString());
        return newCustomer;
    }

    public Customer findByEmail(String emailRequest) {
        return simpleQueryCustomerStringUnique.processQuery(Customer.class, "email", emailRequest);
    }
}
