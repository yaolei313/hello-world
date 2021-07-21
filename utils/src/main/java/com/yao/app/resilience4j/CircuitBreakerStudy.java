package com.yao.app.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public class CircuitBreakerStudy {

    public static void main(String[] args) {

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
            .slowCallRateThreshold(50)
            .waitDurationInOpenState(Duration.ofMillis(1000))
            .slowCallDurationThreshold(Duration.ofSeconds(2))
            .permittedNumberOfCallsInHalfOpenState(3)
            .minimumNumberOfCalls(10)
            .slidingWindowType(SlidingWindowType.TIME_BASED)
            .slidingWindowSize(5)
            .recordExceptions(IOException.class, TimeoutException.class)
            .build();
        CircuitBreaker breaker2 = registry.circuitBreaker("name2", name2Config);

    }

    public static void test2() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
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

        CircuitBreaker breaker = CircuitBreaker.of("test1", config);

        Supplier<String> supplier = () -> "hello world";
        Supplier<String> decorated = CircuitBreaker.decorateSupplier(breaker, supplier);
        String result = Try.ofSupplier(decorated).get();

    }
}
