package com.fakeuslugi.seasonservice.dao;

import com.fakeuslugi.daoutil.QueryFromWhereUniqueProcessor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@Transactional
@Slf4j
public class SeasonServiceDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private StatusHistoryDao statusHistoryDao;

    private QueryFromWhereUniqueProcessor<SeasonService, String> simpleQueryServiceStringUnique;
    private QueryFromWhereUniqueProcessor<SeasonService, Long> simpleQueryServiceLongUnique;

    // Init query processors
    @PostConstruct
    private void postConstruct() {
        this.simpleQueryServiceStringUnique = new QueryFromWhereUniqueProcessor<>(sessionFactory);
        this.simpleQueryServiceLongUnique = new QueryFromWhereUniqueProcessor<>(sessionFactory);
    }

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
        log.debug("providedService created: " + providedService.toString());
        return providedService;
    }

    public SeasonService findByName(String serviceRequest) {
       //  Query<SeasonService> query = sessionFactory.getCurrentSession().createQuery("from SeasonService as s where s.name = :serviceRequest");
        // query.setParameter("serviceRequest", serviceRequest);
        // SeasonService result = query.uniqueResult();
        return simpleQueryServiceStringUnique.processQuery(SeasonService.class, "name", serviceRequest);
    }

    public SeasonService findById(long idRequest) {

        // Query<SeasonService> query = sessionFactory.getCurrentSession().createQuery("from SeasonService as s where s.id = :idRequest");
        // query.setParameter("idRequest", idRequest);
        // SeasonService result = query.uniqueResult();
        return simpleQueryServiceLongUnique.processQuery(SeasonService.class, "id", idRequest);
    }

    public long countOrdersByServiceId(long serviceId) {
        Query<Long> query = sessionFactory.getCurrentSession().createQuery("select count(ps) from ProvidedService as ps where ps.seasonService.id = :serviceId");
        query.setParameter("serviceId", serviceId);
        long count = query.uniqueResult();
        return count;
    }

    public List<SeasonService> getSeasonServiceList() {
        Query<SeasonService> query = sessionFactory.getCurrentSession().createQuery("from SeasonService");
        List<SeasonService> resultList = query.getResultList();
        return resultList;
    }

    public List<ProvidedService> getOrderList(long customerId, long statusId) {
        Query<ProvidedService> query = sessionFactory.getCurrentSession()
                .createQuery("select ps from ProvidedService as ps, StatusHistory as sh where sh.providedService.id = ps.id and ps.customer.id = :customerId and sh.status.id = :statusId order by sh.timestamp desc");
        query.setParameter("statusId", statusId);
        query.setParameter("customerId", customerId);
        List<ProvidedService> resultList = query.getResultList();
        return resultList;
    }


}
