package com.tuapp.plantasmedicinales;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PlantDetailActivity extends AppCompatActivity {

    private ImageView plantImage;
    private TextView nameText;
    private TextView scientificText;
    private TextView familyText;
    private TextView descriptionText;
    private TextView usesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);

        // Habilitar botón de regreso
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Inicializar vistas
        plantImage = findViewById(R.id.plantDetailImage);
        nameText = findViewById(R.id.plantDetailName);
        scientificText = findViewById(R.id.plantDetailScientific);
        familyText = findViewById(R.id.plantDetailFamily);
        descriptionText = findViewById(R.id.plantDetailDescription);
        usesText = findViewById(R.id.plantDetailUses);

        // Obtener datos del intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("plant_name", "");
            String scientific = extras.getString("plant_scientific", "");
            String family = extras.getString("plant_family", "");
            String description = extras.getString("plant_description", "");
            String uses = extras.getString("plant_uses", "");

            // Establecer título
            setTitle(name);

            // Mostrar datos
            nameText.setText(name);
            scientificText.setText(scientific);
            familyText.setText("Familia: " + family);
            descriptionText.setText(description);
            usesText.setText(uses);

            // Por ahora usar imagen por defecto
            plantImage.setImageResource(R.mipmap.ic_launcher_round);
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