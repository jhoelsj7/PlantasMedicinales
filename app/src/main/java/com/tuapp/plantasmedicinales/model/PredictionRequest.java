package com.tuapp.plantasmedicinales.model;

import com.google.gson.annotations.SerializedName;

/**
 * Modelo para enviar predicciones a la API REST
 * POST /api/predictions.php
 */
public class PredictionRequest {

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("plant_id")
    private int plantId;

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("confidence")
    private float confidence;

    public PredictionRequest() {}

    public PredictionRequest(Integer userId, int plantId, String imagePath, float confidence) {
        this.userId = userId;
        this.plantId = plantId;
        this.imagePath = imagePath;
        this.confidence = confidence;
    }

    // Getters y Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "PredictionRequest{" +
                "userId=" + userId +
                ", plantId=" + plantId +
                ", imagePath='" + imagePath + '\'' +
                ", confidence=" + confidence +
                '}';
    }
}
