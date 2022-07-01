package com.fakeuslugi.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@Data
public class OrderDto {

    @PositiveOrZero(message = "id cannot be negative")
    private long serviceId;

    private String userComment;

}
