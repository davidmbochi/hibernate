package com.javawhizz.hibernate_jpa_course.config;

import com.javawhizz.hibernate_jpa_course.entity.Course;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.util.concurrent.TimeUnit;


@Configuration
public class EhCacheManagerConfig {

    @Bean
    public CacheManager cacheManager(){
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();
        MutableConfiguration<Long, Course> config =
                new MutableConfiguration<Long, Course>()
                .setTypes(Long.class, Course.class)
                .setStoreByValue(true)
                .setExpiryPolicyFactory(CreatedExpiryPolicy
                        .factoryOf(new Duration(TimeUnit.SECONDS, 1L)));

        cacheManager.createCache("CourseCache", config);
        return cacheManager;
    }
}
