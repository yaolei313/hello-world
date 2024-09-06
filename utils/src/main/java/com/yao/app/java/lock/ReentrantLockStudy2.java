package com.yao.app.java.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockStudy2 {

    ReentrantLock lock;

    Condition cond1;

    Condition cond2;

    int n = 10;

    public ReentrantLockStudy2(ReentrantLock lock, Condition cond1, Condition cond2) {
        this.lock = lock;
        this.cond1 = cond1;
        this.cond2 = cond2;
    }

    public void run1() {
        lock.lock();
        try {
            for(int i=0;i<n;i++) {
                if (i!=0) {
                    cond1.await();
                }
                System.out.print("li");
                cond2.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void run2() {
        lock.lock();
        try {
            for(int i=0;i<n;i++) {
                cond2.await();
                System.out.println("xiang");
                cond1.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) throws Exception {
        ReentrantLock lock = new ReentrantLock();
        Condition cond1 = lock.newCondition();
        Condition cond2 = lock.newCondition();

        ReentrantLockStudy2 r = new ReentrantLockStudy2(lock, cond1, cond2);

        Thread t1 = new Thread() {
            public void run() {
                r.run1();
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                r.run2();
            }
        };

        t2.start();
        t1.start();
        System.out.println("a");
        // Thread.sleep(1000);
        /*lock.lock();
        try {
            cond1.signal();
        } finally {
            lock.unlock();
        }
        System.out.println("wait");*/
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }
}
