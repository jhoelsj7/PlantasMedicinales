package com.tuapp.plantasmedicinales;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import com.tuapp.plantasmedicinales.controller.SyncController;
import com.tuapp.plantasmedicinales.service.AuthService;
import com.tuapp.plantasmedicinales.utils.DatabaseInitializer;

public class MainActivity extends BaseActivity {

    private ImageView btnMenu;
    private CardView btnCamera, btnPlantList, btnSearch, btnSync;
    private SyncController syncController;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        syncController = new SyncController(this);
        authService = new AuthService(this);

        // Inicializar base de datos con plantas por defecto si está vacía
        DatabaseInitializer.initializeWithDefaultPlants(this);

        // Inicializar vistas
        btnMenu = findViewById(R.id.btnMenu);
        btnCamera = findViewById(R.id.btnCamera);
        btnPlantList = findViewById(R.id.btnPlantList);
        btnSearch = findViewById(R.id.btnSearch);
        btnSync = findViewById(R.id.btnSync);

        // Menú principal (3 puntos superior)
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMenuPrincipal(v);
            }
        });

        // Clicks en las cards
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
            }
        });

        btnPlantList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PlantListActivity.class));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronizeData();
            }
        });

        // Sincronizar datos al iniciar
        synchronizeData();
    }

    private void synchronizeData() {
        Toast.makeText(this, "Sincronizando datos...", Toast.LENGTH_SHORT).show();

        syncController.synchronizeData(new SyncController.SyncCallback() {
            @Override
            public void onSyncComplete(final boolean success, final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // MÉTODO PARA MOSTRAR POPUP MENU BLANCO
    private void mostrarMenuPrincipal(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.menu_principal, popup.getMenu());

        // FORZAR MOSTRAR ICONOS (si los hay)
        try {
            java.lang.reflect.Field[] fields = popup.getClass().getDeclaredFields();
            for (java.lang.reflect.Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    java.lang.reflect.Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Listener para clicks en el menú
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.menu_perfil) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    return true;
                }
                else if (id == R.id.menu_historial) {
                    startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                    return true;
                }
                else if (id == R.id.menu_configuracion) {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                    return true;
                }
                else if (id == R.id.menu_acerca) {
                    showAboutDialog();
                    return true;
                }
                else if (id == R.id.menu_ayuda) {
                    showHelpDialog();
                    return true;
                }
                else if (id == R.id.menu_cerrar) {
                    authService.logout();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                }

                return false;
            }
        });

        popup.show();
    }

    private void showAboutDialog() {
        new android.app.AlertDialog.Builder(this)
            .setTitle("Acerca de Plantas Medicinales")
            .setMessage("Versión 1.0\n\n" +
                    "App de identificación de plantas medicinales usando Inteligencia Artificial.\n\n" +
                    "Modelo IA: 96% de precisión\n" +
                    "12 especies identificables\n\n" +
                    "Desarrollado con ❤️ para la comunidad")
            .setPositiveButton("Cerrar", null)
            .show();
    }

    private void showHelpDialog() {
        new android.app.AlertDialog.Builder(this)
            .setTitle("Ayuda")
            .setMessage("Cómo usar la app:\n\n" +
                    "1. Toca 'Identificar Planta' para tomar foto\n" +
                    "2. Captura o selecciona imagen de la planta\n" +
                    "3. Toca 'Identificar Planta' para analizar\n" +
                    "4. Revisa el resultado y confianza\n\n" +
                    "Consejos:\n" +
                    "• Toma fotos con buena luz\n" +
                    "• Enfoca hojas y flores\n" +
                    "• Evita fondos confusos\n" +
                    "• Sincroniza datos regularmente")
            .setPositiveButton("Entendido", null)
            .show();
    }

    @Override
    public void onBackPressed() {
        // Deshabilitar el botón de retroceso en pantalla principal
        // El usuario debe usar logout del menú para salir
    }
}
