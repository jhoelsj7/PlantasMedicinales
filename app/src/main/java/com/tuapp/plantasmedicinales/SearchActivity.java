package com.tuapp.plantasmedicinales;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tuapp.plantasmedicinales.database.AppDatabase;
import com.tuapp.plantasmedicinales.database.PlantDao;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity implements PlantAdapter.OnPlantClickListener {

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private PlantAdapter adapter;
    private ProgressBar progressBar;
    private TextView emptyText;
    private android.widget.ImageButton btnBack;

    private List<Plant> allPlants = new ArrayList<>();
    private List<Plant> filteredPlants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Inicializar vistas
        searchEditText = findViewById(R.id.searchEditText);
        recyclerView = findViewById(R.id.recyclerViewSearch);
        progressBar = findViewById(R.id.progressBarSearch);
        emptyText = findViewById(R.id.emptyTextView);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlantAdapter(filteredPlants, this);
        recyclerView.setAdapter(adapter);

        // Configurar búsqueda en tiempo real
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPlants(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Cargar todas las plantas
        loadAllPlants();
    }

    private void loadAllPlants() {
        progressBar.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.GONE);

        // Cargar plantas desde la base de datos local
        new Thread(new Runnable() {
            @Override
            public void run() {
                PlantDao plantDao = AppDatabase.getInstance(SearchActivity.this).plantDao();
                final List<Plant> plants = plantDao.getAllPlants();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        if (plants != null && !plants.isEmpty()) {
                            allPlants.clear();
                            allPlants.addAll(plants);
                            filteredPlants.clear();
                            filteredPlants.addAll(plants);
                            adapter.updateList(filteredPlants);
                        } else {
                            emptyText.setVisibility(View.VISIBLE);
                            emptyText.setText("No hay plantas. Sincroniza desde la lista de plantas.");
                        }
                    }
                });
            }
        }).start();
    }


    private void filterPlants(String query) {
        filteredPlants.clear();

        if (query.isEmpty()) {
            emptyText.setText("Escribe para buscar plantas");
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            for (Plant plant : allPlants) {
                // Buscar en nombre común, científico y usos medicinales
                if (plant.getCommon_name().toLowerCase().contains(query.toLowerCase()) ||
                        plant.getScientific_name().toLowerCase().contains(query.toLowerCase()) ||
                        (plant.getMedicinal_uses() != null &&
                                plant.getMedicinal_uses().toLowerCase().contains(query.toLowerCase()))) {
                    filteredPlants.add(plant);
                }
            }

            if (filteredPlants.isEmpty()) {
                emptyText.setText("No se encontraron plantas con: \"" + query + "\"");
                emptyText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPlantClick(Plant plant) {
        Intent intent = new Intent(this, PlantDetailActivity.class);
        intent.putExtra("plant_id", plant.getId());
        intent.putExtra("plant_name", plant.getCommon_name());
        intent.putExtra("plant_scientific", plant.getScientific_name());
        intent.putExtra("plant_family", plant.getFamily());
        intent.putExtra("plant_description", plant.getDescription());
        intent.putExtra("plant_uses", plant.getMedicinal_uses());
        intent.putExtra("plant_image_url", plant.getImageUrl());
        startActivity(intent);
    }
}