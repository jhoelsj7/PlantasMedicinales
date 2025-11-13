package com.tuapp.plantasmedicinales;

import com.tuapp.plantasmedicinales.model.LoginRequest;
import com.tuapp.plantasmedicinales.model.LoginResponse;
import com.tuapp.plantasmedicinales.model.PlantsResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface ApiService {
    // Usar List<Plant> directo porque el dashboard devuelve array directo
    @GET("plants.php")
    Call<List<Plant>> getAllPlants();

    @POST("login.php")
    Call<LoginResponse> login(@Body LoginRequest request);
}