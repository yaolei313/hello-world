package com.yao.app.reactivex.create;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Create {

    public static void main(String[] args) {
        // 创建观察者,检查isUnsubscribed状态
        Observable<String> observable = Observable.create(emitter -> {
            try {
                System.out.println("emit hello");
                emitter.onNext("hello");
                System.out.println("emit world");
                emitter.onNext("world");
                System.out.println("emit 1");
                emitter.onNext("1");
                System.out.println("emit complete");
                emitter.onComplete();
                System.out.println("emit li bai");
                emitter.onNext("li bai");
            } catch (Exception e) {
                System.out.println("emit error");
                emitter.onError(e);
            }
        });

        observable.subscribe(new Observer<String>() {

            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe: " + d.isDisposed());
                this.disposable = d;
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s + "/" + disposable.isDisposed());
                if(s != null && s.equals("1")){
                    // 主动解除订阅
                    disposable.dispose();
                    // 查询是否解除订阅,true代表已经解除订阅
                    System.out.println("disposable:" + disposable.isDisposed());
                }

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e + "/" + disposable.isDisposed());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete." + disposable.isDisposed());
            }
        });

    }

}
