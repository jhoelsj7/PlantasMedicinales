package com.tuapp.plantasmedicinales.service;

import android.content.Context;
import android.graphics.Bitmap;
import com.tuapp.plantasmedicinales.PlantClassifier;
import com.tuapp.plantasmedicinales.model.Prediction;
import java.util.List;

public class CNNService {
    private PlantClassifier classifier;
    private Context context;

    public CNNService(Context context) {
        this.context = context;
        this.classifier = new PlantClassifier(context);
    }

    public Prediction predictPlant(Bitmap image) {
        try {
            List<PlantClassifier.Recognition> results = classifier.recognizeImage(image);
            if (results != null && !results.isEmpty()) {
                PlantClassifier.Recognition topResult = results.get(0);
                return new Prediction(topResult.getTitle(), topResult.getConfidence());
            }
        } catch (Exception e) {
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