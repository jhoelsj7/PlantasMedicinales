package com.tuapp.plantasmedicinales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tuapp.plantasmedicinales.controller.PlantController;
import java.util.List;

public class PlantListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlantAdapter adapter;
    private ProgressBar progressBar;
    private PlantController plantController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        plantController = new PlantController(this);

        loadPlants();
    }

    private void loadPlants() {
        progressBar.setVisibility(View.VISIBLE);

        plantController.getAllPlants(new PlantController.PlantCallback() {
            @Override
            public void onPlantsLoaded(final List<Plant> plants) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        adapter = new PlantAdapter(plants, new PlantAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Plant plant) {
                                Intent intent = new Intent(PlantListActivity.this, PlantDetailActivity.class);
                                intent.putExtra("plant_id", plant.getId());
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onError(final String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(PlantListActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}