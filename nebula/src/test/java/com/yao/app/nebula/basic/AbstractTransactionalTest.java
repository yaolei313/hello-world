package com.yao.app.nebula.basic;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * 需要用到事务管理（比如要在测试结果出来之后回滚测试内容）
 *
 * @author summer
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@ActiveProfiles("dev")
public abstract class AbstractTransactionalTest extends AbstractTransactionalJUnit4SpringContextTests {

}
