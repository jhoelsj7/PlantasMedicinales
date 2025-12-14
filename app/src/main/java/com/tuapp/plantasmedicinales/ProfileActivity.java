package com.tuapp.plantasmedicinales;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.tuapp.plantasmedicinales.database.DatabaseHelper;
import com.tuapp.plantasmedicinales.service.AuthService;

public class ProfileActivity extends BaseActivity {

    private ImageButton btnBack;
    private TextView tvUsername;
    private TextView tvMemberSince;
    private TextView tvTotalIdentifications;
    private TextView tvLastIdentification;
    private TextView tvFavoritePlant;
    private Button btnEditProfile;
    private AuthService authService;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        authService = new AuthService(this);
        dbHelper = new DatabaseHelper(this);

        // Inicializar vistas
        btnBack = findViewById(R.id.btnBack);
        tvUsername = findViewById(R.id.tvUsername);
        tvMemberSince = findViewById(R.id.tvMemberSince);
        tvTotalIdentifications = findViewById(R.id.tvTotalIdentifications);
        tvLastIdentification = findViewById(R.id.tvLastIdentification);
        tvFavoritePlant = findViewById(R.id.tvFavoritePlant);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Editar perfil - Próximamente", Toast.LENGTH_SHORT).show();
            }
        });

        loadProfileData();
    }

    private void loadProfileData() {
        // Obtener datos del usuario
        String username = authService.getUsername();
        if (username != null && !username.isEmpty()) {
            tvUsername.setText(username);
        } else {
            tvUsername.setText("Usuario");
        }

        // Cargar estadísticas reales desde la base de datos
        DatabaseHelper.UserStats stats = dbHelper.getUserStats(username);

        tvMemberSince.setText("Miembro desde: Enero 2025");
        tvTotalIdentifications.setText(String.valueOf(stats.totalIdentifications));
        tvLastIdentification.setText(stats.lastIdentification);
        tvFavoritePlant.setText(stats.favoritePlant);
    }
}
