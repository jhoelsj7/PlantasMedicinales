package com.tuapp.plantasmedicinales;

import android.content.Context;
import android.content.SharedPreferences;

import com.tuapp.plantasmedicinales.service.AuthService;
import com.tuapp.plantasmedicinales.service.SessionManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static com.google.common.truth.Truth.assertThat;

/**
 * CASO DE PRUEBA 7: Gestión de sesión y timeout
 *
 * Objetivo: Verificar que el SessionManager controla correctamente el ciclo de vida
 * de la sesión y expira tras 30 minutos de inactividad.
 *
 * Criterios:
 * - Sesión activa permite acciones
 * - Última actividad se actualiza con cada interacción
 * - Tras timeout (30 min = 1800000ms) muestra 'Sesión expirada' y fuerza nuevo login
 *
 * Herramientas: JUnit 4, Robolectric 4.11.1, Truth para assertions
 */
@RunWith(RobolectricTestRunner.class)
public class SessionManagerTest {

    private Context context;
    private SessionManager sessionManager;
    private AuthService authService;
    private SharedPreferences prefs;
    private static final long DEFAULT_TIMEOUT = 30 * 60 * 1000; // 30 minutos

    @Before
    public void setUp() {
        // Obtener contexto de prueba
        context = RuntimeEnvironment.getApplication();

        // Limpiar preferencias antes de cada test
        SharedPreferences sessionPrefs = context.getSharedPreferences("PlantasSession", Context.MODE_PRIVATE);
        sessionPrefs.edit().clear().apply();

        SharedPreferences authPrefs = context.getSharedPreferences("PlantasAuth", Context.MODE_PRIVATE);
        authPrefs.edit().clear().apply();

        SharedPreferences settingsPrefs = context.getSharedPreferences("PlantasPrefs", Context.MODE_PRIVATE);
        settingsPrefs.edit().clear().apply();

        // Inicializar servicios
        authService = new AuthService(context);
        sessionManager = new SessionManager(context);
        prefs = context.getSharedPreferences("PlantasSession", Context.MODE_PRIVATE);
    }

    // ========== TESTS DE CREACIÓN DE SESIÓN ==========

    @Test
    public void testUsuarioLogueado_ActualizaUltimaActividad() {
        // Arrange: Loguear usuario
        authService.login("admin", "admin123");

        // Act: Actualizar última actividad
        sessionManager.updateLastActivity();

        // Assert: Verificar que se guardó el timestamp
        long lastActivity = prefs.getLong("lastActivity", 0);
        assertThat(lastActivity).isGreaterThan(0);
    }

    @Test
    public void testSesionReciente_NoDebeEstarExpirada() {
        // Arrange: Loguear usuario y actualizar actividad
        authService.login("admin", "admin123");
        sessionManager.updateLastActivity();

        // Act: Verificar expiración inmediatamente
        boolean expired = sessionManager.isSessionExpired();

        // Assert: No debe estar expirada
        assertThat(expired).isFalse();
    }

    @Test
    public void testUsuarioNoLogueado_SesionDebeEstarExpirada() {
        // Arrange: NO loguear usuario

        // Act: Verificar expiración
        boolean expired = sessionManager.isSessionExpired();

        // Assert: Debe estar expirada porque no hay login
        assertThat(expired).isTrue();
    }

    // ========== TESTS DE TIMEOUT DE SESIÓN ==========

    @Test
    public void testSesionConTimeout_DebeEstarExpirada() {
        // Arrange: Loguear usuario
        authService.login("admin", "admin123");

        // Simular última actividad hace 31 minutos (más que el timeout de 30)
        long hace31Minutos = System.currentTimeMillis() - (31 * 60 * 1000);
        prefs.edit().putLong("lastActivity", hace31Minutos).apply();

        // Act: Verificar expiración
        boolean expired = sessionManager.isSessionExpired();

        // Assert: Debe estar expirada
        assertThat(expired).isTrue();
    }

    @Test
    public void testSesionDentroTimeout_NoDebeEstarExpirada() {
        // Arrange: Loguear usuario
        authService.login("admin", "admin123");

        // Simular última actividad hace 25 minutos (menos que el timeout de 30)
        long hace25Minutos = System.currentTimeMillis() - (25 * 60 * 1000);
        prefs.edit().putLong("lastActivity", hace25Minutos).apply();

        // Act: Verificar expiración
        boolean expired = sessionManager.isSessionExpired();

        // Assert: NO debe estar expirada
        assertThat(expired).isFalse();
    }

