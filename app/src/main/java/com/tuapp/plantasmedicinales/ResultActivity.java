package com.tuapp.plantasmedicinales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private ImageView ivPlantImage;
    private TextView tvPlantName, tvConfidence, tvScientificName;
    private Button btnViewDetails, btnTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ivPlantImage = findViewById(R.id.ivPlantImage);
        tvPlantName = findViewById(R.id.tvPlantName);
        tvConfidence = findViewById(R.id.tvConfidence);
        tvScientificName = findViewById(R.id.tvScientificName);
        btnViewDetails = findViewById(R.id.btnViewDetails);
        btnTryAgain = findViewById(R.id.btnTryAgain);

        String plantName = getIntent().getStringExtra("plant_name");
        float confidence = getIntent().getFloatExtra("confidence", 0);

        tvPlantName.setText(plantName);
        tvConfidence.setText(String.format("Confianza: %.1f%%", confidence * 100));

        btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, PlantDetailActivity.class);
                intent.putExtra("plant_name", plantName);
                startActivity(intent);
            }
        });

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}