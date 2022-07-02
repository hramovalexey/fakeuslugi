package com.fakeuslugi.seasonservice.dao;

import java.time.ZonedDateTime;

public interface StatusHistoryInfo {
    ZonedDateTime getTimestamp();
    String getStatusName();
    String getExecutorComment();
}
