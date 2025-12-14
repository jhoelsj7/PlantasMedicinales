package com.tuapp.plantasmedicinales.service;

import android.content.Context;
import com.tuapp.plantasmedicinales.ApiService;
import com.tuapp.plantasmedicinales.Plant;
import com.tuapp.plantasmedicinales.RetrofitClient;
import com.tuapp.plantasmedicinales.database.AppDatabase;
import com.tuapp.plantasmedicinales.database.PlantDao;
import com.tuapp.plantasmedicinales.model.PlantsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import java.util.concurrent.Executors;

public class PlantService {
    private ApiService apiService;
    private PlantDao plantDao;
    private Context context;

    public PlantService(Context context) {
        this.context = context;
        this.apiService = RetrofitClient.getClient().create(ApiService.class);
        this.plantDao = AppDatabase.getInstance(context).plantDao();
    }

    public void syncPlants(final SyncCallback callback) {
        Call<List<Plant>> call = apiService.getAllPlants();
        call.enqueue(new Callback<List<Plant>>() {
            @Override
            public void onResponse(Call<List<Plant>> call, Response<List<Plant>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    // El dashboard devuelve array directo, no wrapper
                    savePlantsToDatabase(response.body(), callback);
                } else {
                    loadPlantsFromDatabase(callback);
                }
            }

            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t) {
                loadPlantsFromDatabase(callback);
            }
        });
    }

    private void savePlantsToDatabase(final List<Plant> plants, final SyncCallback callback) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                plantDao.deleteAllPlants();
                for (Plant plant : plants) {
                    plant.setSynced(true);
                }
                plantDao.insertPlants(plants);
                if (callback != null) {
                    callback.onSuccess(plants);
                }
            }
        });
    }

    private void loadPlantsFromDatabase(final SyncCallback callback) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Plant> plants = plantDao.getAllPlants();
                if (callback != null) {
                    if (plants != null && !plants.isEmpty()) {
                        callback.onSuccess(plants);
                    } else {
                        callback.onError("No hay datos disponibles");
                    }
                }
            }
        });
    }

    public void searchPlants(final String query, final SyncCallback callback) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Plant> plants = plantDao.searchPlants("%" + query + "%");
                if (callback != null) {
                    callback.onSuccess(plants);
                }
            }
        });
    }

    public interface SyncCallback {
        void onSuccess(List<Plant> plants);
        void onError(String error);
    }
}