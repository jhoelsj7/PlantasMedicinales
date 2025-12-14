package com.tuapp.plantasmedicinales.utils;

import android.content.Context;
import android.util.Log;
import com.tuapp.plantasmedicinales.Plant;
import com.tuapp.plantasmedicinales.database.AppDatabase;
import com.tuapp.plantasmedicinales.database.PlantDao;
import java.util.List;

/**
 * Clase para inicializar la base de datos local
 * Las plantas ahora se cargan desde el servidor, no hay datos por defecto
 */
public class DatabaseInitializer {
    private static final String TAG = "DatabaseInitializer";

    /**
     * Verifica el estado de la base de datos
     * Ya no inserta plantas por defecto - se sincronizan desde el servidor
     */
    public static void initializeWithDefaultPlants(Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PlantDao plantDao = AppDatabase.getInstance(context).plantDao();
                    List<Plant> existingPlants = plantDao.getAllPlantsSync();

                    if (existingPlants != null && !existingPlants.isEmpty()) {
                        Log.d(TAG, "Base de datos contiene " + existingPlants.size() + " plantas");
                    } else {
                        Log.d(TAG, "Base de datos vacía. Las plantas se cargarán desde el servidor.");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error al verificar base de datos: " + e.getMessage());
                }
            }
        }).start();
    }

    /**
     * Limpia todas las plantas de la base de datos local
     */
    public static void clearDatabase(Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PlantDao plantDao = AppDatabase.getInstance(context).plantDao();
                    plantDao.deleteAllPlants();
                    Log.d(TAG, "Base de datos limpiada");
                } catch (Exception e) {
                    Log.e(TAG, "Error al limpiar base de datos: " + e.getMessage());
                }
            }
        }).start();
    }
}
