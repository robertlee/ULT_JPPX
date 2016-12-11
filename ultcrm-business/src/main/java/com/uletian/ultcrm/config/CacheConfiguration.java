/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * @author robertxie
 * 2015年9月15日
 */
@Configuration

public class CacheConfiguration {
	
    
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("tech");
    }
    
    
    
}
