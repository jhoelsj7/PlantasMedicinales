package com.tuapp.plantasmedicinales;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;
import org.tensorflow.lite.Interpreter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PlantClassifier {

    private static final String TAG = "PlantClassifier";
    private static final String MODEL_PATH = "plantas_medicinales_final.tflite";
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 224; // Cambio crítico: El modelo espera 224x224
    private static final int PIXEL_SIZE = 3;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;
    private static final int MAX_RESULTS = 3;
    private static final float THRESHOLD = 0.01f; // Reducido a 1%

    private Context context;
    private Interpreter interpreter;
    private List<String> labelList;
    private int[] intValues;
    private float[][] outputArray;

    public PlantClassifier(Context context) {
        this.context = context;
        try {
            Log.d(TAG, "Iniciando carga del modelo...");
            MappedByteBuffer modelFile = loadModelFile();
            Log.d(TAG, "Archivo del modelo cargado, tamaño: " + modelFile.capacity() + " bytes");

            interpreter = new Interpreter(modelFile);
            Log.d(TAG, "Interpreter creado exitosamente");

            // Verificar el tamaño de entrada esperado por el modelo
            int[] inputShape = interpreter.getInputTensor(0).shape();
            Log.d(TAG, "Forma de entrada del modelo: [" + inputShape[0] + ", " + inputShape[1] + ", " + inputShape[2] + ", " + inputShape[3] + "]");
            Log.d(TAG, "INPUT_SIZE configurado: " + INPUT_SIZE);

            labelList = loadLabelList();
            Log.d(TAG, "Labels cargadas: " + labelList.size());

            intValues = new int[INPUT_SIZE * INPUT_SIZE];
            outputArray = new float[1][labelList.size()];
            Log.d(TAG, "Modelo cargado exitosamente. Plantas: " + labelList.size());
        } catch (Exception e) {
            Log.e(TAG, "Error al cargar modelo: " + e.getMessage());
            e.printStackTrace();
            // Asegurarse de que interpreter queda null si hay error
            interpreter = null;
        }
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(MODEL_PATH);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private List<String> loadLabelList() throws IOException {
        List<String> labels = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(assetManager.open(LABEL_PATH)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                labels.add(line);
            }
        }
        return labels;
    }

    public List<Recognition> recognizeImage(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e(TAG, "Imagen es null");
            return new ArrayList<>();
        }

        if (interpreter == null) {
            Log.e(TAG, "Interpreter es null - modelo no se cargó correctamente");
            return new ArrayList<>();
        }

        if (labelList == null || labelList.isEmpty()) {
            Log.e(TAG, "Lista de labels es null o vacía");
            return new ArrayList<>();
        }

        try {
            Log.d(TAG, "Escalando imagen de " + bitmap.getWidth() + "x" + bitmap.getHeight() + " a " + INPUT_SIZE + "x" + INPUT_SIZE);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

            Log.d(TAG, "Convirtiendo imagen a ByteBuffer...");
            ByteBuffer byteBuffer = convertBitmapToByteBuffer(scaledBitmap);

            Log.d(TAG, "Ejecutando inferencia con el modelo...");
            interpreter.run(byteBuffer, outputArray);

            // Log de todas las confianzas
            Log.d(TAG, "=== Resultados de predicción ===");
            for (int i = 0; i < labelList.size(); i++) {
                Log.d(TAG, labelList.get(i) + ": " + String.format("%.4f", outputArray[0][i]));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error durante la inferencia: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }

        return getSortedResult();
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(
                4 * INPUT_SIZE * INPUT_SIZE * PIXEL_SIZE);
        byteBuffer.order(ByteOrder.nativeOrder());

        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0,
                bitmap.getWidth(), bitmap.getHeight());

        int pixel = 0;
        for (int i = 0; i < INPUT_SIZE; ++i) {
            for (int j = 0; j < INPUT_SIZE; ++j) {
                final int val = intValues[pixel++];
                byteBuffer.putFloat((((val >> 16) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
                byteBuffer.putFloat((((val >> 8) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
                byteBuffer.putFloat(((val & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
            }
        }
        return byteBuffer;
    }

    private List<Recognition> getSortedResult() {
        PriorityQueue<Recognition> pq = new PriorityQueue<>(
                MAX_RESULTS,
                new Comparator<Recognition>() {
                    @Override
                    public int compare(Recognition lhs, Recognition rhs) {
                        return Float.compare(rhs.getConfidence(), lhs.getConfidence());
                    }
                });

        for (int i = 0; i < labelList.size(); ++i) {
            float confidence = outputArray[0][i];
            if (confidence > THRESHOLD) {
                pq.add(new Recognition(
                        String.valueOf(i),
                        labelList.get(i),
                        confidence));
            }
        }

        final ArrayList<Recognition> recognitions = new ArrayList<>();
        int recognitionsSize = Math.min(pq.size(), MAX_RESULTS);
        for (int i = 0; i < recognitionsSize; ++i) {
            recognitions.add(pq.poll());
        }

        Log.d(TAG, "Resultados filtrados: " + recognitions.size() + " de " + labelList.size());
        if (!recognitions.isEmpty()) {
            Log.d(TAG, "Top resultado: " + recognitions.get(0).getTitle() +
                  " con confianza " + String.format("%.2f%%", recognitions.get(0).getConfidence() * 100));
        }

        return recognitions;
    }

    public void close() {
        if (interpreter != null) {
            interpreter.close();
            interpreter = null;
        }
    }

    // Clase interna Recognition
    public class Recognition {
        private final String id;
        private final String title;
        private final float confidence;

        public Recognition(String id, String title, float confidence) {
            this.id = id;
            this.title = title;
            this.confidence = confidence;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public float getConfidence() {
            return confidence;
        }

        @Override
        public String toString() {
            return "Title = " + title + ", Confidence = " + confidence;
        }
    }
}