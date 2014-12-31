package com.yao.app.metric;

import java.io.File;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

/**
 * https://dropwizard.github.io/metrics/3.1.0/getting-started/
 * 
 * @author lei.yao
 *
 */
public class GetStarted {

    static final MetricRegistry metrics = new MetricRegistry();

    public static void main(String args[]) {
        startConsoleReport();
        startJMXReport();
        
        // Gauge最简单的度量指标，每次相当于重置这个值。
        metrics.register(MetricRegistry.name(GetStarted.class, "size"),
                new Gauge<Integer>() {
                    @Override
                    public Integer getValue() {
                        return (new Random()).nextInt();
                    }
                });
        
        // Meters 事件发生的速度，会将最近1分钟，5分钟，15分钟的TPS（每秒处理的request数）给打印出来，还有所有时间的TPS。
        Meter requests = metrics.meter("requests");
        requests.mark();
        
        // 计数器a gauge for an AtomicLong instance
        Counter pendingJobs = metrics.counter(MetricRegistry.name(GetStarted.class, "test1"));
        pendingJobs.inc();
        pendingJobs.dec();
        
        // Histograms 统计分布，如最大值，最小值，百分比数据，如75%,90%,98%,99%的数据在哪个范围内。蓄水池抽样
        Histogram responseSizes = metrics.histogram(MetricRegistry.name(GetStarted.class, "response-sizes"));
        responseSizes.update("responseBody".length());
        
        // Timer 统计出来时间(nanosecond)分布 [Histograms]及调用频率 a rate of requests in requests per second [meter]
        final Timer responses = metrics.timer(MetricRegistry.name(GetStarted.class, "responses"));
        final Timer.Context context = responses.time();
        try{
            waitSeconds(1);
        }finally{
            context.stop();
        }
        
        // service’s health check
        final HealthCheckRegistry healthChecks = new HealthCheckRegistry();
        healthChecks.register("db", new HealthCheck(){

            @Override
            protected Result check() throws Exception {
                Random r = new Random();
                if(r.nextInt() > 100){
                    return HealthCheck.Result.healthy();
                }
                return HealthCheck.Result.unhealthy("I'm not happy");
            }
            
        });
        healthChecks.runHealthChecks();
        
        
        
        waitSeconds(120);
    }

    static void startConsoleReport() {
        ConsoleReporter reporter =
                ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS)
                        .convertDurationsTo(TimeUnit.MILLISECONDS).build();
        reporter.start(1, TimeUnit.SECONDS);
    }
    
    static void startJMXReport(){
        // Reporting via JMX
        final JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
        reporter.start();
    }
    
    static void startCSVReport() {
        final CsvReporter reporter = CsvReporter.forRegistry(metrics)
                .formatFor(Locale.US)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build(new File("~/projects/data/"));
        reporter.start(1, TimeUnit.SECONDS);
    }
    
    static void startSLF4JLogger(){
        final Slf4jReporter reporter = Slf4jReporter.forRegistry(metrics)
                .outputTo(LoggerFactory.getLogger("com.example.metrics"))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.MINUTES);
    }

    static void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
        }
    }

}
