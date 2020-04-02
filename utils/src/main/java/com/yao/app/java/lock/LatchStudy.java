package com.yao.app.java.lock;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by yaolei02 on 2019/1/30.
 */
public class LatchStudy {
    public static void main(String[] args) {

        try {
            CountDownLatch latch = new CountDownLatch(5);

            List<Thread> threads = ThreadUtils.createTask(5, () -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " start do job.");
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "'s job finished.");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("the boss distributes jobs");
            ThreadUtils.startTask(threads);

            System.out.println("the boss is waiting");
            latch.await();
            System.out.println("all job finished. the boss go away");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
