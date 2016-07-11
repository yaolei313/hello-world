package com.yao.app.reactivex;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;

public class TransformingObservables {

    public static void main(String[] args) {
        //buffer();
        //bufferSkip();
        bufferByTime();
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
    
    public static void bufferSkip(){
        Observable<Integer> observable = Observable.range(1, 50);
        
        // creates a new buffer starting with the first emitted item from the source Observable, and every skip items thereafter;
        // fills each buffer with count items
        Observable<List<Integer>> observable2 = observable.buffer(10,5);
        
        observable2.subscribe(new Action1<List<Integer>>(){

            @Override
            public void call(List<Integer> t) {
                System.out.println(t);
            }
        });
    }

    public static void bufferByTime(){
        Observable<Long> observable = Observable.interval(3, TimeUnit.MILLISECONDS).take(100);
        
        // creates a new buffer starting with the first emitted item from the source Observable, and every skip items thereafter;
        // fills each buffer with count items
        Observable<List<Long>> observable2 = observable.buffer(5, TimeUnit.MILLISECONDS);
        
        observable2.subscribe(new Action1<List<Long>>(){

            @Override
            public void call(List<Long> t) {
                System.out.println(t);
            }
        });
        
        Scanner input = new Scanner(System.in);
        input.next();
    }
}
