package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/nasa")
public class AsteroidController {

    private final String NASA_API = "https://api.nasa.gov/neo/rest/v1/feed";
    private final String API_KEY  = "uWLLsqN6wvM5osNWqb5boU5goqIyv4qK3oN7czLa"; 

    @GetMapping("/asteroids")
    public List<Asteroid> getAsteroids(
            @RequestParam String start_date,
            @RequestParam String end_date) {

        RestTemplate restTemplate = new RestTemplate();

        // 1) Construir URL
        String url = NASA_API + "?start_date=" + start_date + "&end_date=" + end_date + "&api_key=" + API_KEY;

        // 2) Obtener respuesta JSON
        String response = restTemplate.getForObject(url, String.class);

        // 3) Parsear JSON con org.json
        JSONObject jsonResponse = new JSONObject(response);
        JSONObject nearEarthObjects = jsonResponse.getJSONObject("near_earth_objects");

        List<Asteroid> result = new ArrayList<>();

        // El JSON trae un objeto con claves = fechas (ej: "2025-09-18")
        for (String date : nearEarthObjects.keySet()) {
            JSONArray asteroidsArray = nearEarthObjects.getJSONArray(date);

            for (int i = 0; i < asteroidsArray.length(); i++) {
                JSONObject asteroid = asteroidsArray.getJSONObject(i);

                String name = asteroid.getString("name");
                String nasaJplUrl = asteroid.getString("nasa_jpl_url");
                double estimatedDiameter = asteroid
                        .getJSONObject("estimated_diameter")
                        .getJSONObject("meters")
                        .getDouble("estimated_diameter_max");
                boolean isHazardous = asteroid.getBoolean("is_potentially_hazardous_asteroid");

                result.add(new Asteroid(name, estimatedDiameter, isHazardous, nasaJplUrl, date));
            }
        }

        return result;
    }
}
