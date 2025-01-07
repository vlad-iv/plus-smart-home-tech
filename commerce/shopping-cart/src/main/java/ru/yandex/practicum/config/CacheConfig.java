package ru.yandex.practicum.config;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfig {
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("orders");
	}

	@Bean
	public Caffeine caffeineConfig() {
		return Caffeine.newBuilder()
				.maximumSize(1_000)
				.expireAfterWrite(60, TimeUnit.MINUTES);
	}

	@Bean
	public CacheManager cacheManager(Caffeine caffeine) {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.setCaffeine(caffeine);
		caffeineCacheManager.setCacheNames(Collections.singleton("cart"));
		return caffeineCacheManager;
	}
}
