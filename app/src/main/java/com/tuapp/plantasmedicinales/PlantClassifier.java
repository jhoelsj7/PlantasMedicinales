package com.tuapp.plantasmedicinales;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
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

    private static final String MODEL_PATH = "modelo_plantas_96acc.tflite";
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 224;
    private static final int PIXEL_SIZE = 3;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;
    private static final int MAX_RESULTS = 3;
    private static final float THRESHOLD = 0.1f;

    private Context context;
    private Interpreter interpreter;
    private List<String> labelList;
    private int[] intValues;
    private float[][] outputArray;

    public PlantClassifier(Context context) {
        this.context = context;
        try {
            interpreter = new Interpreter(loadModelFile());
            labelList = loadLabelList();
            intValues = new int[INPUT_SIZE * INPUT_SIZE];
            outputArray = new float[1][labelList.size()];
        } catch (Exception e) {
            e.printStackTrace();
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
            return new ArrayList<>();
        }

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
        ByteBuffer byteBuffer = convertBitmapToByteBuffer(scaledBitmap);

        if (interpreter != null) {
            interpreter.run(byteBuffer, outputArray);
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