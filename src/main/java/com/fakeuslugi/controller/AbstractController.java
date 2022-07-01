package com.fakeuslugi.controller;

import com.fakeuslugi.seasonservice.exception.SeasonServiceException;
import com.fakeuslugi.security.dao.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
public abstract class AbstractController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<HashMap<String, List<String>>> handleValidationException(MethodArgumentNotValidException e) {
        HashMap<String, List<String>> errors = new HashMap<>();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            errors.computeIfAbsent("error", k -> new ArrayList<>()).add(error.getDefaultMessage());
        }
        log.debug(errors.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<HashMap<String, List<String>>> handleUserException(UsernameNotFoundException e) {
        HashMap<String, List<String>> errors = new HashMap<>();
        errors.compute("error", (k, v) -> Arrays.asList(e.getMessage()));
        log.debug(errors.toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(SeasonServiceException.class)
    protected ResponseEntity<HashMap<String, List<String>>> handleSeasonServiceException(SeasonServiceException e) {
        HashMap<String, List<String>> errors = new HashMap<>();
        errors.compute("error", (k, v) -> Arrays.asList(e.getMessage()));
        e.printStackTrace();
        return ResponseEntity.status(e.getHttpStatus()).body(errors);
    }

    protected Customer findCustomer(HttpServletRequest httpServletRequest){
        Customer customer = (Customer) httpServletRequest.getAttribute("customer");
        if (customer == null){
            throw new UsernameNotFoundException("No such user registered!");
        }
        return customer;
    }
}
