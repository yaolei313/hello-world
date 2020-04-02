package com.yao.app.reactivex.transform;

public class FlatMap {

    public static void main(String[] args) {
        // flatMap();
        // flatMap2();
        // concatMap();
    }

    /*public static void flatMap() {
        Observable<Integer> observable = getDefaultIntegerObservable2();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        // 顺序可能发生变化，若对顺序有严格要求，则使用concatMap
        Observable<Integer> observable2 = observable.flatMap(new Func1<Integer, Observable<Integer>>() {

            @Override
            public Observable<Integer> call(Integer t) {
                if (t == 19) {
                    throw new RuntimeException("is 19");
                }
                return Observable.just(t * 2).subscribeOn(Schedulers.from(executor));
            }

        });


        observable2.subscribe(new Action1<Integer>() {

            @Override
            public void call(Integer t) {
                System.out.println(t);
            }
        }, new Action1<Throwable>() {

            @Override
            public void call(Throwable t) {
                System.out.println("出错了" + t.getMessage());
            }

        }, new Action0() {
            @Override
            public void call() {
                System.out.println("执行结束");

            }

        });

    }

    public static void flatMap2() {
        Observable<Integer> observable = getDefaultIntegerObservable();

        // 顺序可能发生变化，若对顺序有严格要求，则使用concatMap
        Observable<Integer> observable2 = observable.flatMap(new Func1<Integer, Observable<Integer>>() {

            @Override
            public Observable<Integer> call(Integer t) {
                return Observable.just(t * 2 + 1);
            }

        }, new Func1<Throwable, Observable<Integer>>() {

            @Override
            public Observable<Integer> call(Throwable t) {
                return Observable.error(t);
            }

        }, new Func0<Observable<Integer>>() {

            @Override
            public Observable<Integer> call() {
                return Observable.empty();
            }

        });

        observable2.subscribe(new DefaultSubscriber<Integer>());
    }

    public static void concatMap() {
        Observable<Integer> observable = getDefaultIntegerObservable();

        Observable<String> observable2 = observable.concatMap(new Func1<Integer, Observable<String>>() {

            @Override
            public Observable<String> call(Integer t) {
                if (t == null) {
                    return Observable.just("empty");
                } else if (t % 2 == 0) {
                    return Observable.just("oushu");
                } else {
                    return Observable.just("jishu");
                }
            }

        });

        observable2.subscribe(new DefaultSubscriber<String>());

    }

    //
    private static Observable<Integer> getDefaultIntegerObservable() {
        Observable<Integer> observable = Observable.range(1, 20);

        return observable;
    }

    private static Observable<Integer> getDefaultIntegerObservable2() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        for (int i = 0; i < 100; i++) {
                            if (i == 77) {
                                throw new RuntimeException("is 77");
                            }
                            observer.onNext(i);
                        }
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }

        });
    }*/

}
