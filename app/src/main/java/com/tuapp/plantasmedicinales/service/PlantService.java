package com.tuapp.plantasmedicinales.service;

import android.content.Context;
import android.util.Log;
import com.tuapp.plantasmedicinales.ApiService;
import com.tuapp.plantasmedicinales.Plant;
import com.tuapp.plantasmedicinales.RetrofitClient;
import com.tuapp.plantasmedicinales.database.AppDatabase;
import com.tuapp.plantasmedicinales.database.PlantDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import java.util.concurrent.Executors;

public class PlantService {
    private static final String TAG = "PlantService";
    private ApiService apiService;
    private PlantDao plantDao;
    private Context context;

    public PlantService(Context context) {
        this.context = context;
        this.apiService = RetrofitClient.getClient().create(ApiService.class);
        this.plantDao = AppDatabase.getInstance(context).plantDao();
    }

    public void syncPlants(final SyncCallback callback) {
        // Usar el endpoint que devuelve array directo [...]
        Call<List<Plant>> call = apiService.getAllPlants();
        call.enqueue(new Callback<List<Plant>>() {
            @Override
            public void onResponse(Call<List<Plant>> call, Response<List<Plant>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Plant> plants = response.body();
                    if (plants != null && !plants.isEmpty()) {
                        Log.d(TAG, "Plantas recibidas del servidor: " + plants.size());
                        savePlantsToDatabase(plants, callback);
                    } else {
                        Log.w(TAG, "Respuesta vacía del servidor, cargando desde BD local");
                        loadPlantsFromDatabase(callback);
                    }
                } else {
                    Log.e(TAG, "Error en respuesta: " + response.code());
                    loadPlantsFromDatabase(callback);
                }
            }

            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t) {
                Log.e(TAG, "Error de conexión: " + t.getMessage());
                loadPlantsFromDatabase(callback);
            }
        });
    }

    private void savePlantsToDatabase(final List<Plant> plants, final SyncCallback callback) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Limpiar completamente la base de datos local
                    plantDao.deleteAllPlants();
                    Log.d(TAG, "Base de datos local limpiada");

                    // Insertar plantas del servidor
                    for (Plant plant : plants) {
                        plant.setSynced(true);
                    }
                    plantDao.insertPlants(plants);
                    Log.d(TAG, "Insertadas " + plants.size() + " plantas del servidor");

                    // Verificar inserción
                    List<Plant> verificacion = plantDao.getAllPlants();
                    Log.d(TAG, "Verificación: BD local tiene " + verificacion.size() + " plantas");

                    if (callback != null) {
                        callback.onSuccess(plants);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error al guardar plantas: " + e.getMessage());
                    if (callback != null) {
                        callback.onError("Error al guardar: " + e.getMessage());
                    }
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