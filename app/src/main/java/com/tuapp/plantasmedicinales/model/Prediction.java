package com.tuapp.plantasmedicinales.model;

public class Prediction {
    private String plantName;
    private float confidence;
    private String scientificName;

    public Prediction(String plantName, float confidence) {
        this.plantName = plantName;
        this.confidence = confidence;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }
}