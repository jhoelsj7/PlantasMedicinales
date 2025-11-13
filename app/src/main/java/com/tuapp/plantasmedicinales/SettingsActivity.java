package com.tuapp.plantasmedicinales;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.widget.SwitchCompat;
import com.tuapp.plantasmedicinales.database.DatabaseHelper;
import com.tuapp.plantasmedicinales.service.AuthService;

public class SettingsActivity extends BaseActivity {

    private ImageButton btnBack;
    private LinearLayout btnChangePassword;
    private Button btnChangeSessionTimeout;
    private SwitchCompat switchNotifications;
    private LinearLayout btnClearCache;
    private LinearLayout btnClearHistory;
    private SharedPreferences preferences;
    private DatabaseHelper dbHelper;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences("PlantasPrefs", MODE_PRIVATE);
        dbHelper = new DatabaseHelper(this);
        authService = new AuthService(this);

        // Inicializar vistas
        btnBack = findViewById(R.id.btnBack);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangeSessionTimeout = findViewById(R.id.btnChangeSessionTimeout);
        switchNotifications = findViewById(R.id.switchNotifications);
        btnClearCache = findViewById(R.id.btnClearCache);
        btnClearHistory = findViewById(R.id.btnClearHistory);

        setupListeners();
        loadSettings();
    }

    private void setupListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog();
            }
        });

        btnChangeSessionTimeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSessionTimeoutDialog();
            }
        });

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean("notifications_enabled", isChecked).apply();
            Toast.makeText(SettingsActivity.this,
                isChecked ? "Notificaciones activadas" : "Notificaciones desactivadas",
                Toast.LENGTH_SHORT).show();
        });

        btnClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCache();
            }
        });

        btnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
            }
        });
    }

    private void loadSettings() {
        boolean notificationsEnabled = preferences.getBoolean("notifications_enabled", true);
        switchNotifications.setChecked(notificationsEnabled);
    }

    private void showChangePasswordDialog() {
        new android.app.AlertDialog.Builder(this)
            .setTitle("Cambiar Contraseña")
            .setMessage("Esta función permitirá cambiar tu contraseña.\n\nPróximamente disponible.")
            .setPositiveButton("Entendido", null)
            .show();
    }

    private void showSessionTimeoutDialog() {
        final String[] options = {"3 segundos", "5 segundos", "10 segundos", "30 segundos", "1 minuto", "5 minutos"};
        final int[] timeoutValues = {3000, 5000, 10000, 30000, 60000, 300000};

        new android.app.AlertDialog.Builder(this)
            .setTitle("Tiempo de sesión")
            .setItems(options, (dialog, which) -> {
                preferences.edit().putInt("session_timeout", timeoutValues[which]).apply();
                Toast.makeText(this, "Tiempo de sesión cambiado a " + options[which], Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }

    private void clearCache() {
        new android.app.AlertDialog.Builder(this)
            .setTitle("Borrar Caché")
            .setMessage("¿Estás seguro de que deseas borrar el caché de la aplicación?")
            .setPositiveButton("Borrar", (dialog, which) -> {
                try {
                    deleteCache(getCacheDir());
                    Toast.makeText(this, "Caché borrado exitosamente", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Error al borrar caché", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }

    private void clearHistory() {
        new android.app.AlertDialog.Builder(this)
            .setTitle("Borrar Historial")
            .setMessage("¿Estás seguro de que deseas borrar todo el historial de identificaciones?\n\nEsta acción no se puede deshacer.")
            .setPositiveButton("Borrar", (dialog, which) -> {
                String username = authService.getUsername();
                boolean cleared = dbHelper.clearHistory(username);
                if (cleared) {
                    Toast.makeText(this, "Historial borrado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No hay historial para borrar", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }

    private void deleteCache(java.io.File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    deleteCache(new java.io.File(dir, child));
                }
            }
        }
        if (dir != null) {
            dir.delete();
        }
    }
}
