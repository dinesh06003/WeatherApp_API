package com.dinesh.WeatherApp.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

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

    public ResponseEntity<Map<String, Object>> getWeather(String city, String unit) {
        // Build the geo API URL
        String geoUrl = geoApiUrl + "?q=" + city + "&limit=1&appid=" + apiKey;

        // Call geo API to get location data
        List<Map<String, Object>> geoData = restTemplate.getForObject(geoUrl, List.class);

        if (geoData == null || geoData.isEmpty()) {
            // Return 404 Not Found if geo data is empty
            return new ResponseEntity<>(Map.of("message", "City not found"), HttpStatus.NOT_FOUND);
        } else {
            // Extract latitude, longitude, country, and state from geo data
            Map<String, Object> location = geoData.get(0);
            double lat = (double) location.get("lat");
            double lon = (double) location.get("lon");
            String country = (String) location.get("country");
            String state = (String) location.get("state");

            // Build the weather API URL with latitude, longitude, and unit
            String weatherUrl = weatherApiUrl + "?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=" + unit;

            Map<String, Object> weatherData = restTemplate.getForObject(weatherUrl, Map.class);

            // Adding country and state to the response
            weatherData.put("country", country);
            if (state != null) {
                weatherData.put("state", state);
            }

            return new ResponseEntity<>(weatherData, HttpStatus.OK);
        }
    }
}
