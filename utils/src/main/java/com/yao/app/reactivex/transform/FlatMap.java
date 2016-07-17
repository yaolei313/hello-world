package com.yao.app.reactivex.transform;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

public class FlatMap {

    public static void main(String[] args) {
        // flatMap();
        flatMap2();
    }

    

    public static void flatMap() {
        Observable<Long> observable = Observable.interval(3, TimeUnit.MILLISECONDS).take(100);

        Observable<Long> observable2 = observable.flatMap(new Func1<Long, Observable<Long>>() {

            @Override
            public Observable<Long> call(Long t) {
                return Observable.just(t * 2);
            }

        });

        observable2.subscribe(new Action1<Long>() {

            @Override
            public void call(Long t) {
                System.out.println(t);
            }
        });

        Scanner input = new Scanner(System.in);
        input.next();
    }

    public static void flatMap2() {
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        for (int i = 0; i < 100; i++) {
                            if (i == 77) {
                                throw new RuntimeException("is 77");
                            }
                            observer.onNext(i);
                        }
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        });

        Observable<Integer> observable2 = observable.flatMap(new Func1<Integer, Observable<Integer>>() {

            @Override
            public Observable<Integer> call(Integer t) {
                return Observable.just(t * 2 + 1);
            }

        }, new Func1<Throwable, Observable<Integer>>() {

            @Override
            public Observable<Integer> call(Throwable t) {
                return Observable.error(t);
            }

        }, new Func0<Observable<Integer>>() {

            @Override
            public Observable<Integer> call() {
                return Observable.empty();
            }

        });

        observable2.subscribe(new Action1<Integer>() {

            @Override
            public void call(Integer t) {
                System.out.println(t);
            }
        }, new Action1<Throwable>() {

            @Override
            public void call(Throwable t) {
                System.out.println("出错了" + t.getMessage());
            }

        }, new Action0() {
            @Override
            public void call() {
                System.out.println("执行结束");

            }

        });
    }

}
