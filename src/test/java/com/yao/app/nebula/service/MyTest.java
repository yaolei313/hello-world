package com.yao.app.nebula.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class MyTest extends AbstractJUnit4SpringContextTests {

    @Test
    public void test() {
        System.out.println("test");
    }
}
