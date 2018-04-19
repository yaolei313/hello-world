package com.yao.app.java.nio.memory;

import sun.misc.JavaNioAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Scanner;

/**
 * https://stackoverflow.com/questions/20058489/is-there-a-way-to-measure-direct-memory-usage-in-java
 * <p>
 * Created by yaolei02 on 2018/1/4.
 */
public class MeasureDirectMemory {

    private static final int SIZE_1_MB = 1024 * 1024;

    public static void main(String[] args) {
        System.out.println("before allocate 1m:" + Runtime.getRuntime().freeMemory());
        ByteBuffer buffer = ByteBuffer.allocateDirect(SIZE_1_MB);
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
            for (int j = 0; j < str.length(); j++) {
                buffer.put((byte) str.charAt(j));
            }
        }
        buffer.flip();

        System.out.println("before allocate 2m:" + Runtime.getRuntime().freeMemory());

        ByteBuffer buffer2 = ByteBuffer.allocateDirect(SIZE_1_MB * 2);
        buffer2.put((byte) 10);
        buffer2.flip();

        System.out.println("now memory:" + Runtime.getRuntime().freeMemory());

        JavaNioAccess.BufferPool pool = sun.misc.SharedSecrets.getJavaNioAccess().getDirectBufferPool();
        System.out.println("count:" + pool.getCount());
        System.out.println("memory used:" + pool.getMemoryUsed());
        System.out.println("name:" + pool.getName());
        System.out.println("total capacity:" + pool.getTotalCapacity());

        System.out.println("----------------------------------");

        List<BufferPoolMXBean> poolMxBeans = ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class);
        for (BufferPoolMXBean poolMXBean : poolMxBeans) {
            System.out.println(poolMXBean.getName());
            System.out.println(poolMXBean.getCount());
            System.out.println("memory used " + toMB(poolMXBean.getMemoryUsed()));
            System.out.println("total capacity" + toMB(poolMXBean.getTotalCapacity()));
            System.out.println();
        }

        Scanner in = new Scanner(System.in);
        String str = in.next();
        System.out.println("stopping " + str);
    }

    private static String toMB(long init) {
        return (Long.valueOf(init).doubleValue() / (1024 * 1024)) + " MB";
    }

    private static String readLine(String format, Object... args) throws IOException {
        if (System.console() != null) {
            return System.console().readLine(format, args);
        }
        System.out.print(String.format(format, args));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    private static char[] readPassword(String format, Object... args) throws IOException {
        if (System.console() != null)
            return System.console().readPassword(format, args);
        return readLine(format, args).toCharArray();
    }

    private static String[] strings =
        {"A random string value", "The product of an infinite number of monkeys", "Hey hey we're the Monkees", "Opening act for the Monkees: Jimi Hendrix",
            "'Scuse me while I kiss this fly", "Help Me! HelpMe!"};
}
