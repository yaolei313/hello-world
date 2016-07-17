package com.yao.app.reactivex;

import rx.Observable;
import rx.Subscriber;

public class From {

    public static void main(String[] args) {
        Integer[] array = new Integer[]{1,2,3,4,5};
        Observable<Integer> observable = Observable.from(array);

        observable.subscribe(new Subscriber<Integer>() {

            @Override
            public void onCompleted() {
                System.out.println("观察到数据Completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("观察到数据Error " + e.getMessage());

            }

            @Override
            public void onNext(Integer t) {
                System.out.println("观察到数据:" + t);

            }
        });

    }

}
