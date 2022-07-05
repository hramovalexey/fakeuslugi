package com.fakeuslugi.controller;

import com.fakeuslugi.controller.dto.OrderDtoRequest;
import com.fakeuslugi.controller.dto.OrderDtoResponse;
import com.fakeuslugi.controller.dto.OrderListDto;
import com.fakeuslugi.controller.dto.ServiceDtoResponse;
import com.fakeuslugi.seasonservice.OrderService;
import com.fakeuslugi.seasonservice.dao.ProvidedService;
import com.fakeuslugi.security.dao.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/service")
@Slf4j
public class ServiceController extends AbstractController {

    @Autowired
    private OrderService orderService;

    // Post new order (order new season service)
    @PostMapping(value = "order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> order(@Valid @RequestBody OrderDtoRequest orderRequest, HttpServletRequest httpServletRequest) {
        Customer customer = findCustomer(httpServletRequest);
        ProvidedService order = orderService.createOrder(orderRequest, customer);
        return ResponseEntity.ok().body(String.format("{\"id\":%d}", order.getId()));
    }

    // Get list of all existing services
    @GetMapping(value = "list")
    public ResponseEntity<List<ServiceDtoResponse>> list() {
        return ResponseEntity.ok().body(orderService.getServiceList());
    }

    // Get list of all ordered season services by given user
    @GetMapping(value = "orders")
    public ResponseEntity<OrderListDto> orders(HttpServletRequest httpServletRequest) {
        Customer customer = findCustomer(httpServletRequest);
        return ResponseEntity.ok().body(orderService.getOrderList(customer));
    }
}
