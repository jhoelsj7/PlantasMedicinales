package com.tuapp.plantasmedicinales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Respuesta del endpoint GET /api/predictions.php
 */
public class PredictionsApiResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<PredictionItem> data;

    @SerializedName("count")
    private int count;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<PredictionItem> getData() {
        return data;
    }

    public void setData(List<PredictionItem> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Item individual de predicción
     */
    public static class PredictionItem {
        @SerializedName("id")
        private int id;

        @SerializedName("image_path")
        private String imagePath;

        @SerializedName("confidence")
        private String confidence;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("username")
        private String username;

        @SerializedName("user_name")
        private String userName;

        @SerializedName("plant_name")
        private String plantName;

        @SerializedName("scientific_name")
        private String scientificName;

        @SerializedName("family")
        private String family;

        // Getters
        public int getId() { return id; }
        public String getImagePath() { return imagePath; }
        public String getConfidence() { return confidence; }
        public String getCreatedAt() { return createdAt; }
        public String getUsername() { return username; }
        public String getUserName() { return userName; }
        public String getPlantName() { return plantName; }
        public String getScientificName() { return scientificName; }
        public String getFamily() { return family; }

        // Setters
        public void setId(int id) { this.id = id; }
        public void setImagePath(String imagePath) { this.imagePath = imagePath; }
        public void setConfidence(String confidence) { this.confidence = confidence; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
        public void setUsername(String username) { this.username = username; }
        public void setUserName(String userName) { this.userName = userName; }
        public void setPlantName(String plantName) { this.plantName = plantName; }
        public void setScientificName(String scientificName) { this.scientificName = scientificName; }
        public void setFamily(String family) { this.family = family; }
    }
}
