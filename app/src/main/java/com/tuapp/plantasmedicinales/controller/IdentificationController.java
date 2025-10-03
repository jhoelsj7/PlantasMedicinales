package com.tuapp.plantasmedicinales.controller;

import android.content.Context;
import android.graphics.Bitmap;
import com.tuapp.plantasmedicinales.service.CNNService;
import com.tuapp.plantasmedicinales.model.Prediction;

public class IdentificationController {
    private CNNService cnnService;

    public IdentificationController(Context context) {
        cnnService = new CNNService(context);
    }

    public Prediction identifyPlant(Bitmap image) {
        if (image == null) {
            return null;
        }

        Bitmap scaledImage = Bitmap.createScaledBitmap(image, 224, 224, false);
        return cnnService.predictPlant(scaledImage);
    }

    public void cleanup() {
        if (cnnService != null) {
            cnnService.close();
        }
    }
}