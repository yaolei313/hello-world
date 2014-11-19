package com.yao.app.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Job t1 = new Job("job1");
        Job t2 = new Job("job2");
        Job t3 = new Job("job3");
        Job t4 = new Job("job4");
        Job t5 = new Job("job5");

        // 将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);
        // 关闭线程池
        pool.shutdown();

    }

    public static class Job implements Runnable {
        private String name;

        public Job(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("name:" + name + "\t"
                    + Thread.currentThread().getId() + "/"
                    + Thread.currentThread().getName());

        }
    }
}
