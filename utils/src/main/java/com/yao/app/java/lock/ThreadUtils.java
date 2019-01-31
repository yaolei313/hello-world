package com.yao.app.java.lock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaolei02 on 2019/1/31.
 */
public class ThreadUtils {

    public static List<Thread> createAndStartTask(int count, Runnable runnable) {
        List<Thread> threadList = createTask(count, runnable);
        startTask(threadList);
        return threadList;
    }

    public static List<Thread> createTask(int count, Runnable runnable) {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Thread t = new Thread(runnable);
            t.setName("man-" + i);
            threadList.add(t);
        }
        return threadList;
    }

    public static void startTask(List<Thread> threadList) {
        threadList.stream().forEach((t) -> t.start());
    }

    public static void join(List<Thread> threadList) {
        threadList.stream().forEach((t) -> {
            try {
                t.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
