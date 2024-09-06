package com.yao.app;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {

    // 2个线程交替打印1-100
    public static void main(String[] args) {
        Test1 instance = new Test1();
        try {
            instance.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ReentrantLock lock  = new ReentrantLock();

    private Condition c1 = lock.newCondition();

    private Condition c2 = lock.newCondition();

    private int count = 0;

    public void print() throws Exception{
        Thread t1 = new Thread(()-> {
            while (true) {
                lock.lock();
                try {
                    if (count>100) {
                        c2.signalAll();
                        System.out.println("end t1");
                        return;
                    }

                    if (count % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() +":"+ count);
                        count++;
                        c2.signalAll();
                    }
                    System.out.println("await t1");
                    c1.await();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread t2 = new Thread(()-> {
            while (true) {
                lock.lock();
                try {
                    if (count>100) {
                        c1.signalAll();
                        System.out.println("end t2");
                        break;
                    }

                    if (count % 2 == 1) {
                        System.out.println(Thread.currentThread().getName() +":"+count);
                        count++;
                        c1.signalAll();
                    }
                    System.out.println("await t2");
                    c2.await();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
