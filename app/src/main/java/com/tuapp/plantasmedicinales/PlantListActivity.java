package com.tuapp.plantasmedicinales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantListActivity extends AppCompatActivity implements PlantAdapter.OnPlantClickListener {

    private RecyclerView recyclerView;
    private PlantAdapter adapter;
    private ProgressBar progressBar;
    private List<Plant> plantList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        setTitle("Catálogo de Plantas");

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewPlants);
        progressBar = findViewById(R.id.progressBar);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlantAdapter(plantList, this);
        recyclerView.setAdapter(adapter);

        // Cargar plantas
        loadPlants();
    }

    private void loadPlants() {
        progressBar.setVisibility(View.VISIBLE);

        // Primero intentar cargar desde API
        Call<List<Plant>> call = RetrofitClient.getInstance()
                .getApiService()
                .getAllPlants();

        call.enqueue(new Callback<List<Plant>>() {
            @Override
            public void onResponse(Call<List<Plant>> call, Response<List<Plant>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    plantList.clear();
                    plantList.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    if (plantList.isEmpty()) {
                        // Si no hay datos de API, cargar datos de prueba
                        loadDummyData();
                    }
                } else {
                    // Si falla la API, cargar datos de prueba
                    loadDummyData();
                }
            }

            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PlantListActivity.this,
                        "Sin conexión a API. Mostrando datos de ejemplo",
                        Toast.LENGTH_SHORT).show();
                loadDummyData();
            }
        });
    }

    private void loadDummyData() {
        plantList.clear();

        // Crear plantas de ejemplo
        Plant p1 = new Plant();
        p1.setId(1);
        p1.setCommon_name("Manzanilla");
        p1.setScientific_name("Matricaria chamomilla");
        p1.setFamily("Asteraceae");
        p1.setDescription("Hierba aromática con flores blancas y centro amarillo. Es una de las plantas medicinales más populares.");
        p1.setMedicinal_uses("Antiinflamatoria, calmante, digestiva, ayuda con el insomnio, reduce la ansiedad");

        Plant p2 = new Plant();
        p2.setId(2);
        p2.setCommon_name("Aloe Vera");
        p2.setScientific_name("Aloe barbadensis");
        p2.setFamily("Asphodelaceae");
        p2.setDescription("Planta suculenta con hojas carnosas llenas de gel transparente con propiedades curativas.");
        p2.setMedicinal_uses("Cicatrizante, hidratante, antiinflamatoria, trata quemaduras, mejora la digestión");

        Plant p3 = new Plant();
        p3.setId(3);
        p3.setCommon_name("Menta");
        p3.setScientific_name("Mentha piperita");
        p3.setFamily("Lamiaceae");
        p3.setDescription("Planta aromática con hojas verdes y aroma refrescante característico.");
        p3.setMedicinal_uses("Digestiva, descongestionante, alivia náuseas, reduce dolores de cabeza");

        Plant p4 = new Plant();
        p4.setId(4);
        p4.setCommon_name("Muña");
        p4.setScientific_name("Minthostachys mollis");
        p4.setFamily("Lamiaceae");
        p4.setDescription("Arbusto aromático andino muy utilizado en la medicina tradicional peruana.");
        p4.setMedicinal_uses("Digestiva, carminativa, alivia el mal de altura, problemas estomacales");

        Plant p5 = new Plant();
        p5.setId(5);
        p5.setCommon_name("Eucalipto");
        p5.setScientific_name("Eucalyptus globulus");
        p5.setFamily("Myrtaceae");
        p5.setDescription("Árbol alto con hojas aromáticas de color verde azulado.");
        p5.setMedicinal_uses("Descongestionante, antiséptico, problemas respiratorios, resfriados");

        Plant p6 = new Plant();
        p6.setId(6);
        p6.setCommon_name("Jengibre");
        p6.setScientific_name("Zingiber officinale");
        p6.setFamily("Zingiberaceae");
        p6.setDescription("Raíz aromática con sabor picante, ampliamente usada en medicina y cocina.");
        p6.setMedicinal_uses("Antiinflamatorio, alivia náuseas, mejora la digestión, fortalece el sistema inmune");

        Plant p7 = new Plant();
        p7.setId(7);
        p7.setCommon_name("Romero");
        p7.setScientific_name("Rosmarinus officinalis");
        p7.setFamily("Lamiaceae");
        p7.setDescription("Arbusto aromático con hojas en forma de aguja y flores azules o violetas.");
        p7.setMedicinal_uses("Mejora la memoria, antioxidante, estimulante circulatorio, antiinflamatorio");

        Plant p8 = new Plant();
        p8.setId(8);
        p8.setCommon_name("Lavanda");
        p8.setScientific_name("Lavandula angustifolia");
        p8.setFamily("Lamiaceae");
        p8.setDescription("Arbusto con flores púrpuras muy aromáticas, símbolo de relajación.");
        p8.setMedicinal_uses("Relajante, mejora el sueño, alivia ansiedad, antiséptica, calmante");

        Plant p9 = new Plant();
        p9.setId(9);
        p9.setCommon_name("Caléndula");
        p9.setScientific_name("Calendula officinalis");
        p9.setFamily("Asteraceae");
        p9.setDescription("Flor de color naranja brillante con múltiples propiedades medicinales.");
        p9.setMedicinal_uses("Cicatrizante, antiinflamatoria, antiséptica para la piel, regeneradora");

        Plant p10 = new Plant();
        p10.setId(10);
        p10.setCommon_name("Orégano");
        p10.setScientific_name("Origanum vulgare");
        p10.setFamily("Lamiaceae");
        p10.setDescription("Hierba aromática muy usada en cocina con propiedades medicinales.");
        p10.setMedicinal_uses("Antibacteriano, antioxidante, digestivo, alivia problemas respiratorios");

        plantList.add(p1);
        plantList.add(p2);
        plantList.add(p3);
        plantList.add(p4);
        plantList.add(p5);
        plantList.add(p6);
        plantList.add(p7);
        plantList.add(p8);
        plantList.add(p9);
        plantList.add(p10);

        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Mostrando " + plantList.size() + " plantas de ejemplo", Toast.LENGTH_SHORT).show();
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