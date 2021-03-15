package com.yao.app.java.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
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

        pool.submit(() -> System.out.println("test"));

        pool.shutdown();

        pool.submit(() -> System.out.println("test2"));

        System.out.println("====");

        // https://stackoverflow.com/questions/6894595/scheduledexecutorservice-exception-handling
        // Any thrown exception or error reaching the executor causes the executor to halt.
        ScheduledExecutorService scheduledExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledExecutor.scheduleAtFixedRate(() -> {
            System.out.println("sleep");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("throw runtime exception");
            throw new RuntimeException("test");
        }, 1, 5, TimeUnit.SECONDS);

        System.out.println("====");
    }
}
