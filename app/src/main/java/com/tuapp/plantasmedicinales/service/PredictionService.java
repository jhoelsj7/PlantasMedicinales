package com.tuapp.plantasmedicinales.service;

import android.content.Context;
import android.util.Log;

import com.tuapp.plantasmedicinales.ApiService;
import com.tuapp.plantasmedicinales.Plant;
import com.tuapp.plantasmedicinales.RetrofitClient;
import com.tuapp.plantasmedicinales.database.AppDatabase;
import com.tuapp.plantasmedicinales.database.PlantDao;
import com.tuapp.plantasmedicinales.model.PredictionRequest;
import com.tuapp.plantasmedicinales.model.PredictionResponse;
import com.tuapp.plantasmedicinales.utils.NetworkUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Servicio para gestionar el envío de predicciones a la API REST
 */
public class PredictionService {

    private static final String TAG = "PredictionService";

    private Context context;
    private ApiService apiService;
    private PlantDao plantDao;
    private AuthService authService;

    /**
     * Mapeo de nombres alternativos del modelo IA a nombres en la BD
     * Útil para normalizar nombres que pueden variar
     */
    private static final Map<String, String> NAME_ALIASES = new HashMap<>();

    static {
        // Mapeo de nombres alternativos a nombres canónicos en la BD
        // Formato: "nombre_en_labels.txt" -> "nombre_en_BD"
        NAME_ALIASES.put("aloevera", "Aloe Vera");
        NAME_ALIASES.put("aloe vera", "Aloe Vera");
        NAME_ALIASES.put("tulasi", "Tulsi");
        NAME_ALIASES.put("tulsi", "Tulsi");
        NAME_ALIASES.put("drumstick", "Moringa");
        NAME_ALIASES.put("mint", "Menta");
        NAME_ALIASES.put("ginger", "Jengibre");
        NAME_ALIASES.put("eucalyptus", "Eucalipto");
        NAME_ALIASES.put("lemon", "Limón");
        NAME_ALIASES.put("lemon_grass", "Hierba Limón");
        NAME_ALIASES.put("lemongrass", "Hierba Limón");
        NAME_ALIASES.put("papaya", "Papaya");
        NAME_ALIASES.put("pappaya", "Papaya");
        NAME_ALIASES.put("guava", "Guayaba");
        NAME_ALIASES.put("gauva", "Guayaba");
        NAME_ALIASES.put("pomegranate", "Granada");
        NAME_ALIASES.put("pomoegranate", "Granada");
        NAME_ALIASES.put("curry_leaf", "Hoja de Curry");
        NAME_ALIASES.put("curry", "Hoja de Curry");
        NAME_ALIASES.put("coriender", "Cilantro");
        NAME_ALIASES.put("coriander", "Cilantro");
        NAME_ALIASES.put("pepper", "Pimienta Negra");
        NAME_ALIASES.put("chilly", "Chile");
        NAME_ALIASES.put("onion", "Cebolla");
        NAME_ALIASES.put("tomato", "Tomate");
        NAME_ALIASES.put("mango", "Mango");
        NAME_ALIASES.put("coffee", "Café");
        NAME_ALIASES.put("bamboo", "Bambú");
        NAME_ALIASES.put("rose", "Rosa");
        NAME_ALIASES.put("jasmine", "Jazmín");
        NAME_ALIASES.put("hibiscus", "Hibisco");
        NAME_ALIASES.put("marigold", "Caléndula");
        NAME_ALIASES.put("neem", "Neem");
        NAME_ALIASES.put("henna", "Henna");
        NAME_ALIASES.put("spinach1", "Espinaca");
        NAME_ALIASES.put("palak(spinach)", "Espinaca");
        NAME_ALIASES.put("tamarind", "Tamarindo");
        NAME_ALIASES.put("avacado", "Aguacate");
        NAME_ALIASES.put("jackfruit", "Yaca");
        NAME_ALIASES.put("pumpkin", "Calabaza");
        NAME_ALIASES.put("beans", "Frijol");
        NAME_ALIASES.put("taro", "Taro");
        NAME_ALIASES.put("camphor", "Alcanfor");
        NAME_ALIASES.put("castor", "Ricino");
        NAME_ALIASES.put("betel", "Betel");
        NAME_ALIASES.put("common rue(naagdalli)", "Ruda");
        NAME_ALIASES.put("nagadali", "Ruda");
        NAME_ALIASES.put("wood_sorel", "Acedera");
        NAME_ALIASES.put("ashoka", "Ashoka");
        NAME_ALIASES.put("geranium", "Geranio");
    }

