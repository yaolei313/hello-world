package com.yao.app.reactivex.transform;


public class Map {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //map();
        //cast();
    }

    /*public static void map() {
        Observable<Integer> observable = Observable.range(1, 20);

        Observable<String> observable2 = observable.map(new Func1<Integer, String>() {

            @Override
            public String call(Integer t) {
                return "num:" + t;
            }
        });

        observable2.subscribe(new Action1<String>() {

            @Override
            public void call(String t) {
                System.out.println(t);
            }
        });

    }
    
    public static void cast() {
        Observable<Integer> observable = Observable.range(1, 20);

        Observable<Number> observable2 = observable.cast(Number.class);

        observable2.subscribe(new Action1<Number>() {

            @Override
            public void call(Number t) {
                System.out.println(t);
            }
        });

    }*/

}
