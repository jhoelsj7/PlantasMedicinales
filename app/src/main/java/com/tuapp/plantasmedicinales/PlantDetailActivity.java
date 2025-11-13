package com.tuapp.plantasmedicinales;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.tuapp.plantasmedicinales.utils.ImageUtils;

public class PlantDetailActivity extends BaseActivity {

    private ImageView plantImage;
    private TextView nameText;
    private TextView scientificText;
    private TextView familyText;
    private TextView descriptionText;
    private TextView usesText;
    private TextView preparationText;
    private TextView precautionsText;
    private android.widget.ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);

        // Inicializar vistas
        plantImage = findViewById(R.id.plantDetailImage);
        nameText = findViewById(R.id.plantDetailName);
        scientificText = findViewById(R.id.plantDetailScientific);
        familyText = findViewById(R.id.plantDetailFamily);
        descriptionText = findViewById(R.id.plantDetailDescription);
        usesText = findViewById(R.id.plantDetailUses);
        preparationText = findViewById(R.id.plantDetailPreparation);
        precautionsText = findViewById(R.id.plantDetailPrecautions);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                finish();
            }
        });

        // Obtener datos del intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("plant_name", "Desconocido");
            String scientific = extras.getString("plant_scientific", "No disponible");
            String family = extras.getString("plant_family", "No disponible");
            String description = extras.getString("plant_description", "No hay descripción disponible");
            String uses = extras.getString("plant_uses", "No hay información de usos medicinales");
            String preparation = extras.getString("plant_preparation", "No hay información de preparación");
            String precautions = extras.getString("plant_precautions", "Consulte con un profesional de la salud");
            String imageUrl = extras.getString("plant_image_url", "");

            // Establecer título
            setTitle(name);

            // Mostrar datos
            nameText.setText(name);
            scientificText.setText(scientific);
            familyText.setText("Familia: " + family);
            descriptionText.setText(description);
            usesText.setText(uses);
            preparationText.setText(preparation);
            precautionsText.setText(precautions);

            // Cargar imagen con Glide si existe URL
            if (imageUrl != null && !imageUrl.isEmpty()) {
                ImageUtils.loadPlantImage(this, imageUrl, plantImage);
            } else {
                plantImage.setImageResource(R.mipmap.ic_launcher_round);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}