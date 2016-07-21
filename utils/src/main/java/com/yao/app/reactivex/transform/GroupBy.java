package com.yao.app.reactivex.transform;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

public class GroupBy {

    public static void main(String[] args) {
        Observable<Integer> ovservable = Observable.range(1, 20);
        Observable<GroupedObservable<String, Integer>> grouped = ovservable.groupBy(new Func1<Integer, String>() {

            @Override
            public String call(Integer t) {
                return String.valueOf(t % 3);
            }
        });

        grouped.subscribe(new Action1<GroupedObservable<String,Integer>>(){
            @Override
            public void call(GroupedObservable<String, Integer> t) {
                // TODO Auto-generated method stub
                
            }
            
        });
    }

}
