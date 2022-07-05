package com.fakeuslugi.controller;

import com.fakeuslugi.seasonservice.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
public class MainController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String mainPage() {
        log.debug("Main page request");
        return "mainpage";
    }

    @GetMapping("testmail")
    public String mail(){
        emailService.sendSimpleMessage("hramovalexey@yandex.ru", "testmess", "Hello test message");
        return "";
    }

    @GetMapping("/ajax/orderlist")
    public String orderlist() {
        return "orderlist";
    }

    @GetMapping("/ajax/order")
    public String order() {
        return "order";
    }

    @GetMapping("/ajax/history")
    public String history() {
        return "history";
    }

    @GetMapping("/ajax/auth")
    public String auth() {
        return "auth";
    }
}
