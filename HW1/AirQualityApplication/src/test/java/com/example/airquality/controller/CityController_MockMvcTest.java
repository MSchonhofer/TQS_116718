package com.example.airquality.controller;

import com.example.airquality.service.CacheService;
import com.example.airquality.cache.Cache;
import com.example.airquality.model.City;
import com.example.airquality.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CityController_MockMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityService cityService;

    @MockBean
    private CacheService cacheService;

    @Test
    void whenGetValidCity_thenReturnCityAQI() throws Exception {
        City london = new City(6132L, "London", "2023-04-12 10:00:00 +02:00", 37L, null, 29.3, 36.6, 21.0, null, null);

        when(cityService.getCityAirQuality(any())).thenReturn(london);

        mvc.perform(get("/api/city/london").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("London")));

        verify(cityService, times(1)).getCityAirQuality(any());
    }

    @Test
    void whenGetInvalidCity_thenReturnNullCity() throws Exception {
        City invalidCity = new City();

        when(cityService.getCityAirQuality(any())).thenReturn(invalidCity);

        mvc.perform(get("/api/city/XYZ").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", aMapWithSize(10)));

        verify(cityService, times(1)).getCityAirQuality(any());
    }

    @Test
    void whenCacheRequested_thenReturnCacheStats() throws Exception {
        Cache emptyCache = new Cache();

        when(cacheService.getStatistics()).thenReturn(emptyCache);

        mvc.perform(get("/api/cache").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(3)));

        verify(cacheService, times(1)).getStatistics();
    }

}
