package com.fakeuslugi.seasonservice;

import com.fakeuslugi.EmailService;
import com.fakeuslugi.controller.dto.OrderDtoRequest;
import com.fakeuslugi.controller.dto.OrderDtoResponse;
import com.fakeuslugi.controller.dto.ServiceDtoResponse;
import com.fakeuslugi.seasonservice.dao.*;
import com.fakeuslugi.seasonservice.exception.SeasonServiceException;
import com.fakeuslugi.security.dao.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

// Handling season services ordered by customers
@Service
public class OrderService {
    @Autowired
    private SeasonServiceDao seasonServiceDao;

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private StatusHistoryDao statusHistoryDao;

    @Autowired
    private EmailService emailService;

    @Value("${default_creation_comment}")
    private String DEFAULT_CREATION_COMMENT;

    @Transactional
    public ProvidedService createOrder(OrderDtoRequest orderDtoRequest, Customer customer) {
        SeasonService seasonService = seasonServiceDao.findById(orderDtoRequest.getServiceId());
        if (seasonService == null) {
            throw new SeasonServiceException("Such service doesn`t exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        long qtyProvidedServices = seasonServiceDao.countOrdersByServiceId(orderDtoRequest.getServiceId());
        if (qtyProvidedServices >= seasonService.getServiceLimit()) {
            // TODO send email too
            throw new SeasonServiceException("Limit of provided services exceeded. Check your email", HttpStatus.FORBIDDEN);
        }
        ProvidedService providedService = new ProvidedService();
        providedService.setCustomer(customer);
        providedService.setSeasonService(seasonService);
        providedService.setUserComment(orderDtoRequest.getUserComment());
        StatusHistory statusHistory = new StatusHistory(ZonedDateTime.now(), statusDao.getInitialStatus());
        statusHistory.setExecutorComment(DEFAULT_CREATION_COMMENT);
        statusHistory.setProvidedService(providedService);
        providedService = seasonServiceDao.createProvidedService(providedService);
        statusHistoryDao.createStatusHistory(statusHistory);
        String email = customer.getEmail();
        long orderId = providedService.getId();
        Executors.newSingleThreadExecutor().execute(() -> sendSuccessEmail(email, orderId));
        // TODO try to throw unchecked exception from here

        // providedService.getStatusHistory().add(statusHistory);
        return providedService;
    }

    private String createSuccessEmailMessage(long orderId) {
        return "Услуга оказана. Номер вашей заявки: " + orderId;
    }

    private void sendSuccessEmail(String email, long orderId) {
        emailService.sendSimpleMessage(email, "fakeuslugi status", createSuccessEmailMessage(orderId));
    }

    public List<ServiceDtoResponse> getServiceList() {
        List<SeasonService> seasonServiceList = seasonServiceDao.getSeasonServiceList();
        return mapSeasonServiceToServiceDtoResponce(seasonServiceList);
    }

    @Transactional
    public List<OrderDtoResponse> getOrderList(Customer customer) {
        long statusId = statusDao.getInitialStatus().getId();
        List<ProvidedService> orderList = seasonServiceDao.getOrderList(customer.getId(), statusId);
        return mapProvidedServiceToOrderDtoResponse(orderList);
    }

    private List<ServiceDtoResponse> mapSeasonServiceToServiceDtoResponce(List<SeasonService> seasonServiceList) {
        ArrayList<ServiceDtoResponse> serviceDtoResponseList = new ArrayList<>(seasonServiceList.size());
        for (SeasonService seasonService : seasonServiceList) {
            long ordersQty = seasonServiceDao.countOrdersByServiceId(seasonService.getId());
            long currentLimit = seasonService.getServiceLimit() - ordersQty;
            serviceDtoResponseList.add(new ServiceDtoResponse(seasonService, currentLimit));
        }
        return serviceDtoResponseList;
    }

    public List<OrderDtoResponse> mapProvidedServiceToOrderDtoResponse(List<ProvidedService> providedServiceList) {
        return providedServiceList.stream()
                .map(serviceItem -> new OrderDtoResponse(serviceItem, serviceItem.getCustomer(), serviceItem.getStatusHistory()))
                .collect(Collectors.toList());
    }

}
