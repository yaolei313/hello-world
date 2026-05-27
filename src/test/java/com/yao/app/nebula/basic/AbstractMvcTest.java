package com.yao.app.nebula.basic;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy(value = {@ContextConfiguration(locations = "classpath:spring/applicationContext.xml"),
    @ContextConfiguration(locations = "WEB-INF/spring-mvc.xml")})
@ActiveProfiles("dev")
public abstract class AbstractMvcTest extends AbstractJUnit4SpringContextTests {

}
