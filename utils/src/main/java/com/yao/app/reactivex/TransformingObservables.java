package com.yao.app.reactivex;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

public class TransformingObservables {

    public static void main(String[] args) {
        // buffer();
        // bufferSkip();
        // bufferByTime();
        // flatMap();
        flatMap2();
    }

    public static void buffer() {
        Observable<Integer> observable = Observable.range(1, 50);

        Observable<List<Integer>> observable2 = observable.buffer(10);

        observable2.subscribe(new Action1<List<Integer>>() {

            @Override
            public void call(List<Integer> t) {
                System.out.println(t);
            }
        });
    }

    public static void bufferSkip() {
        Observable<Integer> observable = Observable.range(1, 50);

        // creates a new buffer starting with the first emitted item from the
        // source Observable, and every skip items thereafter;
        // fills each buffer with count items
        Observable<List<Integer>> observable2 = observable.buffer(10, 5);

        observable2.subscribe(new Action1<List<Integer>>() {

            @Override
            public void call(List<Integer> t) {
                System.out.println(t);
            }
        });
    }

    public static void bufferByTime() {
        Observable<Long> observable = Observable.interval(3, TimeUnit.MILLISECONDS).take(100);

        // creates a new buffer starting with the first emitted item from the
        // source Observable, and every skip items thereafter;
        // fills each buffer with count items
        Observable<List<Long>> observable2 = observable.buffer(5, TimeUnit.MILLISECONDS);

        observable2.subscribe(new Action1<List<Long>>() {

            @Override
            public void call(List<Long> t) {
                System.out.println(t);
            }
        });

        Scanner input = new Scanner(System.in);
        input.next();
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
        });
    }
}
