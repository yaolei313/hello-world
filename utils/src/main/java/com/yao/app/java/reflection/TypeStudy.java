package com.yao.app.java.reflection;

import com.fasterxml.jackson.core.type.TypeReference;
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

        System.out.println("------1-------");

        System.out.println(clazz.getEnclosingClass());
        System.out.println(clazz.getEnumConstants());
        System.out.println(clazz.getGenericInterfaces());
        System.out.println(clazz.getGenericSuperclass());
        System.out.println(clazz.getTypeName());

        System.out.println("------2-------");

        System.out.println(clazz.getName());
        System.out.println(clazz.getSimpleName());
        System.out.println(clazz.getCanonicalName());

        System.out.println("------3-------");

        TypeRef<ReflectionStudy> typeRef = new TypeRef<ReflectionStudy>() {
        };
        System.out.println(typeRef.getType());

    }

    private abstract static class TypeRef<T> implements Comparable<TypeRef<T>>{

        private Type type;

        public TypeRef() {
            // getGenericSuperclass  返回直接继承的父类（包含泛型参数）
            // getSuperclass   返回直接继承的父类（由于编译擦除，没有显示泛型参数）
            // 这个地方一般都是通过匿名，所以父类的话就是TypeRef<Xxxx>了
            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            this.type = MoreTypes.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public Type getType() {
            return type;
        }

        // 避免编译器提示泛型T never used
        @Override
        public int compareTo(TypeRef<T> o) { return 0; }
    }
}
