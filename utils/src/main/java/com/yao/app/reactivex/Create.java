package com.yao.app.reactivex;

import rx.Observable;
import rx.Subscriber;

public class Create {

    public static void main(String[] args) {
        // 创建观察者,检查isUnsubscribed状态
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        observer.onNext("Hello");
                        observer.onNext(" World!");
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
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
