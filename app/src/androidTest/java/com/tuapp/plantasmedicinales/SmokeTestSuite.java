package com.tuapp.plantasmedicinales;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.google.common.truth.Truth.assertThat;

/**
 * CASO DE PRUEBA 10: Verificación de funcionalidades críticas (Smoke Tests)
 *
 * Objetivo: Confirmar que las funciones esenciales de la aplicación están operativas
 * después de cada actualización o instalación.
 *
 * Pruebas incluidas:
 * 1. App abre sin crash
 * 2. Login funciona
 * 3. MainActivity se muestra
 * 4. Botones principales son accesibles
 * 5. Clasificador de IA carga sin errores
 *
 * Criterios:
 * - Todas las funciones responden en <2s
 * - Sin crashes
 * - Sin pantallas en blanco
 * - Navegación fluida entre activities
 *
 * Herramientas: Espresso 3.5.1, AndroidX Test, Truth
 */
@RunWith(AndroidJUnit4.class)
public class SmokeTestSuite {

    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // Limpiar preferencias antes de cada test
        context.getSharedPreferences("PlantasAuth", Context.MODE_PRIVATE)
                .edit().clear().apply();
        context.getSharedPreferences("PlantasSession", Context.MODE_PRIVATE)
                .edit().clear().apply();

        // Inicializar Espresso Intents
        Intents.init();
    }

    @After
    public void tearDown() {
        // Liberar Espresso Intents
        Intents.release();
    }

    // ========== TEST 1: App se inicia sin crashes ==========

    @Test
    public void smokeTest1_AppIniciaSinCrash() {
        // Act: Lanzar LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Assert: Verificar que se muestra correctamente
        onView(withId(R.id.etUsername)).check(matches(isDisplayed()));
        onView(withId(R.id.etPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()));

        scenario.close();
    }

    // ========== TEST 2: Login funciona ==========

    @Test
    public void smokeTest2_LoginFunciona() {
        // Arrange: Lanzar LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Act: Ingresar credenciales válidas
        onView(withId(R.id.etUsername))
                .perform(typeText("admin"), closeSoftKeyboard());

        onView(withId(R.id.etPassword))
                .perform(typeText("admin123"), closeSoftKeyboard());

        // Medir tiempo de respuesta
        long tiempoInicio = System.currentTimeMillis();

        onView(withId(R.id.btnLogin))
                .perform(click());

        // Assert: Esperar que abra MainActivity
        try {
            Thread.sleep(1000); // Esperar transición
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long tiempoFin = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoFin - tiempoInicio;

        // Assert: Login debe completarse en menos de 2 segundos
        assertThat(tiempoTranscurrido).isLessThan(2000L);

        // Assert: Verificar que se navegó a MainActivity
        intended(hasComponent(MainActivity.class.getName()));

        scenario.close();

        // Log para la tesis
        System.out.println("Tiempo de login: " + tiempoTranscurrido + "ms");
    }

    // ========== TEST 3: MainActivity se muestra correctamente ==========

    @Test
    public void smokeTest3_MainActivityCargaCorrectamente() {
        // Arrange: Login primero
        realizarLogin();

        // Act: Esperar carga de MainActivity
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert: Verificar que los botones principales están visibles
        onView(withId(R.id.btnCamera)).check(matches(isDisplayed()));
        onView(withId(R.id.btnPlantList)).check(matches(isDisplayed()));
        onView(withId(R.id.btnSearch)).check(matches(isDisplayed()));
    }

    // ========== TEST 4: Botones de navegación responden ==========

    @Test
    public void smokeTest4_BotonesNavegacionResponden() {
        // Arrange: Login y esperar MainActivity
        realizarLogin();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Act & Assert: Verificar que botón de lista responde
        long tiempoInicio = System.currentTimeMillis();

        onView(withId(R.id.btnPlantList))
                .perform(click());

        try {
            Thread.sleep(500); // Esperar navegación
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long tiempoFin = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoFin - tiempoInicio;

        // Assert: Navegación debe ser rápida (< 2s)
        assertThat(tiempoTranscurrido).isLessThan(2000L);

        // Assert: Verificar que se navegó a PlantListActivity
        intended(hasComponent(PlantListActivity.class.getName()));

        // Log para la tesis
        System.out.println("Tiempo de navegación a lista: " + tiempoTranscurrido + "ms");
    }

    // ========== TEST 5: Búsqueda responde ==========

    @Test
    public void smokeTest5_BusquedaResponde() {
        // Arrange: Login y esperar MainActivity
        realizarLogin();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Act: Click en botón de búsqueda
        long tiempoInicio = System.currentTimeMillis();

        onView(withId(R.id.btnSearch))
                .perform(click());

        try {
            Thread.sleep(500); // Esperar navegación
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long tiempoFin = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoFin - tiempoInicio;

        // Assert: Navegación debe ser rápida (< 2s)
        assertThat(tiempoTranscurrido).isLessThan(2000L);

        // Assert: Verificar que se navegó a SearchActivity
        intended(hasComponent(SearchActivity.class.getName()));

        // Log para la tesis
        System.out.println("Tiempo de navegación a búsqueda: " + tiempoTranscurrido + "ms");
    }

    // ========== TEST 6: Clasificador de IA carga sin errores ==========

    @Test
    public void smokeTest6_ClasificadorIACargaSinErrores() {
        // Act: Inicializar clasificador
        long tiempoInicio = System.currentTimeMillis();

        PlantClassifier classifier = new PlantClassifier(context);

        long tiempoFin = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoFin - tiempoInicio;

        // Assert: Clasificador debe inicializarse correctamente
        assertThat(classifier).isNotNull();

        // Assert: Debe cargar en menos de 2 segundos
        assertThat(tiempoTranscurrido).isLessThan(2000L);

        // Cleanup
        classifier.close();

        // Log para la tesis
        System.out.println("Tiempo de carga del modelo IA: " + tiempoTranscurrido + "ms");
    }

    // ========== TEST 7: Test de integración completo ==========

    @Test
    public void smokeTest7_FlujoCompletoFuncionaSinCrashes() {
        // Este test ejecuta un flujo completo para detectar cualquier crash

        // 1. Iniciar app
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // 2. Login
        onView(withId(R.id.etUsername))
                .perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.etPassword))
                .perform(typeText("admin123"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin))
                .perform(click());

        // 3. Esperar MainActivity
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4. Verificar elementos críticos
        onView(withId(R.id.btnCamera)).check(matches(isDisplayed()));

        // 5. Inicializar clasificador en background
        PlantClassifier classifier = new PlantClassifier(context);
        assertThat(classifier).isNotNull();
        classifier.close();

        // Si llegamos aquí sin crash, el test pasa
        assertThat(true).isTrue();

        scenario.close();

        System.out.println("✓ Flujo completo ejecutado sin crashes");
    }

    // ========== MÉTODOS AUXILIARES ==========

    /**
     * Realiza login para tests que requieren estar autenticados
     */
    private void realizarLogin() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.etUsername))
                .perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.etPassword))
                .perform(typeText("admin123"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin))
                .perform(click());

        // No cerrar el scenario aquí, se maneja en los tests individuales
    }
}
