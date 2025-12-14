package com.tuapp.plantasmedicinales.controller;

import android.content.Context;
import com.tuapp.plantasmedicinales.Plant;
import com.tuapp.plantasmedicinales.service.PlantService;
import java.util.List;

public class PlantController {
    private PlantService plantService;

    public PlantController(Context context) {
        plantService = new PlantService(context);
    }

    public void getAllPlants(final PlantCallback callback) {
        plantService.syncPlants(new PlantService.SyncCallback() {
            @Override
            public void onSuccess(List<Plant> plants) {
                callback.onPlantsLoaded(plants);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public void searchPlants(String query, final PlantCallback callback) {
        plantService.searchPlants(query, new PlantService.SyncCallback() {
            @Override
            public void onSuccess(List<Plant> plants) {
                callback.onPlantsLoaded(plants);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public interface PlantCallback {
        void onPlantsLoaded(List<Plant> plants);
        void onError(String error);
    }
}