    public PredictionService(Context context) {
        this.context = context;
        this.apiService = RetrofitClient.getClient().create(ApiService.class);
        this.plantDao = AppDatabase.getInstance(context).plantDao();
        this.authService = new AuthService(context);
    }

    /**
     * Callback para el resultado del envío de predicción
     */
    public interface PredictionCallback {
        void onSuccess(PredictionResponse response);
        void onError(String errorMessage);
    }

    /**
     * Envía una predicción a la API REST
     *
     * @param plantName  Nombre de la planta identificada por el modelo (de labels.txt)
     * @param confidence Confianza del modelo (0-1, se convierte a 0-100)
     * @param imagePath  Ruta de la imagen (opcional)
     * @param callback   Callback para el resultado
     */
    public void savePrediction(String plantName, float confidence, String imagePath, PredictionCallback callback) {
        // Verificar conectividad
        if (!NetworkUtils.isNetworkAvailable(context)) {
            Log.w(TAG, "Sin conexión a internet, predicción no enviada");
            if (callback != null) {
                callback.onError("Sin conexión a internet");
            }
            return;
        }

        // Buscar el plant_id
        new Thread(() -> {
            Integer plantId = findPlantIdByName(plantName);

            // Obtener user_id del usuario logueado
            Integer userId = authService.getUserId();

            // Convertir confianza de 0-1 a 0-100
            float confidencePercent = confidence <= 1 ? confidence * 100 : confidence;

            // Extraer solo el nombre del archivo de la ruta
            String imageFileName = extractFileName(imagePath);

            // Crear request
            PredictionRequest request = new PredictionRequest(
                    userId,
                    plantId != null ? plantId : 0,
                    imageFileName,
                    confidencePercent
            );

            Log.d(TAG, "Enviando predicción: " + request.toString());
            Log.d(TAG, "PlantName: " + plantName + " -> PlantId: " + plantId);

            // Enviar a la API
            sendPredictionToApi(request, callback);

        }).start();
    }

