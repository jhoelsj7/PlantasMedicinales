package com.tuapp.plantasmedicinales;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.tuapp.plantasmedicinales.database.DatabaseHelper;
import com.tuapp.plantasmedicinales.database.AppDatabase;
import com.tuapp.plantasmedicinales.database.PlantDao;
import com.tuapp.plantasmedicinales.service.AuthService;
import com.tuapp.plantasmedicinales.utils.ImageUtils;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends BaseActivity {

    private ImageView ivPlantImage;
    private TextView tvPlantName, tvConfidence, tvScientificName, tvRecommendation;
    private Button btnViewDetails, btnTryAgain;
    private android.widget.ImageButton btnBack;
    private DatabaseHelper dbHelper;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        dbHelper = new DatabaseHelper(this);
        authService = new AuthService(this);

        ivPlantImage = findViewById(R.id.ivPlantImage);
        tvPlantName = findViewById(R.id.tvPlantName);
        tvConfidence = findViewById(R.id.tvConfidence);
        tvScientificName = findViewById(R.id.tvScientificName);
        tvRecommendation = findViewById(R.id.tvRecommendation);
        btnViewDetails = findViewById(R.id.btnViewDetails);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final String plantName = getIntent().getStringExtra("plant_name");
        final float confidence = getIntent().getFloatExtra("confidence", 0);
        final Bitmap capturedImage = getIntent().getParcelableExtra("captured_image");

        // Mostrar imagen capturada si está disponible
        if (capturedImage != null) {
            ivPlantImage.setImageBitmap(capturedImage);
        } else {
            ivPlantImage.setImageResource(R.mipmap.ic_launcher_round);
        }

        tvPlantName.setText(plantName != null ? plantName : "Desconocido");
        tvConfidence.setText(String.format("Confianza: %.1f%%", confidence * 100));

        // Guardar en el historial con la imagen
        if (plantName != null && !plantName.isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String username = authService.getUsername();
                    String imagePath = null;

                    // Guardar la imagen capturada si existe
                    if (capturedImage != null) {
                        imagePath = ImageUtils.saveBitmapToInternalStorage(
                            ResultActivity.this, capturedImage, plantName);
                    }

                    dbHelper.addIdentification(plantName, confidence, imagePath, username);
                }
            }).start();
        }

        // Mostrar recomendación basada en confianza
        if (confidence > 0.8f) {
            tvRecommendation.setText("Alta confianza en la identificación");
            tvRecommendation.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else if (confidence > 0.5f) {
            tvRecommendation.setText("Confianza media. Verifica los detalles");
            tvRecommendation.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        } else {
            tvRecommendation.setText("Baja confianza. Recomendamos otra foto");
            tvRecommendation.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buscar la planta en la base de datos local para obtener todos los detalles
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PlantDao plantDao = AppDatabase.getInstance(ResultActivity.this).plantDao();

                        // Intentar búsqueda exacta primero
                        List<Plant> plants = plantDao.searchPlants(plantName);

                        // Si no encuentra, intentar con búsqueda parcial
                        if (plants == null || plants.isEmpty()) {
                            plants = plantDao.searchPlants("%" + plantName + "%");
                        }

                        // Si aún no encuentra, intentar por nombre científico
                        if (plants == null || plants.isEmpty()) {
                            List<Plant> allPlants = plantDao.getAllPlantsSync();
                            for (Plant p : allPlants) {
                                if (p.getCommon_name() != null &&
                                    p.getCommon_name().toLowerCase().trim().contains(plantName.toLowerCase().trim())) {
                                    plants = new ArrayList<>();
                                    plants.add(p);
                                    break;
                                }
                            }
                        }

                        final Plant plant = (plants != null && !plants.isEmpty()) ? plants.get(0) : null;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(ResultActivity.this, PlantDetailActivity.class);
                                if (plant != null) {
                                    intent.putExtra("plant_id", plant.getId());
                                    intent.putExtra("plant_name", plant.getCommon_name());
                                    intent.putExtra("plant_scientific", plant.getScientific_name());
                                    intent.putExtra("plant_family", plant.getFamily());
                                    intent.putExtra("plant_description", plant.getDescription());
                                    intent.putExtra("plant_uses", plant.getMedicinal_uses());
                                    intent.putExtra("plant_preparation", plant.getPreparation());
                                    intent.putExtra("plant_precautions", plant.getPrecautions());
                                    intent.putExtra("plant_image_url", plant.getImageUrl());
                                } else {
                                    // Si no encuentra la planta, pasar valores por defecto
                                    intent.putExtra("plant_name", plantName);
                                    intent.putExtra("plant_scientific", "No disponible");
                                    intent.putExtra("plant_family", "No disponible");
                                    intent.putExtra("plant_description", "No hay información disponible para esta planta.");
                                    intent.putExtra("plant_uses", "Información no disponible");
                                }
                                startActivity(intent);
                            }
                        });
                    }
                }).start();
            }
        });

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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