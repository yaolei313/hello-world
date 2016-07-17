package com.yao.app.nebula.basic;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(value = { @ContextConfiguration(locations = "classpath:spring/applicationContext.xml"),
		@ContextConfiguration(locations = "WEB-INF/spring-mvc.xml") })
@ActiveProfiles("dev")
public abstract class AbstractMvcTest extends AbstractJUnit4SpringContextTests {

}
