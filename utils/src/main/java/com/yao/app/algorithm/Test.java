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

    // 参数都是引用reference对象，存储了对象的地址
    // 传值复制会复制的是引用对象，故交换这2个复制后的引用对象的地址没用处
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
