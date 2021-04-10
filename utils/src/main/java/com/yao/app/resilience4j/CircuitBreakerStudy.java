package com.yao.app.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

public class CircuitBreakerStudy {

    public static void main(String[] args) {
        CircuitBreakerRegistry circuitBreakerRegistry =
            CircuitBreakerRegistry.ofDefaults();
    }
}
