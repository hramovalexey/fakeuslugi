package com.fakeuslugi.controller;

import com.fakeuslugi.TestEntity;
import com.fakeuslugi.dao.TestDao;
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
    private TestDao testDao;

    @GetMapping
    public String mainPage() {
        TestEntity newEntity = new TestEntity();
        // newEntity.setTestField(4L);
        newEntity.setContent("CONTENT2");
        // testDao.saveTest(newEntity);
        testDao.findById(3L);
        log.error("INSIDE CONTROLLER");
        return "mainpage";
    }
}
