package com.fakeuslugi.controller.dto;

import com.fakeuslugi.seasonservice.dao.StatusHistory;
import com.fakeuslugi.seasonservice.dao.StatusHistoryInfo;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
public class StatusHistoryDtoResponse {
    // private final String timestamp;
    private final Date date;
    private final String statusName;
    private final String executorComment;

    public StatusHistoryDtoResponse(StatusHistoryInfo statusHistory) {
        this.date = Date.from(statusHistory.getTimestamp().toInstant());
        this.statusName = statusHistory.getStatusName();
        this.executorComment = statusHistory.getExecutorComment();
    }
}
