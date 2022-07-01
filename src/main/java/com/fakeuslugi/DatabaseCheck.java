package com.fakeuslugi;

import com.fakeuslugi.seasonservice.dao.SeasonService;
import com.fakeuslugi.seasonservice.dao.SeasonServiceDao;
import com.fakeuslugi.seasonservice.dao.StatusDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class DatabaseCheck {
    @Autowired
    private StatusDao statusDao;

    @Autowired
    private SeasonServiceDao seasonServiceDao;

    @Autowired
    ConfigurableApplicationContext configurableApplicationContext;

    @Value("${status_list}")
    private String[] statusList;

    @Value("#{${season_service_map}}")
    private Map<String, Long> seasonServiceMap;

    @EventListener(ContextRefreshedEvent.class)
    public void checkDb(ContextRefreshedEvent event) {
        for (String statusName : statusList) {
            statusDao.tryCreateStatus(statusName);
        }
        if (!isInitialStatusPresent()) {
            log.error("Property status_list is inconsistent with property default_initial_status. Check them at application.properties file. Application stopped");
            configurableApplicationContext.stop(); // TODO CHECK
            System.exit(1);
        }
        log.info("table Status checked");

        for (String name : seasonServiceMap.keySet()) {
            try {
            seasonServiceDao.tryCreateSeasonService(name, seasonServiceMap.get(name));
            } catch (ConstraintViolationException e) {
                log.error(getConstraintExceptionMessage(e) + " Nothing changed");
            }
        }
        log.info("table SeasonService checked");
    }

    private String getConstraintExceptionMessage(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder sb = new StringBuilder();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        sb.append(iterator.next().getMessage());
        iterator.forEachRemaining(v -> sb.append(", ").append(v));
        return sb.toString();
    }

    private boolean isInitialStatusPresent() {
        return statusDao.getInitialStatus() != null;
    }

}
