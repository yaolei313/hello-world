package com.yao.app.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

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

        Room test3 = context.getBean("test3", Room.class);
        System.out.println(test3.getName());
        context.close();

        MyAlias ann = TempName.class.getAnnotation(MyAlias.class);
        System.out.println(ann.value());
        System.out.println(ann.name());

        MyAlias ann2 = AnnotationUtils.findAnnotation(TempName.class, MyAlias.class);
        System.out.println(ann2.value());
        System.out.println(ann2.name());
    }
}
