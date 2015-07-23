package com.yao.app.nebula.basic;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 需要用到事务管理（比如要在测试结果出来之后回滚测试内容）
 * 
 * @author summer
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@ActiveProfiles("dev")
public class AbstractTransactionalTest extends AbstractTransactionalJUnit4SpringContextTests {

}
