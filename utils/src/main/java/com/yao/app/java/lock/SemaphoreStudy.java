package com.yao.app.java.lock;

import java.util.List;
import java.util.concurrent.Semaphore;
import org.apache.commons.lang3.RandomUtils;

/**
 * Created by yaolei02 on 2019/1/31.
 */
public class SemaphoreStudy {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        List<Thread> threads = ThreadUtils.createAndStartTask(10, () -> {
            try {
                int count = semaphore.availablePermits();
                System.out.println(Thread.currentThread().getName() + " arrive bank. the bank have " + count + " idle windows");
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " get one.");
                Thread.sleep(1000 + RandomUtils.nextInt(10, 1000));
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + " leave. release one");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("main thread waiting");
        ThreadUtils.join(threads);
        System.out.println("main thread end");
    }
}