    /**
     * Busca el ID de la planta por su nombre
     * 1. Normaliza el nombre usando aliases
     * 2. Busca en la BD local por nombre común, científico o parcial
     */
    private Integer findPlantIdByName(String plantName) {
        if (plantName == null || plantName.isEmpty()) {
            return null;
        }

        String normalizedName = plantName.toLowerCase().trim();

        // Reemplazar guiones bajos por espacios para mejor búsqueda
        String searchName = normalizedName.replace("_", " ");

        // 1. Verificar si hay un alias para este nombre
        String aliasName = NAME_ALIASES.get(normalizedName);
        if (aliasName != null) {
            searchName = aliasName;
            Log.d(TAG, "Usando alias: " + normalizedName + " -> " + aliasName);
        }

        // 2. Buscar en la base de datos local
        try {
            // Búsqueda exacta por nombre común
            List<Plant> plants = plantDao.searchPlants(searchName);
            if (plants != null && !plants.isEmpty()) {
                Log.d(TAG, "Plant ID encontrado (exacto): " + searchName + " -> " + plants.get(0).getId());
                return plants.get(0).getId();
            }

            // Búsqueda exacta con nombre original
            if (aliasName != null) {
                plants = plantDao.searchPlants(plantName);
                if (plants != null && !plants.isEmpty()) {
                    Log.d(TAG, "Plant ID encontrado (original): " + plantName + " -> " + plants.get(0).getId());
                    return plants.get(0).getId();
                }
            }

            // Búsqueda parcial
            plants = plantDao.searchPlants("%" + searchName + "%");
            if (plants != null && !plants.isEmpty()) {
                Log.d(TAG, "Plant ID encontrado (parcial): " + searchName + " -> " + plants.get(0).getId());
                return plants.get(0).getId();
            }

            // Búsqueda en todas las plantas comparando cada una
            List<Plant> allPlants = plantDao.getAllPlantsSync();
            String finalSearchName = searchName.toLowerCase();
            String originalName = normalizedName;

            for (Plant p : allPlants) {
                String commonName = p.getCommon_name();
                String scientificName = p.getScientific_name();

                // Comparar con nombre común
                if (commonName != null) {
                    String commonLower = commonName.toLowerCase();
                    if (commonLower.contains(finalSearchName) ||
                        finalSearchName.contains(commonLower) ||
                        commonLower.contains(originalName) ||
                        originalName.contains(commonLower)) {
                        Log.d(TAG, "Plant ID encontrado por nombre común: " + p.getId());
                        return p.getId();
                    }
                }

                // Comparar con nombre científico
                if (scientificName != null) {
                    String scientificLower = scientificName.toLowerCase();
                    if (scientificLower.contains(finalSearchName) ||
                        finalSearchName.contains(scientificLower) ||
                        scientificLower.contains(originalName)) {
                        Log.d(TAG, "Plant ID encontrado por nombre científico: " + p.getId());
                        return p.getId();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error buscando planta en BD: " + e.getMessage());
        }

        Log.w(TAG, "No se encontró plant_id para: " + plantName + " (búsqueda: " + searchName + ")");
        return null;
    }

    /**
     * Extrae el nombre del archivo de una ruta completa
     */
    private String extractFileName(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }
        int lastSeparator = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        if (lastSeparator >= 0 && lastSeparator < path.length() - 1) {
            return path.substring(lastSeparator + 1);
        }
        return path;
    }

    /**
     * Envía la predicción a la API
     */
    private void sendPredictionToApi(PredictionRequest request, PredictionCallback callback) {
        apiService.savePrediction(request).enqueue(new Callback<PredictionResponse>() {
            @Override
            public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PredictionResponse predictionResponse = response.body();

                    if (predictionResponse.isSuccess()) {
                        Log.d(TAG, "Predicción guardada: " + predictionResponse.getMessage());
                        if (callback != null) {
                            callback.onSuccess(predictionResponse);
                        }
                    } else {
                        String errorMsg = predictionResponse.getMessage() != null ?
                                predictionResponse.getMessage() : "Error desconocido";
                        Log.e(TAG, "Error de API: " + errorMsg);
                        if (callback != null) {
                            callback.onError(errorMsg);
                        }
                    }
                } else {
                    String errorMsg = "Error del servidor: " + response.code();
                    Log.e(TAG, errorMsg);

                    if (callback != null) {
                        callback.onError(errorMsg);
                    }
                }
            }

            @Override
            public void onFailure(Call<PredictionResponse> call, Throwable t) {
                String errorMsg = "Error de conexión: " + t.getMessage();
                Log.e(TAG, errorMsg, t);

                if (callback != null) {
                    callback.onError(errorMsg);
                }
            }
        });
    }

    /**
     * Envía una predicción de forma síncrona (para uso en hilos secundarios)
     *
     * @return true si se guardó correctamente, false en caso de error
     */
    public boolean savePredictionSync(String plantName, float confidence, String imagePath) {
        // Verificar conectividad
        if (!NetworkUtils.isNetworkAvailable(context)) {
            Log.w(TAG, "Sin conexión a internet");
            return false;
        }

        Integer plantId = findPlantIdByName(plantName);
        Integer userId = authService.getUserId();
        float confidencePercent = confidence <= 1 ? confidence * 100 : confidence;
        String imageFileName = extractFileName(imagePath);

        PredictionRequest request = new PredictionRequest(
                userId,
                plantId != null ? plantId : 0,
                imageFileName,
                confidencePercent
        );

        try {
            Response<PredictionResponse> response = apiService.savePrediction(request).execute();
            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                Log.d(TAG, "Predicción guardada exitosamente");
                return true;
            } else {
                Log.e(TAG, "Error al guardar predicción: " + response.code());
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error de conexión: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Agrega un alias personalizado de nombre de planta
     * Útil para agregar nuevos alias sin modificar el código
     */
    public static void addPlantAlias(String aliasName, String canonicalName) {
        NAME_ALIASES.put(aliasName.toLowerCase().trim(), canonicalName);
    }
}
