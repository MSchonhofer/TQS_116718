package com.example.airquality.model;

import lombok.Data;

import java.util.Objects;


@Data
public class City {
    private Long id;
    private String name;
    private String timestamp;
    private Long aqi;
    private Double co;
    private Double no2;
    private Double o3;
    private Double pm10;
    private Double pm25;
    private Double so2;

    public City(){
    }

    public City(Long id, String name, String timestamp, Long aqi, Double co, Double no2, Double o3, Double pm10, Double pm25, Double so2) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.aqi = aqi;
        this.co = co;
        this.no2 = no2;
        this.o3 = o3;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.so2 = so2;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City city)) return false;
        return Objects.equals(id, city.id) && Objects.equals(name, city.name) && Objects.equals(timestamp, city.timestamp) && Objects.equals(aqi, city.aqi) && Objects.equals(co, city.co) && Objects.equals(no2, city.no2) && Objects.equals(o3, city.o3) && Objects.equals(pm10, city.pm10) && Objects.equals(pm25, city.pm25) && Objects.equals(so2, city.so2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, timestamp, aqi, co, no2, o3, pm10, pm25, so2);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", aqi=" + aqi +
                ", co=" + co +
                ", no2=" + no2 +
                ", o3=" + o3 +
                ", pm10=" + pm10 +
                ", pm25=" + pm25 +
                ", so2=" + so2 +
                '}';
    }
}
