package com.fakeuslugi.seasonservice.dao;

/**
 * Serves for weak connectivity and securing the contract during mapping process between
 * {@link com.fakeuslugi.seasonservice.dao.SeasonService} object and {@link com.fakeuslugi.controller.dto.ServiceDtoResponse}
 */
public interface SeasonServiceInfo {
    long getId();
    String getName();
    long getServiceLimit();
}
