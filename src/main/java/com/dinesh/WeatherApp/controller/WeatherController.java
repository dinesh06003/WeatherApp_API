package com.dinesh.WeatherApp.controller;

import com.dinesh.WeatherApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/hello")
    public String greeting(){
        return  "Hello";
    }

    @GetMapping("/api/weather")
    public Map<String, Object> getWeather(@RequestParam String city, @RequestParam(defaultValue = "imperial") String unit){
    return   weatherService.getWeather(city, unit);

    }
}
