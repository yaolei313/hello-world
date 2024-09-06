package com.yao.app.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * TODO 非Prometheus的类型
 * Gauge 不需要统计计算，用来衡量某个瞬时值，比如监控线程池的活跃线程数
 * Counter 计数器 a gauge for an AtomicLong instance
 * Meters 事件发生的速度，会将最近1分钟，5分钟，15分钟的TPS（每秒处理的request数）给打印出来。
 * Histograms 统计分布，如最大值，最小值，百分比数据，如75%,90%,98%,99%的数据在哪个范围内。
 * Timer 统计出来时间(nanosecond)分布 [Histograms]及调用频率 a rate of requests in requests per second [meter]
 */
public class MicrometerStudy {

    public static void main(String[] args) {
        try {
            test1();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        sc.next();
    }

    public static void test1() throws Exception {
        CompositeMeterRegistry registry = new CompositeMeterRegistry();

        MeterRegistry simple = new SimpleMeterRegistry();

        PrometheusConfig config = PrometheusConfig.DEFAULT;
        MeterRegistry prometheus = new PrometheusMeterRegistry(config);

        registry.add(simple);
        registry.add(prometheus);

        String host = InetAddress.getLocalHost().getHostName();
        String env = "prod";
        String region = "us-east-1";

        // Adding common tags for app name, host, region, etc is a highly recommended practice.
        registry.config().commonTags("host", host, "env", env, "region", region);
        //registry.config().commonTags(Arrays.asList(Tag.of("stack", "prod"), Tag.of("region", "us-east-1")));

        // Timer, measure时间和次数
        // Counter, 次数，只会增加
        // Gauge, 可以增加或减少
        // DistributionSummary,
        // LongTaskTimer,
        // FunctionCounter,
        // FunctionTimer,
        // TimeGauge.

        Counter c1 = registry.counter("database.calls", "passport", "read");
        Counter c2 = registry.counter("http.requests", "uri", "/api/users");
        c1.increment();
        c2.increment();

        registry.gauge("cpu.usage", 10);
    }
}
