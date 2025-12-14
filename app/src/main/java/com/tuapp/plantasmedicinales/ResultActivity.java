package com.tuapp.plantasmedicinales;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.tuapp.plantasmedicinales.database.DatabaseHelper;
import com.tuapp.plantasmedicinales.database.AppDatabase;
import com.tuapp.plantasmedicinales.database.PlantDao;
import com.tuapp.plantasmedicinales.service.AuthService;
import com.tuapp.plantasmedicinales.service.PredictionService;
import com.tuapp.plantasmedicinales.model.PredictionResponse;
import com.tuapp.plantasmedicinales.utils.ImageUtils;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends BaseActivity {

    private static final String TAG = "ResultActivity";
    
    private ImageView ivPlantImage;
    private TextView tvPlantName, tvConfidence, tvScientificName, tvRecommendation;
    private Button btnViewDetails, btnTryAgain;
    private android.widget.ImageButton btnBack;
    private DatabaseHelper dbHelper;
    private AuthService authService;
    private PredictionService predictionService;

    private Bitmap loadedImage;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        dbHelper = new DatabaseHelper(this);
        authService = new AuthService(this);
        predictionService = new PredictionService(this);

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
        imagePath = getIntent().getStringExtra("image_path");

        // Cargar imagen desde la ruta del archivo
        if (imagePath != null && new File(imagePath).exists()) {
            Log.d(TAG, "Cargando imagen desde: " + imagePath);
            loadedImage = loadBitmapFromPath(imagePath);
            if (loadedImage != null) {
                ivPlantImage.setImageBitmap(loadedImage);
            } else {
                ivPlantImage.setImageResource(R.mipmap.ic_launcher_round);
            }
        } else {
            Log.d(TAG, "No hay imagen disponible");
            ivPlantImage.setImageResource(R.mipmap.ic_launcher_round);
        }

        tvPlantName.setText(plantName != null ? plantName : "Desconocido");
        tvConfidence.setText(String.format("Confianza: %.1f%%", confidence * 100));

        // Guardar en el historial con la imagen y enviar predicción a la API
        if (plantName != null && !plantName.isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String username = authService.getUsername();
                    String savedImagePath = null;

                    // Guardar la imagen capturada si existe
                    if (loadedImage != null) {
                        savedImagePath = ImageUtils.saveBitmapToInternalStorage(
                            ResultActivity.this, loadedImage, plantName);
                    }

                    // Guardar en historial local
                    dbHelper.addIdentification(plantName, confidence, savedImagePath, username);

                    // Enviar predicción a la API REST
                    final String finalImagePath = savedImagePath;
                    sendPredictionToServer(plantName, confidence, finalImagePath);
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
    
    /**
     * Carga un Bitmap desde una ruta de archivo con escalado para evitar OutOfMemory
     */
    private Bitmap loadBitmapFromPath(String path) {
        try {
            // Obtener dimensiones sin cargar la imagen
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            
            // Calcular factor de escala (máximo 800px)
            int maxSize = 800;
            int scaleFactor = 1;
            while ((options.outWidth / scaleFactor) > maxSize || 
                   (options.outHeight / scaleFactor) > maxSize) {
                scaleFactor *= 2;
            }
            
            // Cargar imagen escalada
            options.inJustDecodeBounds = false;
            options.inSampleSize = scaleFactor;
            
            return BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            Log.e(TAG, "Error al cargar imagen: " + e.getMessage());
            return null;
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

    /**
     * Envía la predicción del modelo IA a la API REST
     */
    private void sendPredictionToServer(String plantName, float confidence, String imagePath) {
        predictionService.savePrediction(plantName, confidence, imagePath,
                new PredictionService.PredictionCallback() {
                    @Override
                    public void onSuccess(PredictionResponse response) {
                        Log.d(TAG, "Predicción enviada al servidor: " + response.getMessage());
                        runOnUiThread(() -> {
                            Toast.makeText(ResultActivity.this,
                                    "Predicción sincronizada con el servidor",
                                    Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e(TAG, "Error al enviar predicción: " + errorMessage);
                        // No mostramos error al usuario para no interrumpir la experiencia
                        // La predicción se guardó localmente de todas formas
                    }
                });
    }
}
