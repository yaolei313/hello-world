package com.yao.app.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTaskStudy {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        
        Fibonacci task = new Fibonacci(10);
        ForkJoinTask<Integer> r = pool.submit(task);
        try {
            System.out.println(r.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static class Fibonacci extends RecursiveTask<Integer> {

        private static final long serialVersionUID = 1L;
        
        final int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        protected Integer compute() {
            if (n <= 1)
                return n;
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            f2.fork();
            return f2.join() + f1.join();
        }

    }

}
