package com.yao.app.java.lock;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import org.apache.commons.lang3.RandomUtils;

/**
 * Created by yaolei02 on 2019/1/31.
 */
public class BarrierStudy {
    public static void main(String[] args) {
        try {
            CyclicBarrier barrier = new CyclicBarrier(5);

            List<Thread> threads = ThreadUtils.createAndStartTask(5, () -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " start do phase 1.");
                    Thread.sleep(1000 + RandomUtils.nextInt(10, 1000));
                    System.out.println(Thread.currentThread().getName() + "'s phase 1 finished. waiting others");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " start do phase 2.");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("main thread waiting");
            ThreadUtils.join(threads);
            System.out.println("main thread end");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
