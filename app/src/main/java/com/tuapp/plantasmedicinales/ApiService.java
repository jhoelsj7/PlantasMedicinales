package com.tuapp.plantasmedicinales;

import com.tuapp.plantasmedicinales.model.LoginRequest;
import com.tuapp.plantasmedicinales.model.LoginResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface ApiService {
    @GET("plants.php")
    Call<List<Plant>> getAllPlants();

    @POST("login.php")
    Call<LoginResponse> login(@Body LoginRequest request);
}