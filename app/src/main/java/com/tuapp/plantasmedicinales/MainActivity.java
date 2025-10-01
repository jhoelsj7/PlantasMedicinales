package com.tuapp.plantasmedicinales;

import android.content.Intent;  // ← AGREGAR ESTE
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Crear interfaz programáticamente
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        TextView title = new TextView(this);
        title.setText("🌿 Sistema de Plantas Medicinales");
        title.setTextSize(24);
        title.setPadding(0, 0, 0, 32);
        layout.addView(title);

        Button button = new Button(this);
        button.setText("Cargar Plantas desde API");
        button.setOnClickListener(v -> fetchPlants());
        layout.addView(button);

        // ← AGREGAR AQUÍ EL NUEVO CÓDIGO ↓
        // Botón para identificar plantas con IA
        Button cameraButton = new Button(this);
        cameraButton.setText("📷 Identificar Planta con IA");
        cameraButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent);
        });
        layout.addView(cameraButton);
        // ← HASTA AQUÍ ↑

        ScrollView scrollView = new ScrollView(this);
        textViewResult = new TextView(this);
        textViewResult.setText("Presiona el botón para cargar las plantas");
        textViewResult.setPadding(16, 16, 16, 16);
        scrollView.addView(textViewResult);
        layout.addView(scrollView);

        setContentView(layout);
    }

    private void fetchPlants() {
        textViewResult.setText("Cargando plantas...");

        Call<List<Plant>> call = RetrofitClient.getInstance()
                .getApiService()
                .getAllPlants();

        call.enqueue(new Callback<List<Plant>>() {
            @Override
            public void onResponse(Call<List<Plant>> call, Response<List<Plant>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Plant> plants = response.body();
                    StringBuilder result = new StringBuilder();
                    result.append("✅ Plantas encontradas: ").append(plants.size()).append("\n\n");

                    for (Plant plant : plants) {
                        result.append("🌱 ").append(plant.getCommon_name())
                                .append("\n📚 ").append(plant.getScientific_name())
                                .append("\n💊 Usos: ").append(plant.getMedicinal_uses())
                                .append("\n\n");
                    }

                    textViewResult.setText(result.toString());
                    Toast.makeText(MainActivity.this,
                            "¡Conexión exitosa con la API!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    textViewResult.setText("❌ Error al obtener datos");
                }
            }

            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t) {
                textViewResult.setText("❌ Error de conexión: " + t.getMessage());
                Toast.makeText(MainActivity.this,
                        "Error: Verifica que XAMPP esté corriendo",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}