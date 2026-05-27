package com.yao.app.nebula.basic;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 普通测试基类
 *
 * @author summer
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@ActiveProfiles("dev")
public abstract class AbstractTest extends AbstractJUnit4SpringContextTests {

}
