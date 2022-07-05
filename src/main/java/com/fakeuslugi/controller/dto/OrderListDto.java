package com.fakeuslugi.controller.dto;

import com.fakeuslugi.seasonservice.dao.OrderInfo;
import com.fakeuslugi.seasonservice.dao.StatusHistory;
import com.fakeuslugi.security.dao.Customer;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderListDto {
    private CustomerDtoResponse customer;
    private List<OrderDtoResponse> orders;

    public OrderListDto(List<OrderDtoResponse> orderDtoResponseList, Customer customer) {
        this.customer = new CustomerDtoResponse(customer);
        this.orders = orderDtoResponseList;
    }
}
