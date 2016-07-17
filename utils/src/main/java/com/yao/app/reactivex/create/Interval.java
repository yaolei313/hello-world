package com.yao.app.reactivex.create;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class Interval {
    public static void main(String[] args) {
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS).take(20);
        observable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long t) {
                System.out.println(System.currentTimeMillis() + ":" + t);
            }
        });
        
        Scanner input = new Scanner(System.in);
        input.next();

    }

}
