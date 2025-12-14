package com.tuapp.plantasmedicinales.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.tuapp.plantasmedicinales.service.CNNService;
import com.tuapp.plantasmedicinales.model.Prediction;
import com.tuapp.plantasmedicinales.utils.Constants;

/**
 * Controlador para identificación de plantas usando modelo TFLite local
 */
public class IdentificationController {

    private static final String TAG = "IdentificationController";

    private final Context context;
    private final CNNService cnnService;
    private final Handler mainHandler;

    public interface IdentificationCallback {
        void onComplete(Prediction prediction);
        void onProgress(String message);
    }

    public IdentificationController(Context context) {
        this.context = context;
        this.cnnService = new CNNService(context);
        this.mainHandler = new Handler(Looper.getMainLooper());
        Log.d(TAG, "Sistema TFLite inicializado correctamente");
    }

    /**
     * Identifica una planta (método síncrono)
     */
    public Prediction identifyPlant(Bitmap image) {
        if (image == null) {
            Log.e(TAG, "Error: imagen es null");
            return null;
        }

        Log.d(TAG, "=== IDENTIFICACIÓN CON TFLITE ===");
        Log.d(TAG, "Tamaño imagen original: " + image.getWidth() + "x" + image.getHeight());

        // Escalar imagen al tamaño esperado por el modelo
        Bitmap scaledImage = Bitmap.createScaledBitmap(image, Constants.INPUT_SIZE, Constants.INPUT_SIZE, false);
        Prediction prediction = cnnService.predictPlant(scaledImage);

        if (prediction != null) {
            Log.d(TAG, "✓ Predicción exitosa: " + prediction.getPlantName() +
                  " (confianza: " + String.format("%.2f%%", prediction.getConfidence() * 100) + ")");
        } else {
            Log.e(TAG, "✗ Error: No se pudo obtener predicción");
        }

        return prediction;
    }

    /**
     * Identifica una planta (método asíncrono - no bloquea UI)
     */
    public void identifyPlantAsync(Bitmap image, IdentificationCallback callback) {
        if (image == null) {
            mainHandler.post(() -> callback.onComplete(null));
            return;
        }

        // Ejecutar en hilo secundario
        new Thread(() -> {
            Log.d(TAG, "=== IDENTIFICACIÓN ASÍNCRONA CON TFLITE ===");
            
            // Notificar progreso
            mainHandler.post(() -> callback.onProgress("Analizando imagen con IA..."));

            // Escalar y procesar
            Bitmap scaledImage = Bitmap.createScaledBitmap(image, Constants.INPUT_SIZE, Constants.INPUT_SIZE, false);
            Prediction prediction = cnnService.predictPlant(scaledImage);

            if (prediction != null) {
                Log.d(TAG, "✓ Predicción exitosa: " + prediction.getPlantName() +
                      " (confianza: " + String.format("%.2f%%", prediction.getConfidence() * 100) + ")");
            } else {
                Log.e(TAG, "✗ Error al procesar imagen");
            }

            // Retornar resultado en hilo principal
            mainHandler.post(() -> callback.onComplete(prediction));
        }).start();
    }

    /**
     * Libera recursos del modelo
     */
    public void cleanup() {
        if (cnnService != null) {
            cnnService.close();
            Log.d(TAG, "Recursos liberados");
        }
    }
}
