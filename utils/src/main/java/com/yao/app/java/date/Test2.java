package com.yao.app.java.date;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author yaolei313 <yaolei313@gmail.com> Created on 2021-08-20
 */
public class Test2 {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        // 以下3个时间相等
        Instant instant1 = now.toInstant(ZoneOffset.ofHours(8));
        Instant instant2 = Instant.now();
        long a = System.currentTimeMillis();

        System.out.println(instant1);
        System.out.println(instant2);

        long b = instant1.toEpochMilli();
        long c = instant2.toEpochMilli();
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        System.out.println((a - b) / 3600);
        System.out.println((a - c) / 3600);
    }
}
