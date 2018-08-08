package com.yao.app.java.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Yao on 2017/12/9.
 */
public class Test2 {

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(15);
        final AtomicInteger errorCount = new AtomicInteger(0);
        int i = 0;
        while (errorCount.get() == 0) {
            System.out.println(errorCount.get());
            i++;
            Future<?> future = service.submit(new NormalJob(String.valueOf(i), errorCount));
            if(future.isDone())
        }
        System.out.println("happen error exit");

    }


    public static class NormalJob implements Runnable {
        private String name;

        private AtomicInteger errorCount;

        public NormalJob(String name, AtomicInteger errorCount) {
            this.name = name;
            this.errorCount = errorCount;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (name.equals("60")) {
                errorCount.incrementAndGet();
            } else {
                System.out.println(
                        "working name:" + name + "\t" + Thread.currentThread().getId() + "/" + Thread.currentThread().getName());
            }

        }
    }
}
