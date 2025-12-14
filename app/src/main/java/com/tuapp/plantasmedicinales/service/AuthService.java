package com.tuapp.plantasmedicinales.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.tuapp.plantasmedicinales.model.LoginResponse;

public class AuthService {
    private static final String PREF_NAME = "PlantasAuth";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER = "user";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_FULLNAME = "user_fullname";
    private static final String KEY_IS_LOGGED = "isLogged";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public AuthService(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * Login local (para testing sin servidor)
     */
    public boolean login(String username, String password) {
        if (username.equals("admin") && password.equals("admin123")) {
            editor.putString(KEY_USER, username);
            editor.putInt(KEY_USER_ID, 1); // ID por defecto para admin local
            editor.putBoolean(KEY_IS_LOGGED, true);
            editor.putString(KEY_TOKEN, "dummy_token_123");
            editor.apply();
            return true;
        }
        return false;
    }

    /**
     * Guardar sesión desde respuesta de API
     */
    public void saveSession(LoginResponse response) {
        if (response != null && response.isSuccess()) {
            editor.putString(KEY_TOKEN, response.getToken());
            editor.putBoolean(KEY_IS_LOGGED, true);

            if (response.getUser() != null) {
                LoginResponse.User user = response.getUser();
                editor.putInt(KEY_USER_ID, user.getId());
                editor.putString(KEY_USER, user.getUsername());
                editor.putString(KEY_USER_EMAIL, user.getEmail());
                editor.putString(KEY_USER_FULLNAME, user.getFullName());
            }

            editor.apply();
        }
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGGED, false);
    }

    public String getUsername() {
        return preferences.getString(KEY_USER, "");
    }

    public String getToken() {
        return preferences.getString(KEY_TOKEN, "");
    }

    /**
     * Obtiene el ID numérico del usuario logueado
     * @return user_id o null si no hay usuario logueado
     */
    public Integer getUserId() {
        if (isLoggedIn()) {
            int userId = preferences.getInt(KEY_USER_ID, -1);
            return userId > 0 ? userId : null;
        }
        return null;
    }

    public String getUserEmail() {
        return preferences.getString(KEY_USER_EMAIL, "");
    }

    public String getUserFullName() {
        return preferences.getString(KEY_USER_FULLNAME, "");
    }
}
