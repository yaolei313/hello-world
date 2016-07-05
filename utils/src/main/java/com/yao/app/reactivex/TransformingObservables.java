package com.yao.app.reactivex;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

public class TransformingObservables {

    public static void main(String[] args) {
        buffer();
    }
    
    public static void buffer(){
        Observable<Integer> observable = Observable.range(1, 50);
        
        Observable<List<Integer>> observable2 = observable.buffer(10);
        
        observable2.subscribe(new Action1<List<Integer>>(){

            @Override
            public void call(List<Integer> t) {
                System.out.println(t);
            }
        });
    }

}
