package com.fakeuslugi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DatabaseCheck {

    @Value("${test.key}")
    private String[] testval;

    @EventListener(ContextRefreshedEvent.class)
    public void checkDb(ContextRefreshedEvent e) {
      log.error("!!!!!!!!!!!!!!!!!!!!!!!!!LISTENER WORKED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
