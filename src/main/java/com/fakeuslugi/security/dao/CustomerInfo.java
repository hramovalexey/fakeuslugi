package com.fakeuslugi.security.dao;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Serves for weak connectivity and securing the contract during mapping process between
 * {@link com.fakeuslugi.security.dao.Customer} object and {@link com.fakeuslugi.controller.dto.CustomerDtoResponse}
 */
public interface CustomerInfo extends UserDetails {
    String getName();
    String getSecondName();
    String getThirdName();
    String getEmail();
}
