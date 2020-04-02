package com.yao.app.reactivex.create;

public class Start {

    public static void main(String[] args) {
        // asyncAction();
        // toAsync();
        // fromRunnable();
        // fromCallable();
        //fromAction();
        // start();
        // Scanner input = new Scanner(System.in);
        // input.next();
    }

    /*public static void asyncAction() {
        FuncN<Observable<Void>> fun = Async.asyncAction(new ActionN() {

            @Override
            public void call(Object... args) {
                System.out.println("delay");
                for (Object arg : args) {
                    System.out.println(arg);
                }

            }

        });
        Observable<Void> observable = fun.call("hello", "world");
        // observable.subscribe();
        System.out.println("---------");
        observable.subscribe(new Action1<Void>() {

            @Override
            public void call(Void t) {
                System.out.println("test");

            }
        });

    }

    public static void toAsync() {
        FuncN<Observable<Void>> fun = Async.toAsync(new ActionN() {

            @Override
            public void call(Object... args) {
                System.out.println("delay");
                for (Object arg : args) {
                    System.out.println(arg);
                }

            }

        });
        Observable<Void> observable = fun.call("hello", "world");
        System.out.println("---------");
        observable.subscribe(new Action1<Void>() {

            @Override
            public void call(Void t) {
                System.out.println("test");

            }
        });
    }

    public static void start() {
        Observable<String> observable = Async.start(new Func0<String>() {

            @Override
            public String call() {
                // TODO Auto-generated method stub
                return "hello";
            }

        });
        observable.subscribe(new Subscriber<String>() {

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");

            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());

            }

            @Override
            public void onNext(String t) {
                System.out.println(t);

            }

        });
    }

    public static void fromRunnable() {
        String result = "123";
        Observable<String> observable = Async.fromRunnable(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " run");

            }

        }, result);
        observable.subscribe(new Action1<String>() {

            @Override
            public void call(String t) {
                System.out.println(Thread.currentThread().getName() + "结果" + t);

            }

        });
    }

    public static void fromCallable() {
        Observable<String> observable = Async.fromCallable(new Callable<String>() {

            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                return "hello world";
            }

        });
        observable.subscribe(new Action1<String>() {

            @Override
            public void call(String t) {
                System.out.println(Thread.currentThread().getName() + "结果" + t);

            }

        });
    }
    
    public static void fromAction() {
        String result = "query action finished";
        Observable<String> observable = Async.fromAction(new Action0(){

            @Override
            public void call() {
                System.out.println(Thread.currentThread().getName() + " call");
                
            }}, result);
        observable.subscribe(new Action1<String>() {

            @Override
            public void call(String t) {
                System.out.println(Thread.currentThread().getName() + "结果" + t);

            }

        });
    }*/

}
