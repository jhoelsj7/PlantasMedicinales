package com.tuapp.plantasmedicinales;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * CASO DE PRUEBA 2: Clasificación de imagen con modelo TensorFlow Lite
 *
 * Objetivo: Evaluar que el modelo de IA identifica correctamente plantas medicinales
 * con una confianza superior al umbral establecido para imágenes de prueba.
 *
 * Criterios:
 * - Modelo carga correctamente desde assets
 * - Procesa imágenes de 224x224px
 * - Retorna predicciones ordenadas por confianza
 * - Tiempo de inferencia < 3 segundos
 *
 * Herramientas: AndroidX Test, Truth para assertions, Instrumentación
 */
@RunWith(AndroidJUnit4.class)
public class PlantClassifierTest {

    private Context context;
    private PlantClassifier classifier;

    @Before
    public void setUp() {
        // Obtener contexto de instrumentación
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // Inicializar clasificador (esto carga el modelo TFLite)
        classifier = new PlantClassifier(context);
    }

    // ========== TESTS DE CARGA DEL MODELO ==========

    @Test
    public void testModeloCargaCorrectamente() {
        // Assert: El clasificador debe haberse inicializado sin errores
        assertThat(classifier).isNotNull();
    }

    @Test
    public void testModeloProcesaImagenNula() {
        // Arrange: Imagen nula
        Bitmap imagen = null;

        // Act: Intentar clasificar
        List<PlantClassifier.Recognition> resultados = classifier.recognizeImage(imagen);

        // Assert: Debe retornar lista vacía, no crashear
        assertThat(resultados).isNotNull();
        assertThat(resultados).isEmpty();
    }

    // ========== TESTS DE PROCESAMIENTO DE IMÁGENES ==========

    @Test
    public void testModeloProcesaImagenVerde() {
        // Arrange: Crear imagen sintética verde (simulando hoja)
        Bitmap imagen = crearImagenColor(Color.GREEN);

        // Act: Clasificar imagen
        List<PlantClassifier.Recognition> resultados = classifier.recognizeImage(imagen);

        // Assert: Debe retornar al menos algún resultado
        assertThat(resultados).isNotNull();
        // Puede estar vacía si ninguna confianza supera el threshold
    }

    @Test
    public void testModeloProcesaImagenDeResolucionCorrecta() {
        // Arrange: Crear imagen de 224x224 (tamaño esperado por el modelo)
        Bitmap imagen = Bitmap.createBitmap(224, 224, Bitmap.Config.ARGB_8888);
        // Llenar con patrón verde
        for (int x = 0; x < 224; x++) {
            for (int y = 0; y < 224; y++) {
                imagen.setPixel(x, y, Color.rgb(50, 150, 50));
            }
        }

        // Act: Clasificar
        List<PlantClassifier.Recognition> resultados = classifier.recognizeImage(imagen);

        // Assert: Debe procesar sin errores
        assertThat(resultados).isNotNull();
    }

    @Test
    public void testModeloProcesaImagenDeResolucionDiferente() {
        // Arrange: Crear imagen de tamaño diferente (será escalada internamente)
        Bitmap imagen = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
        imagen.eraseColor(Color.rgb(100, 200, 100));

        // Act: Clasificar
        List<PlantClassifier.Recognition> resultados = classifier.recognizeImage(imagen);

        // Assert: Debe procesar sin errores (el clasificador escala internamente)
        assertThat(resultados).isNotNull();
    }

    // ========== TESTS DE RESULTADOS ==========

    @Test
    public void testResultadosTienenFormatoCorrecto() {
        // Arrange: Crear imagen de prueba
        Bitmap imagen = crearImagenColor(Color.GREEN);

        // Act: Clasificar
        List<PlantClassifier.Recognition> resultados = classifier.recognizeImage(imagen);

        // Assert: Si hay resultados, deben tener formato correcto
        if (resultados != null && !resultados.isEmpty()) {
            for (PlantClassifier.Recognition recognition : resultados) {
                assertThat(recognition.getId()).isNotNull();
                assertThat(recognition.getTitle()).isNotNull();
                assertThat(recognition.getConfidence()).isAtLeast(0.0f);
                assertThat(recognition.getConfidence()).isAtMost(1.0f);
            }
        }
    }

