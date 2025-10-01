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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements PlantAdapter.OnPlantClickListener {

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private PlantAdapter adapter;
    private ProgressBar progressBar;
    private TextView emptyText;

    private List<Plant> allPlants = new ArrayList<>();
    private List<Plant> filteredPlants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle("Buscar Planta");

        // Inicializar vistas
        searchEditText = findViewById(R.id.searchEditText);
        recyclerView = findViewById(R.id.recyclerViewSearch);
        progressBar = findViewById(R.id.progressBarSearch);
        emptyText = findViewById(R.id.emptyTextView);

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

        // Cargar datos de prueba directamente
        loadDummyData();
        progressBar.setVisibility(View.GONE);
    }

    private void loadDummyData() {
        allPlants.clear();

        // Crear plantas de ejemplo
        Plant p1 = new Plant();
        p1.setId(1);
        p1.setCommon_name("Manzanilla");
        p1.setScientific_name("Matricaria chamomilla");
        p1.setFamily("Asteraceae");
        p1.setDescription("Hierba aromática con flores blancas y centro amarillo");
        p1.setMedicinal_uses("Antiinflamatoria, calmante, digestiva, ayuda con el insomnio");

        Plant p2 = new Plant();
        p2.setId(2);
        p2.setCommon_name("Aloe Vera");
        p2.setScientific_name("Aloe barbadensis");
        p2.setFamily("Asphodelaceae");
        p2.setDescription("Planta suculenta con hojas carnosas llenas de gel");
        p2.setMedicinal_uses("Cicatrizante, hidratante, antiinflamatoria, quemaduras");

        Plant p3 = new Plant();
        p3.setId(3);
        p3.setCommon_name("Menta");
        p3.setScientific_name("Mentha piperita");
        p3.setFamily("Lamiaceae");
        p3.setDescription("Planta aromática con hojas verdes y aroma refrescante");
        p3.setMedicinal_uses("Digestiva, descongestionante, alivia náuseas");

        Plant p4 = new Plant();
        p4.setId(4);
        p4.setCommon_name("Muña");
        p4.setScientific_name("Minthostachys mollis");
        p4.setFamily("Lamiaceae");
        p4.setDescription("Arbusto aromático andino");
        p4.setMedicinal_uses("Digestiva, carminativa, alivia el mal de altura");

        Plant p5 = new Plant();
        p5.setId(5);
        p5.setCommon_name("Eucalipto");
        p5.setScientific_name("Eucalyptus globulus");
        p5.setFamily("Myrtaceae");
        p5.setDescription("Árbol con hojas aromáticas");
        p5.setMedicinal_uses("Descongestionante, antiséptico, problemas respiratorios");

        Plant p6 = new Plant();
        p6.setId(6);
        p6.setCommon_name("Romero");
        p6.setScientific_name("Rosmarinus officinalis");
        p6.setFamily("Lamiaceae");
        p6.setDescription("Arbusto aromático con hojas en forma de aguja");
        p6.setMedicinal_uses("Mejora la memoria, antioxidante, estimulante circulatorio");

        Plant p7 = new Plant();
        p7.setId(7);
        p7.setCommon_name("Jengibre");
        p7.setScientific_name("Zingiber officinale");
        p7.setFamily("Zingiberaceae");
        p7.setDescription("Raíz aromática con sabor picante");
        p7.setMedicinal_uses("Antiinflamatorio, alivia náuseas, mejora digestión");

        Plant p8 = new Plant();
        p8.setId(8);
        p8.setCommon_name("Lavanda");
        p8.setScientific_name("Lavandula angustifolia");
        p8.setFamily("Lamiaceae");
        p8.setDescription("Arbusto con flores púrpuras aromáticas");
        p8.setMedicinal_uses("Relajante, mejora el sueño, alivia ansiedad");

        allPlants.add(p1);
        allPlants.add(p2);
        allPlants.add(p3);
        allPlants.add(p4);
        allPlants.add(p5);
        allPlants.add(p6);
        allPlants.add(p7);
        allPlants.add(p8);

        emptyText.setText("Escribe para buscar plantas");
        emptyText.setVisibility(View.VISIBLE);
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
    public void onPlantClick(Plant plant) {
        Intent intent = new Intent(this, PlantDetailActivity.class);
        intent.putExtra("plant_id", plant.getId());
        intent.putExtra("plant_name", plant.getCommon_name());
        intent.putExtra("plant_scientific", plant.getScientific_name());
        intent.putExtra("plant_family", plant.getFamily());
        intent.putExtra("plant_description", plant.getDescription());
        intent.putExtra("plant_uses", plant.getMedicinal_uses());
        startActivity(intent);
    }
}