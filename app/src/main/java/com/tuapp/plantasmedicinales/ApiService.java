package com.tuapp.plantasmedicinales;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("plants.php")
    Call<List<Plant>> getAllPlants();
}