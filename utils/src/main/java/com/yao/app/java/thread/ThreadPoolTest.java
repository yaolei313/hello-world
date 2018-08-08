package com.yao.app.java.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

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
