package com.fakeuslugi.controller;

import com.fakeuslugi.controller.model.AuthModel;
import com.fakeuslugi.controller.model.TestCont;
import com.fakeuslugi.controller.model.UserModel;
import com.fakeuslugi.security.Authority;
import com.fakeuslugi.security.JwtTokenUtil;
import com.fakeuslugi.security.User;
import com.fakeuslugi.security.UserService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping(value = "/auth")
@Slf4j
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    public AuthController() {
        this.jwtTokenUtil = new JwtTokenUtil();
    }

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody UserModel newUser) {
        // UserModel newUser = new UserModel("testUser", "passs", "f@mail.fff");
        if (userService.isExistingName(newUser.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Such username already exists");
        }
        userService.createUser(Authority.USER, newUser.getPassword(), newUser.getUsername(), newUser.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginUser(@RequestBody AuthModel authModel) {
        // AuthModel authModel = new AuthModel("testUser", "passs");
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authModel.getUsername(), authModel.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authToken);
            User user = (User) authenticate.getPrincipal();
            String jwtToken = jwtTokenUtil.generateAccessToken(user);
            log.debug("Authenticated user = " + user.toString());
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
        User user = (User) httpServletRequest.getAttribute("user");
        if (user != null){
            log.debug("User ID authenticated: " + user.getUserId());
            return ResponseEntity.ok("Authenticated user: " + user.toString());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");
    }
}
