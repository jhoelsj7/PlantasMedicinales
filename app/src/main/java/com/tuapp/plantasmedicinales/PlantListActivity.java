package com.tuapp.plantasmedicinales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tuapp.plantasmedicinales.controller.PlantController;
import java.util.List;

public class PlantListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private PlantAdapter adapter;
    private ProgressBar progressBar;
    private PlantController plantController;
    private android.widget.ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                                intent.putExtra("plant_name", plant.getCommon_name());
                                intent.putExtra("plant_scientific", plant.getScientific_name());
                                intent.putExtra("plant_family", plant.getFamily());
                                intent.putExtra("plant_description", plant.getDescription());
                                intent.putExtra("plant_uses", plant.getMedicinal_uses());
                                intent.putExtra("plant_image_url", plant.getImageUrl());
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

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}