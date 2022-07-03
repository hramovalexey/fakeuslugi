package com.fakeuslugi.seasonservice.dao;

import java.time.ZonedDateTime;

/**
 * Serves for weak connectivity and securing the contract during mapping process between
 * {@link com.fakeuslugi.seasonservice.dao.StatusHistory} object and {@link com.fakeuslugi.controller.dto.StatusHistoryDtoResponse}
 */
public interface StatusHistoryInfo {
    ZonedDateTime getTimestamp();
    String getStatusName();
    String getExecutorComment();
}
