package com.tuapp.plantasmedicinales.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import com.tuapp.plantasmedicinales.Plant;
import java.util.List;

@Dao
public interface PlantDao {
    @Query("SELECT * FROM plants")
    List<Plant> getAllPlants();

    @Query("SELECT * FROM plants WHERE id = :id")
    Plant getPlantById(int id);

    @Query("SELECT * FROM plants WHERE name LIKE :searchQuery OR scientificName LIKE :searchQuery")
    List<Plant> searchPlants(String searchQuery);

    @Insert
    void insertPlant(Plant plant);

    @Insert
    void insertPlants(List<Plant> plants);

    @Update
    void updatePlant(Plant plant);

    @Delete
    void deletePlant(Plant plant);

    @Query("DELETE FROM plants")
    void deleteAllPlants();

    @Query("SELECT * FROM plants WHERE isSynced = 0")
    List<Plant> getUnsyncedPlants();
}