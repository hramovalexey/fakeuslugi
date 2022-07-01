package com.fakeuslugi.controller;

import com.fakeuslugi.controller.dto.CustomerDto;
import com.fakeuslugi.controller.dto.OrderDto;
import com.fakeuslugi.seasonservice.OrderService;
import com.fakeuslugi.seasonservice.dao.ProvidedService;
import com.fakeuslugi.security.Authority;
import com.fakeuslugi.security.dao.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/service")
@Slf4j
public class ServiceController extends AbstractController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "order", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> order(@Valid @RequestBody OrderDto orderRequest, HttpServletRequest httpServletRequest) {
        Customer customer = findCustomer(httpServletRequest);
        ProvidedService order = orderService.createOrder(orderRequest, customer);
        return ResponseEntity.ok().build();
    }
}
