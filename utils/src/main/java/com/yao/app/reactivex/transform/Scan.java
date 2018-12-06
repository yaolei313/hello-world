package com.yao.app.reactivex.transform;


public class Scan {
    public static void main(String[] args) {
        //scan();
        //scan2();
    }

    /*public static void scan() {
        Observable<Integer> observable = Observable.range(1, 5);

        Observable<Integer> observable2 = observable.scan(new Func2<Integer, Integer, Integer>() {

            @Override
            public Integer call(Integer t1, Integer t2) {
                System.out.println(t1 + ":" + t2);
                return t1 + t2;
            }

        });
        
        observable2.subscribe(new Action1<Integer>() {

            @Override
            public void call(Integer t) {
                System.out.println(t);
            }
        });
    }
    
    public static void scan2() {
        Observable<Integer> observable = Observable.range(1, 5);

        Observable<Integer> observable2 = observable.scan(1, new Func2<Integer, Integer, Integer>() {

            @Override
            public Integer call(Integer t1, Integer t2) {
                System.out.println(t1 + ":" + t2);
                return t1 + t2;
            }

        });
        
        observable2.subscribe(new Action1<Integer>() {

            @Override
            public void call(Integer t) {
                System.out.println(t);
            }
        });
    }*/
}
