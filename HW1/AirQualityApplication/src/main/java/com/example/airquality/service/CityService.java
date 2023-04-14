package com.example.airquality.service;

import com.example.airquality.model.City;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CityService {

    @Autowired
    private CacheService cacheService;
    static final String API_BASEURL = "https://api.waqi.info/feed/";
    static final String API_TOKEN = "/?token=6addccba8550e0c70f2d10e8dc3e1d5d2e89238b";

    Logger logger = LoggerFactory.getLogger(CityService.class);

    private void getWarning(Exception e) {
        logger.warn("[CityService] Exception caught!", e);
    }

    public City getCityAirQuality(String cityName) {
        // Utilities
        RestTemplate restTemplate = new RestTemplate();
        // Try fetching data from Cache
        String incache = cacheService.getFromCache(cityName);
        if (incache != null){
            logger.info("[CityService] SUCCESS! City found in cache! --> HIT");
        } else {
            logger.info("[CityService] FAILURE! City not found in cache! --> MISS");
            String url = API_BASEURL + cityName + API_TOKEN;
            incache = restTemplate.getForObject(url, String.class);
            cacheService.storeInCache(cityName, incache);
        }

        // JSON-STRING Conversion
        JSONObject object = new JSONObject(incache);
        String status = object.get("status").toString();
        if (!status.equals("ok")){
            logger.error("[CityService] Status Not OK");
            return new City();
        }
        City output = new City();
        JSONObject data = object.getJSONObject("data");
        JSONObject iaqi = data.getJSONObject("iaqi");
        JSONObject city = data.getJSONObject("city");
        JSONObject time = data.getJSONObject("time");
        // Set the ID
        Integer id = data.getInt("idx");
        output.setId((long) id);

        // Set Location value
        try {
            String name = city.getString("name");
            output.setName(name);
        } catch (JSONException exception) {
            getWarning(exception);
            output.setName(null);
        }
        // Set Timestamp value
        try {
            String timestamp = time.getString("s");
            String timezone = time.getString("tz");
            output.setTimestamp(timestamp+' '+timezone);
        } catch (JSONException exception) {
            getWarning(exception);
            output.setTimestamp(null);
        }
        // Set AQI value
        try {
            Integer aqi = data.getInt("aqi");
            output.setAqi((long) aqi);
        } catch (JSONException exception) {
            getWarning(exception);
            output.setAqi(null);
        }
        // Set CO value
        try {
            Double co = iaqi.getJSONObject("co").getDouble("v");
            output.setCo(co);
        } catch (JSONException exception) {
            getWarning(exception);
            output.setCo(null);
        }
        // Set NO2 value
        try {
            Double no2 = iaqi.getJSONObject("no2").getDouble("v");
            output.setNo2(no2);
        } catch (JSONException exception) {
            getWarning(exception);
            output.setNo2(null);
        }
        // Set O3 value
        try {
            Double o3 = iaqi.getJSONObject("o3").getDouble("v");
            output.setO3(o3);
        } catch (JSONException exception) {
            getWarning(exception);
            output.setO3(null);
        }
        // Set PM10 value
        try {
            Double pm10 = iaqi.getJSONObject("pm10").getDouble("v");
            output.setPm10(pm10);
        } catch (JSONException exception) {
            getWarning(exception);
            output.setPm10(null);
        }
        // Set PM25 value
        try {
            Double pm25 = iaqi.getJSONObject("pm25").getDouble("v");
            output.setPm25(pm25);
        } catch (JSONException exception) {
            getWarning(exception);
            output.setPm25(null);
        }
        // Set SO2 value
        try {
            Double so2 = iaqi.getJSONObject("so2").getDouble("v");
            output.setSo2(so2);
        } catch (JSONException exception) {
            getWarning(exception);
            output.setSo2(null);
        }
        return output;
    }

}
