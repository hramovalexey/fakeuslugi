package com.fakeuslugi.seasonservice.dao;

import java.time.ZonedDateTime;

/**
 * Serves for weak connectivity and securing the contract during mapping process between
 * {@link com.fakeuslugi.seasonservice.dao.ProvidedService} object and {@link com.fakeuslugi.controller.dto.OrderDtoResponse}
 */
public interface OrderInfo {
    ZonedDateTime getTimestamp(); // When order was started
    long getId();
    String getName();
    String getUserComment();

}
