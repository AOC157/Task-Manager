package com.example.taskmanager.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CacheConfig {

    private static final String TASKS_CACHE_NAME = "tasks";
    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(TASKS_CACHE_NAME);
    }

    //This method will evict cache every 20 minutes
    @CacheEvict(allEntries = true, value = {TASKS_CACHE_NAME})
    @Scheduled(fixedDelay = 20 * 60 * 1000, initialDelay = 500)
    public void reportCacheEvict() {
        logger.info("cache evicted");
    }
}