package com.yao.app.reactivex.create;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

public class Defer {

    public static void main(String[] args) {
        Observable<String> observable = Observable.defer(new Func0<Observable<String>>(){

            @Override
            public Observable<String> call() {
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> t) {
                        t.onNext("hello");
                        t.onNext(" world");
                        t.onCompleted();
                    }
                });
            }
            
        });
        observable.subscribe(new Subscriber<String>() {

            @Override
            public void onCompleted() {
                System.out.println("观察到数据Completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("观察到数据Error" + e.getMessage());

            }

            @Override
            public void onNext(String t) {
                System.out.println("观察到数据:" + t);

            }
        });

    }

}
