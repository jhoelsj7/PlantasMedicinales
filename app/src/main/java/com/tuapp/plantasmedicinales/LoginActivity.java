package com.tuapp.plantasmedicinales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.tuapp.plantasmedicinales.service.AuthService;
import com.tuapp.plantasmedicinales.service.SessionManager;
import com.tuapp.plantasmedicinales.utils.ValidationUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private AuthService authService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authService = new AuthService(this);
        sessionManager = new SessionManager(this);

        // Verificar si llegó por sesión expirada
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("session_expired", false)) {
            Toast.makeText(this, "Sesión cerrada por inactividad. Por favor, inicia sesión de nuevo.", Toast.LENGTH_LONG).show();
        }

        // COMENTADO: Auto-login desactivado - siempre muestra pantalla de login
        // if (authService.isLoggedIn() && !sessionManager.isSessionExpired()) {
        //     navigateToMain();
        //     return;
        // }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validar campos vacíos
        if (!ValidationUtils.isNotEmpty(username)) {
            etUsername.setError("Usuario requerido");
            etUsername.requestFocus();
            return;
        }

        if (!ValidationUtils.isNotEmpty(password)) {
            etPassword.setError("Contraseña requerida");
            etPassword.requestFocus();
            return;
        }

        // Validar longitud mínima
        if (!ValidationUtils.isValidUsername(username)) {
            etUsername.setError("Usuario debe tener entre 3 y 20 caracteres");
            etUsername.requestFocus();
            return;
        }

        if (!ValidationUtils.isValidPassword(password)) {
            etPassword.setError("Contraseña debe tener al menos 6 caracteres");
            etPassword.requestFocus();
            return;
        }

        // Intentar login
        if (authService.login(username, password)) {
            // Inicializar timestamp de sesión
            sessionManager.updateLastActivity();
            Toast.makeText(this, "Bienvenido " + username, Toast.LENGTH_SHORT).show();
            navigateToMain();
        } else {
            Toast.makeText(this, "Credenciales incorrectas. Intenta con: admin/admin123", Toast.LENGTH_LONG).show();
        }
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}