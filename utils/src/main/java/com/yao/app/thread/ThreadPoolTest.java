package com.yao.app.thread;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/**
 * Created by yaolei02 on 2017/6/15.
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new SynchronousQueue<Runnable>();
        ExecutorService pool =
            new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, queue, new CustomizableThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
            }
        });

        pool.shutdown();

        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("test2");
            }
        });
    }
}
