package com.yao.app.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yaolei02 on 2017/7/29.
 */
public class SpringStudy {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("classpath*:spring/application.xml");
        /*TestBean testBean = context.getBean("testBean", TestBean.class);
        System.out.println(testBean.getName1().getName());
        System.out.println(testBean.getName2().getName());*/

        TempName test3 = context.getBean("test3", TempName.class);
        System.out.println(test3.getName());
        context.close();
    }
}
