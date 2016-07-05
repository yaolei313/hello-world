package com.yao.app.reactivex;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class Timer {

    public static void main(String[] args) {
        Observable<Long> observable = Observable.timer(2, TimeUnit.SECONDS);
        observable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long t) {
                System.out.println(t);
            }
        });
        
        // deprecated so use interval
        Observable<Long> observable2 = Observable.timer(2, 1, TimeUnit.SECONDS);
        observable2.subscribe(new Action1<Long>() {
            @Override
            public void call(Long t) {
                System.out.println(t);
            }
        });
        
        Scanner input = new Scanner(System.in);
        input.next();
    }

}
