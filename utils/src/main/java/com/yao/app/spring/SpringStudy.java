package com.yao.app.spring;

import java.util.Arrays;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
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

        MyAlias ann = Temp1.class.getAnnotation(MyAlias.class);
        System.out.println(ann.value());
        System.out.println(ann.name());

        MyAlias ann2 = AnnotationUtils.findAnnotation(Temp1.class, MyAlias.class);
        System.out.println(ann2.value());
        System.out.println(ann2.name());

        System.out.println("-------------");

        MySubConfig ann5 = AnnotationUtils.findAnnotation(Temp2.class, MySubConfig.class);
        System.out.println(Arrays.toString(ann5.scanBasePackages()));

        ComponentScan ann6 = AnnotationUtils.findAnnotation(Temp2.class, ComponentScan.class);
        System.out.println(Arrays.toString(ann6.basePackages()));

        System.out.println("-------------");

        MySubConfig ann7 = AnnotatedElementUtils.findMergedAnnotation(Temp2.class, MySubConfig.class);
        System.out.println(Arrays.toString(ann7.scanBasePackages()));

        ComponentScan ann8 = AnnotatedElementUtils.findMergedAnnotation(Temp2.class, ComponentScan.class);
        System.out.println(Arrays.toString(ann8.basePackages()));
    }

    @MyAlias(value = "aa")
    public static class Temp1 {

        public String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @MySubConfig(scanBasePackages = "abc2")
    public static class Temp2 {

        public String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
