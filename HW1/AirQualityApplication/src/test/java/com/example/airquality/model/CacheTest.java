package com.example.airquality.model;

import com.example.airquality.cache.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CacheTest {

    private Cache emptyCache;
    private Cache request;
    private Cache hit;
    private Cache miss;

    @BeforeEach
    public void setUp() {
        emptyCache = new Cache();

        request = new Cache();
        request.addRequestsCount();

        hit = new Cache();
        hit.addHit();

        miss = new Cache();
        miss.addMiss();
    }

    @DisplayName("The cache should be empty upon construction")
    @Test
    void isEmpty_Test() {
        assertEquals(0, emptyCache.getRequests());
        assertEquals(0, emptyCache.getHits());
        assertEquals(0, emptyCache.getMisses());
    }

    @DisplayName("The cache shouldn't be empty, if it was previously requested")
    @Test
    void hasRequests_Test() {
        assertEquals(1, request.getRequests());
        assertNotEquals(0, request.getRequests());
    }

    @DisplayName("The cache shouldn't be empty, if it was hit")
    @Test
    void hasHits_Test() {
        assertEquals(1, hit.getHits());
        assertNotEquals(0, hit.getHits());
    }

    @DisplayName("The cache shouldn't be empty, if it was missed")
    @Test
    void hasMisses_Test() {
        assertEquals(1, miss.getMisses());
        assertNotEquals(0, miss.getMisses());
    }
}
