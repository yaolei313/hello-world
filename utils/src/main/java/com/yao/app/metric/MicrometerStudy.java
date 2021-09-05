package com.yao.app.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import java.net.InetAddress;

public class MicrometerStudy {

    public static void main(String[] args) {
        try {
            test1();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        // Counter,
        // Gauge,
        // DistributionSummary,
        // LongTaskTimer,
        // FunctionCounter,
        // FunctionTimer,
        // TimeGauge.

        Counter c1 = registry.counter("db", "a1", "read");
        Counter c2 = registry.counter("db", "a1", "write");
        c1.increment();
        c2.increment();
    }
}
