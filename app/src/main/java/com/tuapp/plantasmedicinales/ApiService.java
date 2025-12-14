package com.tuapp.plantasmedicinales;

import com.tuapp.plantasmedicinales.model.LoginRequest;
import com.tuapp.plantasmedicinales.model.LoginResponse;
import com.tuapp.plantasmedicinales.model.PlantsApiResponse;
import com.tuapp.plantasmedicinales.model.PlantsResponse;
import com.tuapp.plantasmedicinales.model.PredictionRequest;
import com.tuapp.plantasmedicinales.model.PredictionResponse;
import com.tuapp.plantasmedicinales.model.PredictionsApiResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Query;

public interface ApiService {

    // ============ AUTENTICACIÓN ============

    /**
     * Login de usuario
     * POST /api/login.php
     * Respuesta: { success, message, user: { id, username, email, full_name }, token }
     */
    @POST("login.php")
    Call<LoginResponse> login(@Body LoginRequest request);

    // ============ PLANTAS ============

    /**
     * Obtener todas las plantas
     * GET /api/plants.php
     * Respuesta: { success, data: [...plantas...] }
     */
    @GET("plants.php")
    Call<PlantsApiResponse> getAllPlantsWrapped();

    /**
     * Obtener todas las plantas (respuesta directa - retrocompatibilidad)
     * Usar solo si la API devuelve array directo
     */
    @GET("plants.php")
    Call<List<Plant>> getAllPlants();

    /**
     * Obtener planta por ID
     * GET /api/get_plant.php?id=5
     */
    @GET("get_plant.php")
    Call<Plant> getPlantById(@Query("id") int plantId);

    /**
     * Buscar plantas por nombre
     * GET /api/search_plants.php?query=manzanilla
     * Respuesta: { success, data: [...], total, page }
     */
    @GET("search_plants.php")
    Call<PlantsApiResponse> searchPlantsWrapped(@Query("query") String query);

    // ============ PREDICCIONES ============

    /**
     * Guardar una nueva predicción del modelo IA
     * POST /api/predictions.php
     * Body: { user_id, plant_id, image_path, confidence }
     * Respuesta: { success, message }
     */
    @POST("predictions.php")
    Call<PredictionResponse> savePrediction(@Body PredictionRequest request);

    /**
     * Obtener historial de predicciones de un usuario
     * GET /api/predictions.php?user_id=1&limit=50
     * Respuesta: { success, data: [...predicciones...], count }
     */
    @GET("predictions.php")
    Call<PredictionsApiResponse> getUserPredictions(@Query("user_id") int userId);

    /**
     * Obtener historial de predicciones con límite
     * GET /api/predictions.php?user_id=1&limit=50&offset=0
     */
    @GET("predictions.php")
    Call<PredictionsApiResponse> getUserPredictions(
            @Query("user_id") int userId,
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}