package com.raminq.security.configuration;


import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Objects.requireNonNull;

/*
https://examples.javacodegeeks.com/setting-up-ehcache-without-the-xml-hassle/
 */
@Configuration
@EnableCaching
public class CacheConfig {

    public static final String Users_Cache = "users";

    @Bean
    public EhCacheManagerFactoryBean cacheManager() {
        return new EhCacheManagerFactoryBean();
    }

    @Bean
    public EhCacheCacheManager testEhCacheManager() {
        CacheConfiguration usersEhCacheConfig = new CacheConfiguration()
                .eternal(false)                     // if true, timeouts are ignored
                .timeToIdleSeconds(3600)            // time since last accessed before item is marked for removal
                .timeToLiveSeconds(3600)            // time since inserted before item is marked for removal
                .maxEntriesLocalHeap(10)            // total items that can be stored in cache
                .memoryStoreEvictionPolicy("LRU")   // eviction policy for when items exceed cache. LRU = Least Recently Used
                .name(Users_Cache);

        Cache usersCache = new Cache(usersEhCacheConfig);

        requireNonNull(cacheManager().getObject()).addCache(usersCache);
        return new EhCacheCacheManager(requireNonNull(cacheManager().getObject()));
    }


}
