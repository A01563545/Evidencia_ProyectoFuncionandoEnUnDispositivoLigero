package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

@RestController
@RequestMapping("/cityweather")
public class CityWeatherController {

    private final String WEATHER_API  = "https://api.openweathermap.org/data/2.5/weather";
    private final String GEO_API      = "http://api.openweathermap.org/geo/1.0/direct";
    private final String API_KEY      = "fc80b217838c359a09d0a2134750b340";

    @GetMapping()
    public String getByCity(@RequestParam String city) {
        RestTemplate restTemplate = new RestTemplate();

        String geoUrl = GEO_API + "?q=" + city + "&limit=1&appid=" + API_KEY;
        String geoResponse = restTemplate.getForObject(geoUrl, String.class);

        if (geoResponse == null || geoResponse.equals("[]")) {
            return "Error: Could not fetch coordinates for city: " + city;
        }

        JSONArray geoArray = new JSONArray(geoResponse);
        JSONObject geoObject = geoArray.getJSONObject(0);
        double latitude = geoObject.getDouble("lat");
        double longitude = geoObject.getDouble("lon");

        String weatherUrl = WEATHER_API + "?lat=" + latitude + "&lon=" + longitude 
            + "&units=metric" + "&appid=" + API_KEY;
        String weatherResponse = restTemplate.getForObject(weatherUrl, String.class);

        if (weatherResponse != null) {
            return weatherResponse;
        }
        return "Error: Unable to fetch weather data for " + city;
    }
}
