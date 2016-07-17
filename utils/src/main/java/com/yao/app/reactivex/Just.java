package com.yao.app.reactivex;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

import com.google.common.collect.Lists;

public class Just {

    public static void main(String[] args) {
        Observable<String> observable = Observable.just("hello","li bai");
        observable.subscribe(new Action1<String>(){

            @Override
            public void call(String t) {
                System.out.println(t);
                
            }
            
        });
        
        List<String> list = Lists.newArrayList("hello","li bai");
        Observable<List<String>> observable2 = Observable.just(list);
        observable2.subscribe(new Action1<List<String>>(){

            @Override
            public void call(List<String> t) {
                System.out.println(t);
            }
        });
    }

}
