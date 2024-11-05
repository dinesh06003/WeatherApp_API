package com.dinesh.WeatherApp.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Data
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url.geo}")
    private String geoApiUrl;

    @Value("${openweathermap.api.url.weather}")
    private String weatherApiUrl;

    private final RestTemplate restTemplate;


    public Map<String, Object> getWeather(String city, String unit) {
        String geoUrl = geoApiUrl + "?q=" + city + "&limit=1&appid=" + apiKey;
        List<Map<String, Object>> geoData = restTemplate.getForObject(geoUrl, List.class);
        if (geoData == null || geoData.isEmpty()) {
            throw new RuntimeException("City not found");
        }
        Map<String, Object> location = geoData.get(0);
        double lat = (double) location.get("lat");
        double lon = (double) location.get("lon");
        String weatherUrl = weatherApiUrl + "?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=" + unit;

        return restTemplate.getForObject(weatherUrl, Map.class);
    }


}
