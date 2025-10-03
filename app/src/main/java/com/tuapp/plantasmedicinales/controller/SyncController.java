package com.tuapp.plantasmedicinales.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.tuapp.plantasmedicinales.service.PlantService;
import com.tuapp.plantasmedicinales.Plant;
import java.util.List;

public class SyncController {
    private Context context;
    private PlantService plantService;

    public SyncController(Context context) {
        this.context = context;
        this.plantService = new PlantService(context);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void synchronizeData(final SyncCallback callback) {
        if (isNetworkAvailable()) {
            plantService.syncPlants(new PlantService.SyncCallback() {
                @Override
                public void onSuccess(List<Plant> plants) {
                    callback.onSyncComplete(true, "Sincronización completa");
                }

                @Override
                public void onError(String error) {
                    callback.onSyncComplete(false, error);
                }
            });
        } else {
            callback.onSyncComplete(false, "No hay conexión a internet");
        }
    }

    public interface SyncCallback {
        void onSyncComplete(boolean success, String message);
    }
}