package com.yao.app.java.date;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.Year;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 为什么没有getMillis方法
 * <p>
 * JSR-310 is based on nanoseconds, not milliseconds.
 * As such, the minimal set of sensible methods are based on hour, minutes, second and nanosecond.
 * The decision to have a nanosecond base was one of the original decisions of the project, and one
 * that I strongly believe to be correct.
 * Adding a method for millis would overlap that of nanosecond is a non-obvious way.
 * Users would have to think about whether the nano field was nano-of-second or nano-of-milli for example.
 * Adding a confusing additional method is not desirable, so the method was omitted.
 * As pointed out, the alternative get(MILLI_OF_SECOND) is available.
 * <p>
 * <p>
 * Created by yaolei02 on 2018/11/30.
 */
public class Test {
    public static void main(String[] args) {
        DateTimeFormatter YYYY_MM_DD_HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter YYYY_MM_DD_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

        System.out.println("--------all object is immutable------------");
        // ZoneDateTime会涉及夏令时，比较恶心，尽量用localdatetime
        ZonedDateTime dt = ZonedDateTime.now();
        System.out.println(dt.toString());
        System.out.println(dt.format(YYYY_MM_DD_HH_MM_SS_FORMATTER));
        System.out.println(dt.withYear(2000).format(YYYY_MM_DD_HH_MM_SS_FORMATTER));
        System.out.println(dt.plusDays(10).format(YYYY_MM_DD_HH_MM_SS_FORMATTER));
        System.out.println(dt.withHour(12).withMinute(0).withSecond(0).withNano(0).format(YYYY_MM_DD_HH_MM_SS_FORMATTER));
        System.out.println("-----------------------");
        System.out.println(dt.getMonth());
        System.out.println("----leap year 闰年 整百数，能整除400是闰年，非整百数，能整除4为闰年----");
        System.out.println(Year.from(dt).isLeap());
        System.out.println(Year.from(dt.withYear(2000)).isLeap());
        System.out.println(Year.of(1900).isLeap());
        System.out.println("---------------------");


        LocalDate ld = LocalDate.from(dt);
        System.out.println(ld.format(YYYY_MM_DD_HH_MM_SS_FORMATTER));
        System.out.println(ld.format(YYYY_MM_DD_FORMATTER));

        LocalTime lt = LocalTime.from(dt);
        System.out.println(lt.format(YYYY_MM_DD_HH_MM_SS_FORMATTER));
        System.out.println(lt.format(HH_MM_SS_FORMATTER));

        LocalDateTime ldt = LocalDateTime.from(dt);
        System.out.println(ldt.format(YYYY_MM_DD_HH_MM_SS_FORMATTER));
        System.out.println(ldt.format(YYYY_MM_DD_FORMATTER));
        System.out.println(ldt.format(HH_MM_SS_FORMATTER));

        System.out.println("---------Interval Period Duration------------");

        // 对比joda
        // Interval -- holds a start and end date-time
        // Duration -- holds a period such as 6 months, 3 days and 7 hours
        // Period --

        // JDK8
        // 可以看做时间线上的一个点，即瞬时时间，0s表示1970-01-01 0:0:0这个点, 之前的时间对应值为负数
        Instant instant = Instant.from(dt);
        System.out.println(instant.getEpochSecond());

        // A time-based amount of time,底层存储的是seconds和nanoseconds
        Duration d = Duration.between(ldt, LocalDateTime.now());
        System.out.println(d.toDays());
        System.out.println(d.toHours());

        Period p = Period.ofDays(10);
        System.out.println(dt.plus(p));

    }

}
