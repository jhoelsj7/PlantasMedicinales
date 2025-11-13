package com.tuapp.plantasmedicinales;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.tuapp.plantasmedicinales.service.SessionManager;

/**
 * Actividad base que maneja el control de sesión por inactividad.
 * Todas las actividades protegidas deben heredar de esta clase.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected SessionManager sessionManager;
    private boolean shouldCheckSession = true;
    private boolean isUsingExternalActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // FORZAR MODO CLARO SIEMPRE (evita crashes con modo oscuro del sistema)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Si regresamos de una actividad externa (cámara, galería), no verificar sesión
        if (isUsingExternalActivity) {
            // Solo resetear el flag, no actualizar el timestamp todavía
            // Se actualizará cuando haya interacción real del usuario
            isUsingExternalActivity = false;
            sessionManager.updateLastActivity();
            return;
        }

        if (shouldCheckSession) {
            // Verificar si la sesión expiró por inactividad
            if (sessionManager.isSessionExpired()) {
                // Cerrar sesión y redirigir a login con mensaje
                sessionManager.expireSession();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("session_expired", true);
                startActivity(intent);
                finish();
                return;
            }
        }

        // Actualizar timestamp de actividad
        sessionManager.updateLastActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Cuando regresamos de cámara/galería, actualizar timestamp
        sessionManager.updateLastActivity();
        isUsingExternalActivity = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Solo actualizar timestamp si no vamos a una actividad externa
        if (!isUsingExternalActivity) {
            sessionManager.updateLastActivity();
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        // Actualizar timestamp en cada interacción del usuario
        sessionManager.updateLastActivity();
    }

    /**
     * Desactiva la verificación de sesión para esta actividad
     * Útil para LoginActivity
     */
    protected void disableSessionCheck() {
        shouldCheckSession = false;
    }

    /**
     * Marca que se va a usar una actividad externa (cámara, galería, etc.)
     * Esto evita que se cierre la sesión cuando el usuario regrese
     */
    protected void markExternalActivityUsage() {
        isUsingExternalActivity = true;
    }
}
