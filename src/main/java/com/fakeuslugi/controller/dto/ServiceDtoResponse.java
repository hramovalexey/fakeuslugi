package com.fakeuslugi.controller.dto;

import com.fakeuslugi.seasonservice.dao.SeasonServiceInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
public class ServiceDtoResponse {
    private final long id;
    private final String name;
    private final long totalLimit;
    private final long currentLimit;

    public ServiceDtoResponse(SeasonServiceInfo seasonService, long currentLimit) {
        this.id = seasonService.getId();
        this.name = seasonService.getName();
        this.totalLimit = seasonService.getServiceLimit();
        this.currentLimit = currentLimit;
    }
}