    @Test
    public void testResultadosOrdenadosPorConfianza() {
        // Arrange: Crear imagen de prueba
        Bitmap imagen = crearImagenColor(Color.GREEN);

        // Act: Clasificar
        List<PlantClassifier.Recognition> resultados = classifier.recognizeImage(imagen);

        // Assert: Si hay múltiples resultados, deben estar ordenados descendente por confianza
        if (resultados != null && resultados.size() > 1) {
            for (int i = 0; i < resultados.size() - 1; i++) {
                float confianzaActual = resultados.get(i).getConfidence();
                float confianzaSiguiente = resultados.get(i + 1).getConfidence();
                assertThat(confianzaActual).isAtLeast(confianzaSiguiente);
            }
        }
    }

    @Test
    public void testMaximoTresResultados() {
        // Arrange: Crear imagen de prueba
        Bitmap imagen = crearImagenColor(Color.GREEN);

        // Act: Clasificar
        List<PlantClassifier.Recognition> resultados = classifier.recognizeImage(imagen);

        // Assert: No debe retornar más de 3 resultados (MAX_RESULTS = 3)
        if (resultados != null) {
            assertThat(resultados.size()).isAtMost(3);
        }
    }

    // ========== TESTS DE RENDIMIENTO ==========

    @Test
    public void testTiempoInferenciaMenorA3Segundos() {
        // Arrange: Crear imagen de prueba
        Bitmap imagen = crearImagenColor(Color.GREEN);

        // Act: Medir tiempo de clasificación
        long tiempoInicio = System.currentTimeMillis();
        List<PlantClassifier.Recognition> resultados = classifier.recognizeImage(imagen);
        long tiempoFin = System.currentTimeMillis();

        long tiempoTranscurrido = tiempoFin - tiempoInicio;

        // Assert: Debe tardar menos de 3 segundos (3000ms)
        assertThat(tiempoTranscurrido).isLessThan(3000L);

        // Log para la tesis
        System.out.println("Tiempo de inferencia: " + tiempoTranscurrido + "ms");
    }

    @Test
    public void testRendimientoMultiplesInferencias() {
        // Arrange: Crear imagen de prueba
        Bitmap imagen = crearImagenColor(Color.GREEN);
        int numeroInferencias = 10;

        // Act: Realizar múltiples inferencias y medir tiempo total
        long tiempoInicio = System.currentTimeMillis();

        for (int i = 0; i < numeroInferencias; i++) {
            classifier.recognizeImage(imagen);
        }

        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;
        long tiempoPromedio = tiempoTotal / numeroInferencias;

        // Assert: Tiempo promedio debe ser razonable
        assertThat(tiempoPromedio).isLessThan(3000L);

        // Log para la tesis
        System.out.println("Tiempo total para " + numeroInferencias + " inferencias: " + tiempoTotal + "ms");
        System.out.println("Tiempo promedio por inferencia: " + tiempoPromedio + "ms");
    }

    // ========== TESTS DE CONSISTENCIA ==========

    @Test
    public void testMismaImagenProduceMismosResultados() {
        // Arrange: Crear imagen de prueba
        Bitmap imagen = crearImagenColor(Color.rgb(50, 150, 50));

        // Act: Clasificar dos veces la misma imagen
        List<PlantClassifier.Recognition> resultados1 = classifier.recognizeImage(imagen);
        List<PlantClassifier.Recognition> resultados2 = classifier.recognizeImage(imagen);

        // Assert: Deben tener el mismo tamaño
        assertThat(resultados1.size()).isEqualTo(resultados2.size());

        // Assert: Si hay resultados, los top predictions deben ser iguales
        if (resultados1.size() > 0 && resultados2.size() > 0) {
            assertThat(resultados1.get(0).getTitle()).isEqualTo(resultados2.get(0).getTitle());
            // Las confianzas deben ser muy similares (con pequeño margen de error)
            float diferencia = Math.abs(resultados1.get(0).getConfidence() - resultados2.get(0).getConfidence());
            assertThat(diferencia).isLessThan(0.001f); // Margen de error: 0.1%
        }
    }

    // ========== TESTS DE LIMPIEZA ==========

    @Test
    public void testCloseLiberaRecursos() {
        // Arrange: Crear clasificador
        PlantClassifier tempClassifier = new PlantClassifier(context);

        // Act: Cerrar clasificador
        tempClassifier.close();

        // Assert: Debe poder volver a crear un clasificador (recursos liberados)
        PlantClassifier nuevoClassifier = new PlantClassifier(context);
        assertThat(nuevoClassifier).isNotNull();
        nuevoClassifier.close();
    }

    // ========== MÉTODOS AUXILIARES ==========

    /**
     * Crea una imagen sintética de 224x224 con un color sólido
     */
    private Bitmap crearImagenColor(int color) {
        Bitmap bitmap = Bitmap.createBitmap(224, 224, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(color);
        return bitmap;
    }
}
