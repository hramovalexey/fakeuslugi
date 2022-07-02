package com.fakeuslugi.controller.dto;

import com.fakeuslugi.seasonservice.dao.StatusHistory;
import com.fakeuslugi.seasonservice.dao.StatusHistoryInfo;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class StatusHistoryDtoResponse {
    private final String timestamp;
    private final String statusName;
    private final String executorComment;

    public StatusHistoryDtoResponse(StatusHistoryInfo statusHistory) {
        this.timestamp = statusHistory.getTimestamp().toString();
        this.statusName = statusHistory.getStatusName();
        this.executorComment = statusHistory.getExecutorComment();
    }
}
