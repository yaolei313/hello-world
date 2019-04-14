package com.yao.app.algorithm;

public class Test {

    public static void main(String[] args) {
        Person c = new Person("c");
        Person d = new Person("d");
        swap(c, d);
        System.out.println("" + c + "," + d);

        // 和python不太一样，java中是3，-3；python中是3，7
        System.out.println(123%10);
        System.out.println(-123%10);
    }

    // 左侧都是引用reference，存储了对象的地址
    // 可以认为reference是个数据结构，传值复制会复制该引用。
    public static void swap(Person a, Person b) {
        Person tmp = a;
        a = b;
        b = tmp;
    }

    public static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                "name='" + name + '\'' +
                '}';
        }
    }
}
