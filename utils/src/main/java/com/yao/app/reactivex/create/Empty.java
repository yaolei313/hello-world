package com.yao.app.reactivex.create;

import rx.Observable;
import rx.Subscriber;

public class Empty {

    public static void main(String[] args) {
        throwTest();
    }
    
    public static void emptyTest(){
        // 直接调用观察者的onCompleted
        Observable<String> observable = Observable.empty();

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
    
    public static void neverTest(){
        Observable<String> observable = Observable.never();

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
    
    public static void throwTest(){
        Observable<String> observable = Observable.error(new IllegalArgumentException("hahah"));

        observable.subscribe(new Subscriber<String>() {

            @Override
            public void onCompleted() {
                System.out.println("观察到数据Completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("观察到数据Error " + e.getMessage());

            }

            @Override
            public void onNext(String t) {
                System.out.println("观察到数据:" + t);

            }
        });
    }

}
