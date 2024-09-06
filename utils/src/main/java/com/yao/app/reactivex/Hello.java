package com.yao.app.reactivex;


import io.reactivex.rxjava3.core.Observable;

/**
 * Created by yaolei02 on 2018/12/4.
 */
public class Hello {

    public static void main(String[] args) {
        hello("li bai", "du fu");
    }

    public static void hello(String... names) {
        Observable.fromArray(names).subscribe(o -> System.out.println("hello " + o));
    }

    public static void test2() {
        Observable.just("hello world").subscribe(str -> System.out.println(str));
    }
}
