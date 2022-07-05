package com.fakeuslugi;


// @TestConstructor(autowireMode=TestConstructor.AutowireMode.ANNOTATED)
// @WebAppConfiguration("file:src/main/webapp/WEB-INF/web.xml")


// @ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config.xml"})

// @ContextConfiguration(locations = {"/spring-config.xml"})

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/*@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:/spring-config.xml"})*/

// @ContextConfiguration(locations = {"classpath:/spring-config.xml"})
@Slf4j
class FakeuslugiApplicationTests {
/*
    @Autowired
    private TestDao testDao;*/

    @Test
    void contextLoads() {
        // testDao.findById(3L);

        // TestEntity te = new TestEntity();
        log.info("INSIDE TEST");
        // Assertions.assertNotNull(te);
    }

}
