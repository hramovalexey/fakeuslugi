package com.fakeuslugi.seasonservice.dao;

import com.fakeuslugi.security.dao.Customer;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;

@Repository
@Transactional
@Slf4j
public class SeasonServiceDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private StatusHistoryDao statusHistoryDao;

/*    @Value("${default_creation_comment}")
    private String DEFAULT_CREATION_COMMENT;*/

    /**
    * Check if SeasonService with given service name exists
    * If it doesn`t exist then create new SeasonService
    * If SeasonService exists with different limit then update limit
    */
    public SeasonService tryCreateSeasonService(String serviceName, long serviceLimit) {
        SeasonService serviceChecked = findByName(serviceName);
        if (serviceChecked == null) {
            SeasonService seasonService = new SeasonService(serviceName, serviceLimit);
            sessionFactory.getCurrentSession().save(seasonService);
            log.info("New season service to database added: " + seasonService.toString());
            return seasonService;
        } else if (serviceChecked.getServiceLimit() != serviceLimit) {
            serviceChecked.setServiceLimit(serviceLimit);
            log.info("Season service updated: " + serviceChecked.toString());
        }
        return serviceChecked;
    }

    public ProvidedService createProvidedService(ProvidedService providedService) {
        // StatusHistory statusHistory = statusHistoryDao.createStatusHistory(status, DEFAULT_CREATION_COMMENT);

        /*providedService.getStatusHistory().add(statusHistory);
        providedService.setCustomer(customer);
        providedService.*/

        sessionFactory.getCurrentSession().save(providedService);
        return providedService;
    }

    public SeasonService findByName(String serviceRequest) {
        Query<SeasonService> query = sessionFactory.getCurrentSession().createQuery("from SeasonService as s where s.name = :serviceRequest");
        query.setParameter("serviceRequest", serviceRequest);
        SeasonService result = query.uniqueResult();
        return result;
    }

    public SeasonService findById(long idRequest) {
        Query<SeasonService> query = sessionFactory.getCurrentSession().createQuery("from SeasonService as s where s.id = :idRequest");
        query.setParameter("idRequest", idRequest);
        SeasonService result = query.uniqueResult();
        return result;
    }
}
