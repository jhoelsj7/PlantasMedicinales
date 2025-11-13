package com.tuapp.plantasmedicinales;

import com.tuapp.plantasmedicinales.utils.ValidationUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static com.google.common.truth.Truth.assertThat;

/**
 * CASO DE PRUEBA 1: Validación de credenciales de usuario
 *
 * Objetivo: Verificar que el sistema valida correctamente el formato y longitud
 * de username y password según las reglas de negocio establecidas.
 *
 * Criterios:
 * - Username: debe tener entre 3 y 20 caracteres
 * - Password: debe tener mínimo 6 caracteres
 *
 * Herramientas: JUnit 4.13.2, Robolectric 4.11.1, Truth (Google) para assertions
 */
@RunWith(RobolectricTestRunner.class)
public class ValidationUtilsTest {

    // ========== TESTS DE USERNAME ==========

    @Test
    public void testUsernameVacio_DebeRetornarFalse() {
        // Arrange
        String username = "";

        // Act
        boolean resultado = ValidationUtils.isValidUsername(username);

        // Assert
        assertThat(resultado).isFalse();
    }

    @Test
    public void testUsernameNull_DebeRetornarFalse() {
        // Arrange
        String username = null;

        // Act
        boolean resultado = ValidationUtils.isValidUsername(username);

        // Assert
        assertThat(resultado).isFalse();
    }

    @Test
    public void testUsernameMenorA3Caracteres_DebeRetornarFalse() {
        // Arrange
        String username = "ab";

        // Act
        boolean resultado = ValidationUtils.isValidUsername(username);

        // Assert
        assertThat(resultado).isFalse();
    }

    @Test
    public void testUsernameExactamente3Caracteres_DebeRetornarTrue() {
        // Arrange
        String username = "abc";

        // Act
        boolean resultado = ValidationUtils.isValidUsername(username);

        // Assert
        assertThat(resultado).isTrue();
    }

    @Test
    public void testUsernameValido_DebeRetornarTrue() {
        // Arrange
        String username = "usuario123";

        // Act
        boolean resultado = ValidationUtils.isValidUsername(username);

        // Assert
        assertThat(resultado).isTrue();
    }

    @Test
    public void testUsernameExactamente20Caracteres_DebeRetornarTrue() {
        // Arrange
        String username = "12345678901234567890"; // 20 caracteres exactos

        // Act
        boolean resultado = ValidationUtils.isValidUsername(username);

        // Assert
        assertThat(resultado).isTrue();
    }

    @Test
    public void testUsernameMayorA20Caracteres_DebeRetornarFalse() {
        // Arrange
        String username = "usuariomuylargodemas20caracteres"; // más de 20

        // Act
        boolean resultado = ValidationUtils.isValidUsername(username);

        // Assert
        assertThat(resultado).isFalse();
    }

    // ========== TESTS DE PASSWORD ==========

    @Test
    public void testPasswordVacio_DebeRetornarFalse() {
        // Arrange
        String password = "";

        // Act
        boolean resultado = ValidationUtils.isValidPassword(password);

        // Assert
        assertThat(resultado).isFalse();
    }

    @Test
    public void testPasswordNull_DebeRetornarFalse() {
        // Arrange
        String password = null;

        // Act
        boolean resultado = ValidationUtils.isValidPassword(password);

        // Assert
        assertThat(resultado).isFalse();
    }

    @Test
    public void testPasswordMenorA6Caracteres_DebeRetornarFalse() {
        // Arrange
        String password = "12345"; // 5 caracteres

        // Act
        boolean resultado = ValidationUtils.isValidPassword(password);

        // Assert
        assertThat(resultado).isFalse();
    }

    @Test
    public void testPasswordExactamente6Caracteres_DebeRetornarTrue() {
        // Arrange
        String password = "Pass12"; // 6 caracteres exactos

        // Act
        boolean resultado = ValidationUtils.isValidPassword(password);

        // Assert
        assertThat(resultado).isTrue();
    }

    @Test
    public void testPasswordValido_DebeRetornarTrue() {
        // Arrange
        String password = "Pass123";

        // Act
        boolean resultado = ValidationUtils.isValidPassword(password);

        // Assert
        assertThat(resultado).isTrue();
    }

    @Test
    public void testPasswordLargo_DebeRetornarTrue() {
        // Arrange
        String password = "PasswordValido2024";

        // Act
        boolean resultado = ValidationUtils.isValidPassword(password);

        // Assert
        assertThat(resultado).isTrue();
    }

    // ========== TESTS DE EMAIL ==========

    @Test
    public void testEmailValido_DebeRetornarTrue() {
        // Arrange
        String email = "usuario@example.com";

        // Act
        boolean resultado = ValidationUtils.isValidEmail(email);

        // Assert
        assertThat(resultado).isTrue();
    }

    @Test
    public void testEmailInvalido_DebeRetornarFalse() {
        // Arrange
        String email = "usuarioinvalido";

        // Act
        boolean resultado = ValidationUtils.isValidEmail(email);

        // Assert
        assertThat(resultado).isFalse();
    }

    @Test
    public void testEmailVacio_DebeRetornarFalse() {
        // Arrange
        String email = "";

        // Act
        boolean resultado = ValidationUtils.isValidEmail(email);

        // Assert
        assertThat(resultado).isFalse();
    }

    // ========== TESTS DE CONFIDENCE ==========

    @Test
    public void testConfidenceValida_DebeRetornarTrue() {
        // Arrange
        float confidence = 0.95f;

        // Act
        boolean resultado = ValidationUtils.isValidConfidence(confidence);

        // Assert
        assertThat(resultado).isTrue();
    }

    @Test
    public void testConfidenceNegativa_DebeRetornarFalse() {
        // Arrange
        float confidence = -0.5f;

        // Act
        boolean resultado = ValidationUtils.isValidConfidence(confidence);

        // Assert
        assertThat(resultado).isFalse();
    }

    @Test
    public void testConfidenceMayorA1_DebeRetornarFalse() {
        // Arrange
        float confidence = 1.5f;

        // Act
        boolean resultado = ValidationUtils.isValidConfidence(confidence);

        // Assert
        assertThat(resultado).isFalse();
    }
}
