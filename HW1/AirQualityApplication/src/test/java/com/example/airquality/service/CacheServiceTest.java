package com.example.airquality.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CacheServiceTest {

    private CacheService emptyCacheService;
    private CacheService expiringCacheService;


    @BeforeEach
    public void setUp() {
        emptyCacheService = new CacheService();
        expiringCacheService = new CacheService(3000); // 3 seconds

    }

    @DisplayName("The cache service should be empty upon creation")
    @Test
    void givenEmptyCache_whenGetFromCache_thenReturnNull() {
        String object = "object";
        assertThat(emptyCacheService.getFromCache(object), equalTo(null));
        assertThat(emptyCacheService.getStatistics().getRequests(), is(1));
        assertThat(emptyCacheService.getStatistics().getMisses(), is(1));
    }

    @DisplayName("The cache service should return the correct value, when asked for a key")
    @Test
    void givenCache_whenGetKey_thenReturnValue() {
        String key = "key";
        String value = "value";

        emptyCacheService.storeInCache(key, value);

        assertThat(emptyCacheService.getFromCache(key), equalTo(value));
        assertThat(emptyCacheService.getStatistics().getRequests(), is(1));
        assertThat(emptyCacheService.getStatistics().getHits(), is(1));
    }

    @DisplayName("The cache service should return null, when the storing time expires")
    @Test
    void givenExpiringCache_whenGetKeyAfterTimeExpires_thenReturnValue() {
        String key = "key";
        String value = "value";

        // Storing pair in cache
        expiringCacheService.storeInCache(key, value);

        try {
            Thread.sleep(3001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(expiringCacheService.getFromCache(key), equalTo(null));
        assertThat(expiringCacheService.getStatistics().getMisses(), is(1));
    }


}