package com.tuapp.plantasmedicinales.utils;

import android.text.TextUtils;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidUsername(String username) {
        if (TextUtils.isEmpty(username)) {
            return false;
        }
        return username.length() >= 3 && username.length() <= 20;
    }

    public static boolean isValidPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return password.length() >= 6;
    }

    public static boolean isNotEmpty(String text) {
        return !TextUtils.isEmpty(text) && !text.trim().isEmpty();
    }

    public static String getErrorMessage(String fieldName, String value) {
        if (TextUtils.isEmpty(value)) {
            return fieldName + " no puede estar vacío";
        }
        return null;
    }

    public static boolean isValidConfidence(float confidence) {
        return confidence >= 0.0f && confidence <= 1.0f;
    }
}
