package com.fakeuslugi.controller.dto;

import com.fakeuslugi.security.dao.CustomerInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CustomerDtoResponse {
    private final String name;
    private final String secondName;
    private final String thirdName;
    private final String email;

    public CustomerDtoResponse(CustomerInfo customer) {
        this.name = customer.getName();
        this.secondName = customer.getSecondName();
        this.thirdName = customer.getThirdName();
        this.email = customer.getEmail();
    }
}
