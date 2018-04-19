package com.yao.app.java.reflection;

import com.google.inject.internal.MoreTypes;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaolei02 on 2018/4/17.
 */
public class TypeStudy {

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        Class clazz = map.getClass();

        System.out.println(clazz.getAnnotatedInterfaces());
        System.out.println(clazz.getAnnotatedSuperclass());
        System.out.println(clazz.getComponentType());
        System.out.println(clazz.getDeclaredAnnotations());
        System.out.println(clazz.getDeclaringClass());

        System.out.println("-------------");

        System.out.println(clazz.getEnclosingClass());
        System.out.println(clazz.getEnumConstants());
        System.out.println(clazz.getGenericInterfaces());
        System.out.println(clazz.getGenericSuperclass());
        System.out.println(clazz.getTypeName());

        System.out.println("-------------");

        System.out.println(clazz.getName());
        System.out.println(clazz.getSimpleName());
        System.out.println(clazz.getCanonicalName());

        System.out.println("-------------");
    }

    private static class TypeRef<T>{

        private Type type;

        public TypeRef() {
            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            this.type = MoreTypes.canonicalize(parameterized.getActualTypeArguments()[0]);
        }
    }
}
