package com.tuapp.plantasmedicinales.model;

import com.google.gson.annotations.SerializedName;

/**
 * Respuesta del endpoint POST /api/login.php
 */
public class LoginResponse {
    private boolean success;
    private String message;
    private String token;
    private User user;

    // Getters y Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    /**
     * Clase interna para los datos del usuario
     */
    public static class User {
        private int id;
        private String username;
        private String email;

        @SerializedName("full_name")
        private String fullName;

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
    }
}
