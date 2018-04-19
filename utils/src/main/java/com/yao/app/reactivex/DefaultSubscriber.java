package com.yao.app.reactivex;

import rx.Subscriber;

public class DefaultSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {
        System.out.println("onCompleted");

    }

    @Override
    public void onError(Throwable e) {
        System.out.println("onError:" + e.getMessage());

    }

    @Override
    public void onNext(T t) {
        System.out.println("onNext:" + t);

    }

}
