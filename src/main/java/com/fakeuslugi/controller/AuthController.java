package com.fakeuslugi.controller;

import com.fakeuslugi.controller.dto.AuthDtoRequest;
import com.fakeuslugi.controller.dto.CustomerDtoRequest;
import com.fakeuslugi.security.Authority;
import com.fakeuslugi.security.JwtTokenUtil;
import com.fakeuslugi.security.dao.Customer;
import com.fakeuslugi.security.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@Slf4j
public class AuthController extends AbstractController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final static JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@Valid @RequestBody CustomerDtoRequest customerRegRequest) {
        if (customerService.isExistingUser(customerRegRequest.getEmail())) { // TODO move to service
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with such email already exists");
        }
        customerService.createCustomer(Authority.USER, customerRegRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginUser(@Valid @RequestBody AuthDtoRequest authDtoRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authDtoRequest.getEmail(), authDtoRequest.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authToken);
            Customer customer = (Customer) authenticate.getPrincipal();
            String jwtToken = jwtTokenUtil.generateAccessToken(customer);
            log.debug("Authenticated user = " + customer.toString());
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtToken)
                    .build();
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
        }
    }

    @GetMapping("testuser")
    @ResponseBody
    public ResponseEntity<String> testUser(HttpServletRequest httpServletRequest) {
        Customer customer = (Customer) httpServletRequest.getAttribute("customer");
        if (customer != null) {
            log.debug("User ID authenticated: " + customer.getId());
            return ResponseEntity.ok("Authenticated user: " + customer.toString());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");
    }

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, List<String>>> handleValidationException(MethodArgumentNotValidException e) {
        HashMap<String, List<String>> errors = new HashMap<>();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            errors.computeIfAbsent("error", k -> new ArrayList<>()).add(error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }*/
}
