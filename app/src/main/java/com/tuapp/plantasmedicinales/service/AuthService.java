package com.tuapp.plantasmedicinales.service;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthService {
    private static final String PREF_NAME = "PlantasAuth";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER = "user";
    private static final String KEY_IS_LOGGED = "isLogged";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public AuthService(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public boolean login(String username, String password) {
        if (username.equals("admin") && password.equals("admin123")) {
            editor.putString(KEY_USER, username);
            editor.putBoolean(KEY_IS_LOGGED, true);
            editor.putString(KEY_TOKEN, "dummy_token_123");
            editor.apply();
            return true;
        }
        return false;
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
}