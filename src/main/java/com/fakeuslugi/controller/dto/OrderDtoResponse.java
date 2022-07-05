package com.fakeuslugi.controller.dto;

import com.fakeuslugi.seasonservice.dao.OrderInfo;
import com.fakeuslugi.seasonservice.dao.SeasonServiceInfo;
import com.fakeuslugi.seasonservice.dao.StatusHistory;
import com.fakeuslugi.seasonservice.dao.StatusHistoryInfo;
import com.fakeuslugi.security.dao.CustomerInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderDtoResponse {
    private final Date date;
    private final long id;
    private final String name;
    private final String userComment;
    private final List<StatusHistoryDtoResponse> statusHistory;

    public OrderDtoResponse(OrderInfo providedService, List<StatusHistory> statusHistoryList) {
        this.date = Date.from(providedService.getTimestamp().toInstant());
        this.id = providedService.getId();
        this.name = providedService.getName();
        this.userComment = providedService.getUserComment();
        this.statusHistory = createHistoryList(statusHistoryList);
    }

    private List<StatusHistoryDtoResponse> createHistoryList(List<StatusHistory> statusHistoryList) {
        return statusHistoryList.stream().map(StatusHistoryDtoResponse::new).collect(Collectors.toList());
    }
}
