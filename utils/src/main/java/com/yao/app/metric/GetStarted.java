package com.yao.app.metric;

import static com.codahale.metrics.MetricRegistry.name;

import com.codahale.metrics.*;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 在抽象监控api的时候，可以参考metrics这种形式
 *
 * https://metrics.dropwizard.io/4.1.1/getting-started.html
 *
 * @author lei.yao
 */
public class GetStarted {

    static final MetricRegistry metrics = new MetricRegistry();

    public static void main(String args[]) {
        startConsoleReport();
        startJMXReport();

        // Gauge 不需要统计计算，用来衡量某个瞬时值，比如监控线程池的活跃线程数
        // register可以注册自定义的Metric
        metrics.register(name(GetStarted.class, "size"), (Gauge<Integer>) () -> (new Random()).nextInt());

        // Counter 计数器 a gauge for an AtomicLong instance
        Counter pendingJobs = metrics.counter(name(GetStarted.class, "waiting-job"));
        pendingJobs.inc();
        pendingJobs.dec();

        // Meters 事件发生的速度，会将最近1分钟，5分钟，15分钟的TPS（每秒处理的request数）给打印出来。
        Meter requests = metrics.meter("requests");
        requests.mark();

        // Histograms 统计分布，如最大值，最小值，百分比数据，如75%,90%,98%,99%的数据在哪个范围内。蓄水池抽样
        Histogram responseSizes = metrics.histogram(name(GetStarted.class, "response-sizes"));
        responseSizes.update("responseBody".length());

        // Timer 统计出来时间(nanosecond)分布 [Histograms]及调用频率 a rate of requests in requests per second [meter]
        final Timer responses = metrics.timer(name(GetStarted.class, "responses"));
        final Timer.Context context = responses.time();
        try {
            waitSeconds(1);
        } finally {
            context.stop();
        }

        // service’s health check
        final HealthCheckRegistry healthChecks = new HealthCheckRegistry();
        healthChecks.register("db", new HealthCheck() {

            @Override
            protected Result check() throws Exception {
                Random r = new Random();
                if (r.nextInt() > 100) {
                    return HealthCheck.Result.healthy();
                }
                return HealthCheck.Result.unhealthy("I'm not happy");
            }

        });
        healthChecks.runHealthChecks();

        waitSeconds(120);
    }

    static void startConsoleReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void startJMXReport() {
        // Reporting via JMX
        final JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
        reporter.start();
    }

    static void startCSVReport() {
        final CsvReporter reporter =
                CsvReporter.forRegistry(metrics).formatFor(Locale.US).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS)
                        .build(new File("~/projects/data/"));
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void startSLF4JLogger() {
        final Slf4jReporter reporter =
                Slf4jReporter.forRegistry(metrics).outputTo(LoggerFactory.getLogger("com.example.metrics")).convertRatesTo(TimeUnit.SECONDS)
                        .convertDurationsTo(TimeUnit.MILLISECONDS).build();
        reporter.start(1, TimeUnit.MINUTES);
    }

    static void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
        }
    }

}
