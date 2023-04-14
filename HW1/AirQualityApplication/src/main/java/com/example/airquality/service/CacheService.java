package com.example.airquality.service;

import com.example.airquality.cache.Cache;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    private PassiveExpiringMap<String, String> map; // expiring time in ms
    private Cache cache;

    Logger logger = LoggerFactory.getLogger(CacheService.class);

    public CacheService() {
        this.map = new PassiveExpiringMap<>(TimeUnit.MINUTES.toMillis(2));
        this.cache = new Cache();
    }

    // specify the expiration time for the cache
    public CacheService(long expirationTime) {
        this.map = new PassiveExpiringMap<>(expirationTime); // default 2 minutes
        this.cache = new Cache();
    }

    public Cache getStatistics(){
        logger.info("[CacheService] Retrieving cache statistics");
        return this.cache;
    }

    public String storeInCache(String key, String value){
        logger.info("[CacheService] Storing something in cache");
        return this.map.put(key, value);
    }

    public String getFromCache(String key){
        logger.info("[CacheService] Attempting to get something from cache");
        String value = this.map.get(key);
        this.cache.addRequestsCount();
        if (value != null){
            logger.info("[CacheService] New hit in cache");
            this.cache.addHit();
        } else {
            logger.info("[CacheService] New miss in cache");
            this.cache.addMiss();
        }
        return value;
    }
}
