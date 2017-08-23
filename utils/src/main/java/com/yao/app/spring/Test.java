package com.yao.app.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yaolei02 on 2017/7/29.
 */
public class Test {
    public static void main(String[] args){
        ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("classpath*:spring/application.xml");
        TestBean testBean = context.getBean("dataSource", TestBean.class);
        System.out.println(testBean.getName1().getName());
        System.out.println(testBean.getName2().getName());
    }
}
