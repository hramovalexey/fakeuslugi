package com.fakeuslugi.seasonservice;

import com.fakeuslugi.controller.dto.OrderDto;
import com.fakeuslugi.seasonservice.dao.*;
import com.fakeuslugi.seasonservice.exception.SeasonServiceException;
import com.fakeuslugi.security.dao.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

// Handling season services ordered by customers
@Service
public class OrderService {
    @Autowired
    private SeasonServiceDao seasonServiceDao;

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private StatusHistoryDao statusHistoryDao;

    @Value("${default_creation_comment}")
    private String DEFAULT_CREATION_COMMENT;

    @Transactional
    public ProvidedService createOrder(OrderDto orderDto, Customer customer) {
        SeasonService seasonService = seasonServiceDao.findById(orderDto.getServiceId());
        if (seasonService == null) {
            throw new SeasonServiceException("Such service doesn`t exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // TODO ADD USER COMMENT AND SEASON SERVICE!!!!
        ProvidedService providedService = new ProvidedService();
        providedService.setCustomer(customer);
        providedService.setSeasonService(seasonService);
        providedService.setUserComment(orderDto.getUserComment());
        StatusHistory statusHistory = new StatusHistory(ZonedDateTime.now(), statusDao.getInitialStatus());
        statusHistory.setExecutorComment(DEFAULT_CREATION_COMMENT);
        statusHistory.setProvidedService(providedService);
        providedService = seasonServiceDao.createProvidedService(providedService);
        statusHistory = statusHistoryDao.createStatusHistory(statusHistory);

        // TODO try to throw unchecked exception from here

        // providedService.getStatusHistory().add(statusHistory);
        return providedService;
    }

}
