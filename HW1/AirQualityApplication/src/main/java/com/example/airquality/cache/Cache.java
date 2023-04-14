package com.example.airquality.cache;

import lombok.Data;

import java.util.Objects;

@Data
public class Cache {

    private Integer requests;
    private Integer hits;
    private Integer misses;

    public Cache(){
        this.requests = 0;
        this.hits = 0;
        this.misses = 0;
    }

    public void addRequestsCount() {
        this.requests++;
    }

    public void addHit() {
        this.hits++;
    }

    public void addMiss() {
        this.misses++;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cache cache)) return false;
        return Objects.equals(requests, cache.requests) && Objects.equals(hits, cache.hits) && Objects.equals(misses, cache.misses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requests, hits, misses);
    }

    @Override
    public String toString() {
        return "Cache{" +
                "requests=" + requests +
                ", hits=" + hits +
                ", misses=" + misses +
                '}';
    }
}
