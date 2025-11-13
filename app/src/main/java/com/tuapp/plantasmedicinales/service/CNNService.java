package com.tuapp.plantasmedicinales.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.tuapp.plantasmedicinales.PlantClassifier;
import com.tuapp.plantasmedicinales.model.Prediction;
import java.util.List;

public class CNNService {
    private static final String TAG = "CNNService";
    private PlantClassifier classifier;
    private Context context;

    public CNNService(Context context) {
        this.context = context;
        try {
            this.classifier = new PlantClassifier(context);
            Log.d(TAG, "PlantClassifier inicializado correctamente");
        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar PlantClassifier: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Prediction predictPlant(Bitmap image) {
        if (classifier == null) {
            Log.e(TAG, "Classifier es null - no se pudo inicializar");
            return null;
        }

        try {
            Log.d(TAG, "Ejecutando predicción...");
            List<PlantClassifier.Recognition> results = classifier.recognizeImage(image);

            if (results != null && !results.isEmpty()) {
                PlantClassifier.Recognition topResult = results.get(0);
                Log.d(TAG, "Predicción exitosa: " + topResult.getTitle() + " con confianza " + topResult.getConfidence());
                return new Prediction(topResult.getTitle(), topResult.getConfidence());
            } else {
                Log.w(TAG, "La lista de resultados está vacía o es null");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error durante la predicción: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        if (classifier != null) {
            classifier.close();
        }
    }
}