    @Test
    public void testSesionExactamenteEnTimeout_DebeEstarExpirada() {
        // Arrange: Loguear usuario
        authService.login("admin", "admin123");

        // Simular última actividad hace exactamente 30 minutos + 1ms
        long exactamente30min = System.currentTimeMillis() - DEFAULT_TIMEOUT - 1;
        prefs.edit().putLong("lastActivity", exactamente30min).apply();

        // Act: Verificar expiración
        boolean expired = sessionManager.isSessionExpired();

        // Assert: Debe estar expirada (por 1ms)
        assertThat(expired).isTrue();
    }

    // ========== TESTS DE TIEMPO RESTANTE ==========

    @Test
    public void testTiempoRestante_DebeDevolverValorCorrecto() {
        // Arrange: Loguear usuario
        authService.login("admin", "admin123");

        // Simular última actividad hace 10 minutos
        long hace10Minutos = System.currentTimeMillis() - (10 * 60 * 1000);
        prefs.edit().putLong("lastActivity", hace10Minutos).apply();

        // Act: Obtener tiempo restante
        long timeRemaining = sessionManager.getTimeUntilExpiration();

        // Assert: Debe quedar aproximadamente 20 minutos (con margen de 1 segundo)
        long expectedRemaining = 20 * 60 * 1000; // 20 minutos
        long marginError = 1000; // 1 segundo de margen
        assertThat(timeRemaining).isAtLeast(expectedRemaining - marginError);
        assertThat(timeRemaining).isAtMost(expectedRemaining + marginError);
    }

    @Test
    public void testTiempoRestante_SesionExpirada_DebeRetornar0() {
        // Arrange: Loguear usuario
        authService.login("admin", "admin123");

        // Simular última actividad hace 35 minutos (más que el timeout)
        long hace35Minutos = System.currentTimeMillis() - (35 * 60 * 1000);
        prefs.edit().putLong("lastActivity", hace35Minutos).apply();

        // Act: Obtener tiempo restante
        long timeRemaining = sessionManager.getTimeUntilExpiration();

        // Assert: Debe retornar 0
        assertThat(timeRemaining).isEqualTo(0);
    }

    // ========== TESTS DE EXPIRACIÓN Y LIMPIEZA ==========

    @Test
    public void testExpireSession_DebeCerrarSesionYLimpiarDatos() {
        // Arrange: Loguear usuario y actualizar actividad
        authService.login("admin", "admin123");
        sessionManager.updateLastActivity();

        // Verificar que está logueado
        assertThat(authService.isLoggedIn()).isTrue();

        // Act: Expirar sesión manualmente
        sessionManager.expireSession();

        // Assert: Verificar que se cerró la sesión
        assertThat(authService.isLoggedIn()).isFalse();

        // Assert: Verificar que se limpiaron datos de sesión
        long lastActivity = prefs.getLong("lastActivity", -1);
        assertThat(lastActivity).isEqualTo(-1);
    }

    @Test
    public void testClearSessionData_DebeLimpiarPreferencias() {
        // Arrange: Guardar datos en sesión
        sessionManager.updateLastActivity();
        prefs.edit().putString("test_key", "test_value").apply();

        // Act: Limpiar datos de sesión
        sessionManager.clearSessionData();

        // Assert: Verificar que se limpiaron todos los datos
        assertThat(prefs.getAll()).isEmpty();
    }

    // ========== TEST DE ACTUALIZACIÓN CONTINUA DE ACTIVIDAD ==========

    @Test
    public void testActualizacionContinua_MantieneSessionActiva() throws InterruptedException {
        // Arrange: Loguear usuario
        authService.login("admin", "admin123");

        // Simular uso continuo de la app
        for (int i = 0; i < 5; i++) {
            sessionManager.updateLastActivity();
            Thread.sleep(100); // Esperar 100ms entre actualizaciones

            // Assert: La sesión debe seguir activa
            assertThat(sessionManager.isSessionExpired()).isFalse();
        }
    }
}
