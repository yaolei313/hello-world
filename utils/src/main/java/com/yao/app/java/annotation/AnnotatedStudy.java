package com.yao.app.java.annotation;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * Created by yaolei02 on 2018/4/19.
 */
public class AnnotatedStudy {


    public static void main(String[] args) {
        try {
            Class<Foo> clz = Foo.class;
            System.out.println("clz.getAnnotatedInterfaces()");
            printAnnotatedType(clz.getAnnotatedInterfaces());
            System.out.println("clz.getAnnotatedSuperclass()");
            printAnnotatedType(clz.getAnnotatedSuperclass());

            System.out.println("-----------------");

            Field field = clz.getField("desc");
            System.out.println("field.getAnnotatedType()");
            printAnnotatedType(field.getAnnotatedType());

            System.out.println("-----------------");

            Constructor<Foo> constructor = clz.getConstructor(String.class);
            System.out.println("constructor.getAnnotatedReturnType()");
            printAnnotatedType(constructor.getAnnotatedReturnType());
            System.out.println("constructor.getAnnotatedReceiverType()");
            printAnnotatedType(constructor.getAnnotatedReceiverType());
            System.out.println("constructor.getAnnotatedParameterTypes()");
            printAnnotatedType(constructor.getAnnotatedParameterTypes());
            System.out.println("constructor.getAnnotatedExceptionTypes()");
            printAnnotatedType(constructor.getAnnotatedExceptionTypes());


            System.out.println("-----------------");

            Method method = clz.getMethod("getDesc", String.class);
            System.out.println("method.getAnnotatedReturnType()");
            printAnnotatedType(method.getAnnotatedReturnType());
            System.out.println("method.getAnnotatedReceiverType()");
            printAnnotatedType(method.getAnnotatedReceiverType());
            System.out.println("method.getAnnotatedParameterTypes()");
            printAnnotatedType(method.getAnnotatedParameterTypes());
            System.out.println("method.getAnnotatedExceptionTypes()");
            printAnnotatedType(method.getAnnotatedExceptionTypes());

            System.out.println("-----");
            Parameter[] parameter = method.getParameters();
            System.out.println("parameter[0].getAnnotatedType()");
            printAnnotatedType(parameter[0].getAnnotatedType());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printAnnotatedType(AnnotatedType[] annotatedTypes) {
        for (AnnotatedType annotatedType : annotatedTypes) {
            System.out.println("\t" + Arrays.toString(annotatedType.getAnnotations()));
            System.out.println("\t" + annotatedType.getType());
        }

    }

    private static void printAnnotatedType(AnnotatedType annotatedType) {
        System.out.println("\t" + Arrays.toString(annotatedType.getAnnotations()));
        System.out.println("\t" + annotatedType.getType());
    }
}
