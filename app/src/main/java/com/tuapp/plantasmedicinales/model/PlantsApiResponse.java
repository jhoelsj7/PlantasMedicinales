package com.tuapp.plantasmedicinales.model;

import com.google.gson.annotations.SerializedName;
import com.tuapp.plantasmedicinales.Plant;

import java.util.List;

/**
 * Respuesta del endpoint GET /api/plants.php
 * La API devuelve un objeto con success y data (array de plantas)
 */
public class PlantsApiResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<Plant> data;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Plant> getData() {
        return data;
    }

    public void setData(List<Plant> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
