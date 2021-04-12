package com.yao.app.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.util.concurrent.TimeUnit;

public class CaffeineStudy {

    public static void main(String[] args) {
        LoadingCache<String, String> graphs = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .refreshAfterWrite(1, TimeUnit.MINUTES)
            .build(key -> createExpensiveGraph(key));
    }

    private static String createExpensiveGraph(String key) {
        return key + "123";
    }
}
