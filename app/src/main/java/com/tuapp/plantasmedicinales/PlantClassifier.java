package com.tuapp.plantasmedicinales;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class PlantClassifier {
    private static final String MODEL_NAME = "modelo_plantas_96acc.tflite";
    private static final int INPUT_SIZE = 128;

    private Interpreter interpreter;
    private ByteBuffer imgData;

    public PlantClassifier(Context context) throws IOException {
        interpreter = new Interpreter(loadModelFile(context));
        imgData = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * 3);
        imgData.order(ByteOrder.nativeOrder());
    }

    private MappedByteBuffer loadModelFile(Context context) throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(MODEL_NAME);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public Result classifyImage(Bitmap bitmap) {
        if (bitmap == null) return null;

        Bitmap resized = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true);
        convertBitmapToByteBuffer(resized);

        float[][] result = new float[1][1];
        interpreter.run(imgData, result);

        float probability = result[0][0];

        Result plantResult = new Result();
        if (probability < 0.5f) {
            plantResult.name = "Astromeria";
            plantResult.confidence = (1 - probability) * 100;
        } else {
            plantResult.name = "Muña";
            plantResult.confidence = probability * 100;
        }

        return plantResult;
    }

    private void convertBitmapToByteBuffer(Bitmap bitmap) {
        imgData.rewind();
        int[] pixels = new int[INPUT_SIZE * INPUT_SIZE];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int pixel : pixels) {
            imgData.putFloat(((pixel >> 16) & 0xFF) / 255.0f);
            imgData.putFloat(((pixel >> 8) & 0xFF) / 255.0f);
            imgData.putFloat((pixel & 0xFF) / 255.0f);
        }
    }

    public static class Result {
        public String name;
        public float confidence;
    }
}