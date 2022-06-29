package com.fakeuslugi.security;

import com.fakeuslugi.controller.dto.CustomerDto;
import com.fakeuslugi.security.dao.Customer;
import com.fakeuslugi.security.dao.CustomerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Slf4j
@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails createCustomer(String authorityName, CustomerDto customerRegRequest){


        // TODO make static factory
        Authority authority = new Authority(authorityName);
        if (authority == null) { // TODO take off
            log.error("No such authority name found: " + authorityName);
            throw new IllegalArgumentException("No such authority name found: " + authorityName);
        }
        String password = passwordEncoder.encode(customerRegRequest.getPassword());
        Customer customer = new Customer(authority, password, customerRegRequest.getName(), customerRegRequest.getSecondName(), customerRegRequest.getEmail(), ZonedDateTime.now());
        // TODO add thirdName
        customer.setAccountNonExpired(true);
        customer.setAccountNonLocked(true);
        customer.setCredentialsNonExpired(true);
        customer.setEnabled(true);
        UserDetails newCustomer = customerDao.createCustomer(customer);
        return newCustomer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerDao.findByEmail(username);
        if (customer == null) {
            log.warn("No such user reigstered: " + username);
            throw new UsernameNotFoundException("No such user registered!");
        }
        return customer;
    }

    public boolean isExistingUser(String email){
        return customerDao.findByEmail(email) != null;
    }
}
