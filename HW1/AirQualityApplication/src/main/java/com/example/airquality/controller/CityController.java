package com.example.airquality.controller;

import com.example.airquality.cache.Cache;
import com.example.airquality.model.City;
import com.example.airquality.service.CacheService;
import com.example.airquality.service.CityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class CityController {

    Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityService cityService;

    @Autowired
    private CacheService cacheService;

    @ApiOperation(value = "Get the AQI of a desired city")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = City.class, responseContainer = "List")})
    @GetMapping("/city")
    public String getCityAQI(@ApiParam(
            name =  "form_city",
            type = "City",
            value = "Name of the desired location",
            example = "London",
            required = true) City formCity, Model model) {
        String userInput = formCity.getName();
        logger.info("[CityController] Displaying Air Quality Index for city {}", userInput);
        City fromService = cityService.getCityAirQuality(userInput);
        if (fromService.getName() == null){
            return "errorpage";
        }
        model.addAttribute("city", fromService);
        model.addAttribute("user_input", userInput);
        return "cityinfo";
    }

    @ApiOperation(value = "[Developer Oriented] Get the Air Quality Index of a specific location")
    @GetMapping("/api/city/{city}")
    public ResponseEntity<City> getAPICityAQI(@ApiParam(
            name =  "city",
            type = "String",
            value = "Name of the desired city",
            example = "London",
            required = true) @PathVariable String city) {
        logger.info("[CacheService] Fetching from API Air Quality Index for: {}", city);
        City fromService = cityService.getCityAirQuality(city);
        if (fromService.getName() == null){
            City output = new City();
            return new ResponseEntity<>(output, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(fromService, HttpStatus.OK);
        }

    }

    @ApiOperation(value = "Get the Cache's Statistics")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Cache.class, responseContainer = "List"),
    })
    @GetMapping("/cache")
    public String cacheStatistics(Model model){
        logger.info("[CityController] Displaying Cache statistics");
        Cache statistics = cacheService.getStatistics();
        model.addAttribute("statistics", statistics);
        return "cachestats";
    }

    @ApiOperation(value = "[Developer Oriented] Get the Cache's Statistics")
    @GetMapping("/api/cache")
    public ResponseEntity<Cache> getCacheStatistics(){
        logger.info("[CityController] Fetching Cache statistics to API");
        Cache cacheStatistics = cacheService.getStatistics();
        return new ResponseEntity<>(cacheStatistics, HttpStatus.OK);
    }
}
