package com.yao.app.reactivex;

import rx.Observable;
import rx.functions.Action1;

public class Range {

    public static void main(String[] args) {
        Observable<Integer> observable = Observable.range(10, 5);
        observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer t) {
                System.out.println(t);
            }
        });
    }

}
