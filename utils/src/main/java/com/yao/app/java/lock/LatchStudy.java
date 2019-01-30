package com.yao.app.java.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by yaolei02 on 2019/1/30.
 */
public class LatchStudy {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);

        List<Thread> threadList = new ArrayList<>();
        for(int i = 0;i<5;i++){
            Thread t = new Thread(()->{
                latch.countDown();
            });
            threadList.add(t);
        }

    }
}
