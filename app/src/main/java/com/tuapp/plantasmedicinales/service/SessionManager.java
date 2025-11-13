package com.tuapp.plantasmedicinales.service;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "PlantasSession";
    private static final String KEY_LAST_ACTIVITY = "lastActivity";
    private static final long DEFAULT_TIMEOUT = 30 * 60 * 1000; // 30 minutos por defecto

    private SharedPreferences preferences;
    private SharedPreferences settingsPrefs;
    private SharedPreferences.Editor editor;
    private AuthService authService;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        settingsPrefs = context.getSharedPreferences("PlantasPrefs", Context.MODE_PRIVATE);
        editor = preferences.edit();
        authService = new AuthService(context);
    }

    /**
     * Obtiene el tiempo de timeout configurado por el usuario
     */
    private long getTimeoutDuration() {
        return settingsPrefs.getInt("session_timeout", (int) DEFAULT_TIMEOUT);
    }

    /**
     * Actualiza el timestamp de última actividad del usuario
     */
    public void updateLastActivity() {
        long currentTime = System.currentTimeMillis();
        editor.putLong(KEY_LAST_ACTIVITY, currentTime);
        editor.apply();
    }

    /**
     * Verifica si la sesión ha expirado por inactividad
     * @return true si la sesión expiró, false si sigue activa
     */
    public boolean isSessionExpired() {
        if (!authService.isLoggedIn()) {
            return true;
        }

        long lastActivity = preferences.getLong(KEY_LAST_ACTIVITY, 0);
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - lastActivity;
        long timeoutDuration = getTimeoutDuration();

        return timeDifference > timeoutDuration;
    }

    /**
     * Cierra la sesión por inactividad
     */
    public void expireSession() {
        authService.logout();
        clearSessionData();
    }

    /**
     * Limpia los datos de la sesión
     */
    public void clearSessionData() {
        editor.clear();
        editor.apply();
    }

    /**
     * Obtiene el tiempo restante antes de que expire la sesión (en milisegundos)
     */
    public long getTimeUntilExpiration() {
        long lastActivity = preferences.getLong(KEY_LAST_ACTIVITY, 0);
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - lastActivity;
        long timeoutDuration = getTimeoutDuration();
        long timeRemaining = timeoutDuration - timeDifference;
        return Math.max(0, timeRemaining);
    }
}
