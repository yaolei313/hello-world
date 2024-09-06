package com.yao.app.java.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockStudy {

    public void print() {
        try {
            ReentrantLock lock = new ReentrantLock();
            Condition[] conditions = new Condition[3];
            for (int i = 0; i < 3; i++) {
                conditions[i] = lock.newCondition();
            }
            PrintThread t1 = new PrintThread(0, lock, conditions);
            PrintThread t2 = new PrintThread(1, lock, conditions);
            PrintThread t3 = new PrintThread(2, lock, conditions);

            t1.start();
            t2.start();
            t3.start();

            t1.join();
            t2.join();
            t3.join();
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class PrintThread extends Thread {
        private static int count = 0;
        private int id;
        private ReentrantLock lock;
        private Condition[] conditions;
        public PrintThread(int id, ReentrantLock lock, Condition[] conditions) {
            this.id = id;
            this.lock = lock;
            this.conditions = conditions;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                while (count <= 100) {
                    System.out.println("Thread" + id +":" + count);
                    count++;
                    conditions[(id + 1) % conditions.length].signal();
                    conditions[id].await();
                }
                // 确保其他线程也能退出condition
                if (count > 100) {
                    conditions[(id + 1) % conditions.length].signal();
                }
                System.out.println("end print");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockStudy r = new ReentrantLockStudy();
        r.print();
    }
}
