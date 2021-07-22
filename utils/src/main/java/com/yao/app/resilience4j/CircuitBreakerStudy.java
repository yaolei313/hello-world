package com.yao.app.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.vavr.control.Try;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;
import java.util.function.Supplier;

public class CircuitBreakerStudy {

    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
        /*
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .slowCallRateThreshold(50)
            .waitDurationInOpenState(Duration.ofMillis(1000))
            .slowCallDurationThreshold(Duration.ofSeconds(2))
            .permittedNumberOfCallsInHalfOpenState(3)
            .minimumNumberOfCalls(10)
            .slidingWindowType(SlidingWindowType.TIME_BASED)
            .slidingWindowSize(5)
            .recordExceptions(IOException.class, TimeoutException.class)
            .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        */

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();

        CircuitBreaker breaker1 = registry.circuitBreaker("name1");

        CircuitBreakerConfig name2Config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .slowCallRateThreshold(80)
            .waitDurationInOpenState(Duration.ofMillis(1000))
            .slowCallDurationThreshold(Duration.ofSeconds(2))
            .permittedNumberOfCallsInHalfOpenState(3)
            .minimumNumberOfCalls(10)
            .slidingWindowType(SlidingWindowType.TIME_BASED)
            .slidingWindowSize(5)
            .recordExceptions(IOException.class)
            .build();
        CircuitBreaker breaker2 = registry.circuitBreaker("name2", name2Config);

    }

    public static void test2() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .slowCallRateThreshold(50)
            .waitDurationInOpenState(Duration.ofMillis(5000))
            .slowCallDurationThreshold(Duration.ofSeconds(1))
            .permittedNumberOfCallsInHalfOpenState(10)
            .minimumNumberOfCalls(10)
            .slidingWindowType(SlidingWindowType.TIME_BASED)
            .slidingWindowSize(10)
            .build();

        CircuitBreaker breaker = CircuitBreaker.of("test1", config);
        Retry retry = Retry.ofDefaults("retry1");

        Supplier<String> supplier = () -> {
            Random r = new Random();
            int i = r.nextInt(10); // [0,9]
            if (i < 5) {
                return "hello world";
            } else {
                throw new RuntimeException("test");
            }
        };
        Supplier<String> decorated = CircuitBreaker.decorateSupplier(breaker, supplier);

        // decorated = Retry.decorateSupplier(retry, decorated);

        for (int i = 0; i < 10; i++) {
            Try<String> result = Try.ofSupplier(decorated).recover(throwable -> "recover from exception");
            System.out.println(i + "," + result.get());
        }
    }
}
