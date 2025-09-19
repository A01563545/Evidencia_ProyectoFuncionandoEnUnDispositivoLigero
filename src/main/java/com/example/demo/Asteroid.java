package com.example.demo;

public class Asteroid {
    private String name;
    private double estimatedDiameterMeters;
    private boolean hazardous;
    private String nasaUrl;
    private String closeApproachDate;

    public Asteroid(String name, double estimatedDiameterMeters, boolean hazardous, String nasaUrl, String closeApproachDate) {
        this.name = name;
        this.estimatedDiameterMeters = estimatedDiameterMeters;
        this.hazardous = hazardous;
        this.nasaUrl = nasaUrl;
        this.closeApproachDate = closeApproachDate;
    }

    // Getters (para que Spring los serialice a JSON)
    public String getName() {
        return name;
    }

    public double getEstimatedDiameterMeters() {
        return estimatedDiameterMeters;
    }

    public boolean isHazardous() {
        return hazardous;
    }

    public String getNasaUrl() {
        return nasaUrl;
    }

    public String getCloseApproachDate() {
        return closeApproachDate;
    }
}