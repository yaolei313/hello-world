package com.yao.app.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yaolei
 */
public class VolatileStudy {

    public static void main(String[] args) {
        ExecutorService executorService =  Executors.newFixedThreadPool(20);
        StatusHolder statusHolder = new StatusHolder();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> statusHolder.modifyStatus());
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(statusHolder);

    }

    private static class StatusHolder {
        public volatile int count = 0;

        public int a = 0;

        public void modifyStatus(){
            count = count + 10;
            a = a + 1;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("StatusHolder{");
            sb.append("count=").append(count);
            sb.append(", a=").append(a);
            sb.append('}');
            return sb.toString();
        }
    }

}
