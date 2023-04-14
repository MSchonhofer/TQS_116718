package com.example.airquality.service;

import com.example.airquality.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    private String valid_response;

    @Mock(lenient = true)
    private CacheService cacheService;

    @InjectMocks
    private CityService mockCityService;


    @BeforeEach
    void setUp() {
        valid_response = "{\"status\":\"ok\",\"data\":{\"aqi\":37,\"idx\":6132,\"attributions\":[{\"url\":\"http://www.stadtentwicklung.berlin.de/umwelt/luftqualitaet/\",\"name\":\"Berlin Air Quality - (Luftqualität in Berlin)\",\"logo\":\"Germany-Berlin.png\"},{\"url\":\"https://waqi.info/\",\"name\":\"World Air Quality Index Project\"}],\"city\":{\"geo\":[52.5200066,13.404954],\"name\":\"Berlin, Germany\",\"url\":\"https://aqicn.org/city/germany/berlin\"},\"dominentpol\":\"o3\",\"iaqi\":{\"h\":{\"v\":45.3},\"no2\":{\"v\":29.3},\"o3\":{\"v\":36.6},\"p\":{\"v\":1015.2},\"pm10\":{\"v\":21},\"t\":{\"v\":13.3},\"w\":{\"v\":6.3},\"wg\":{\"v\":16.6}},\"time\":{\"s\":\"2021-05-08 20:00:00\",\"tz\":\"+02:00\",\"v\":1620504000,\"iso\":\"2021-05-08T20:00:00+02:00\"},\"forecast\":{\"daily\":{\"o3\":[{\"avg\":29,\"day\":\"2021-05-06\",\"max\":33,\"min\":18},{\"avg\":25,\"day\":\"2021-05-07\",\"max\":38,\"min\":10},{\"avg\":31,\"day\":\"2021-05-08\",\"max\":41,\"min\":26},{\"avg\":34,\"day\":\"2021-05-09\",\"max\":40,\"min\":30},{\"avg\":35,\"day\":\"2021-05-10\",\"max\":46,\"min\":30},{\"avg\":30,\"day\":\"2021-05-11\",\"max\":45,\"min\":25},{\"avg\":28,\"day\":\"2021-05-12\",\"max\":28,\"min\":24}],\"pm10\":[{\"avg\":8,\"day\":\"2021-05-06\",\"max\":11,\"min\":6},{\"avg\":8,\"day\":\"2021-05-07\",\"max\":14,\"min\":4},{\"avg\":8,\"day\":\"2021-05-08\",\"max\":10,\"min\":5},{\"avg\":7,\"day\":\"2021-05-09\",\"max\":8,\"min\":4},{\"avg\":9,\"day\":\"2021-05-10\",\"max\":12,\"min\":6},{\"avg\":11,\"day\":\"2021-05-11\",\"max\":13,\"min\":8},{\"avg\":11,\"day\":\"2021-05-12\",\"max\":11,\"min\":9}],\"pm25\":[{\"avg\":22,\"day\":\"2021-05-06\",\"max\":32,\"min\":16},{\"avg\":25,\"day\":\"2021-05-07\",\"max\":44,\"min\":11},{\"avg\":25,\"day\":\"2021-05-08\",\"max\":31,\"min\":14},{\"avg\":22,\"day\":\"2021-05-09\",\"max\":28,\"min\":15},{\"avg\":31,\"day\":\"2021-05-10\",\"max\":36,\"min\":20},{\"avg\":39,\"day\":\"2021-05-11\",\"max\":46,\"min\":27},{\"avg\":43,\"day\":\"2021-05-12\",\"max\":43,\"min\":35}],\"uvi\":[{\"avg\":0,\"day\":\"2021-05-06\",\"max\":4,\"min\":0},{\"avg\":0,\"day\":\"2021-05-07\",\"max\":2,\"min\":0},{\"avg\":1,\"day\":\"2021-05-08\",\"max\":3,\"min\":0},{\"avg\":1,\"day\":\"2021-05-09\",\"max\":6,\"min\":0},{\"avg\":1,\"day\":\"2021-05-10\",\"max\":6,\"min\":0},{\"avg\":1,\"day\":\"2021-05-11\",\"max\":4,\"min\":0},{\"avg\":0,\"day\":\"2021-05-12\",\"max\":2,\"min\":0},{\"avg\":0,\"day\":\"2021-05-13\",\"max\":0,\"min\":0}]}},\"debug\":{\"sync\":\"2021-05-09T03:33:46+09:00\"}}}";

        when(cacheService.getFromCache("berlin")).thenReturn(valid_response);
        when(cacheService.getFromCache("XYZ")).thenReturn(null);

    }

    @Test
    void whenValidCity_returnAQI() {
        String city_name = "Berlin, Germany";

        City found = mockCityService.getCityAirQuality("berlin");

        assertThat(found.getName()).isEqualTo(city_name);
        verify(cacheService, times(1)).getFromCache(any());

    }

    @Test
    void whenInvalidCity_returnNullCity() {

        City found = mockCityService.getCityAirQuality("XYZ");

        assertThat(found).isEqualTo(new City());
        verify(cacheService, times(1)).getFromCache(any());


    }
}
