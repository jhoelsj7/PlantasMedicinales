package com.tuapp.plantasmedicinales.model;

import com.google.gson.annotations.SerializedName;

/**
 * Modelo para la respuesta de la API de predicciones
 * Respuesta de POST /api/predictions.php
 */
public class PredictionResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("prediction_id")
    private Integer predictionId;

    public PredictionResponse() {}

    public PredictionResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPredictionId() {
        return predictionId;
    }

    public void setPredictionId(Integer predictionId) {
        this.predictionId = predictionId;
    }

    @Override
    public String toString() {
        return "PredictionResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", predictionId=" + predictionId +
                '}';
    }
}
