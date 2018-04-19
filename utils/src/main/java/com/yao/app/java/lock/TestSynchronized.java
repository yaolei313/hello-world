package com.yao.app.java.lock;

public class TestSynchronized {

    public synchronized void method1() throws InterruptedException {
        System.out.println("method1 begin at:" + System.currentTimeMillis());
        Thread.sleep(10000);
        System.out.println("method1 end at:" + System.currentTimeMillis());
    }

    public synchronized void method2() throws InterruptedException {
        System.out.println("method2 start");
        Thread.sleep(2000);
        System.out.println("method2 end");
    }
    
    public synchronized static void method3() throws InterruptedException {
        System.out.println("static method3 start");
        Thread.sleep(2000);
        System.out.println("static method3 end");
    }

    public static void main(String[] args) {
        TestSynchronized instance = new TestSynchronized();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    instance.method1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    instance.method2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TestSynchronized.method3();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        //thread2.start();
        thread3.start();

    }

}
