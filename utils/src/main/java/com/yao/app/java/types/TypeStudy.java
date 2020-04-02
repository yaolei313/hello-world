package com.yao.app.java.types;

import com.google.inject.internal.MoreTypes;
import com.yao.app.java.reflection.ReflectionStudy;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yaolei02 on 2018/4/17.
 */
public class TypeStudy {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
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

        System.out.println("------4-------");
        TypeTest.testType();

    }

    private abstract static class TypeRef<T> implements Comparable<TypeRef<T>> {

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
        public int compareTo(TypeRef<T> o) {
            return 0;
        }
    }


    public static class TypeTest<T> {
        private List<String> t0;

        private T t1;

        //private List<String>[] t2;
        private List<String>[][] t2;

        private List<? extends String> t3;

        public static void testType() {
            try {
                Field field0 = TypeTest.class.getDeclaredField("t0");
                Field field1 = TypeTest.class.getDeclaredField("t1");
                Field field2 = TypeTest.class.getDeclaredField("t2");
                Field field3 = TypeTest.class.getDeclaredField("t3");

                System.out.println("List<String> t0");
                ParameterizedType type0 = (ParameterizedType) field0.getGenericType();
                print(type0.getActualTypeArguments());
                System.out.println(Arrays.toString(type0.getActualTypeArguments()) + type0.getRawType() + "," + type0.getOwnerType() + "\n");

                System.out.println("T t1");
                TypeVariable type1 = (TypeVariable) field1.getGenericType();
                System.out.println(Arrays.toString(type1.getBounds()) + "," + type1.getGenericDeclaration() + "," + type1.getName() + "\n");

                System.out.println("List<String>[][] t2");
                GenericArrayType type2 = (GenericArrayType) field2.getGenericType();
                System.out.println(type2.getGenericComponentType() + "\n");

                System.out.println("List<? extends String> t3");
                ParameterizedType type3 = (ParameterizedType) field3.getGenericType();
                System.out.println(Arrays.toString(type3.getActualTypeArguments()) + "," + type3.getRawType() + "," + type3.getOwnerType());
                WildcardType type31 = (WildcardType) type3.getActualTypeArguments()[0];
                System.out.println(Arrays.toString(type31.getLowerBounds()) + ", " + Arrays.toString(type31.getUpperBounds()));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        }

        public static void print(Type... types) {
            System.out.print("types:[ ");
            for (Type type : types) {
                System.out.print("class:" + type.getClass() + "\t");
            }
            System.out.println(" ]");
        }
    }
}
