package com.tuapp.plantasmedicinales;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int INPUT_SIZE = 128;

    private ImageView imageView;
    private TextView resultText;
    private ProgressBar progressBar;
    private Button btnCapture, btnIdentify;
    private Bitmap capturedImage;

    private Interpreter tflite;
    private List<String> labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cargar modelo y etiquetas
        try {
            tflite = new Interpreter(loadModelFile());
            labels = loadLabels();
            Toast.makeText(this, "Modelo cargado correctamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            String errorMsg = "Error: " + e.getClass().getSimpleName() + " - " + e.getMessage();
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
            android.util.Log.e("CameraActivity", "Error completo:", e);
            e.printStackTrace();
        }

        // Crear interfaz
        ScrollView scrollView = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);

        // Título
        TextView title = new TextView(this);
        title.setText("Identificador de Plantas con IA");
        title.setTextSize(24);
        title.setPadding(0, 0, 0, 30);
        layout.addView(title);

        // ImageView
        imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 600));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundColor(0xFFEEEEEE);
        layout.addView(imageView);

        // Botón Capturar
        btnCapture = new Button(this);
        btnCapture.setText("Tomar Foto");
        btnCapture.setOnClickListener(v -> checkCameraPermission());
        layout.addView(btnCapture);

        // Botón Identificar
        btnIdentify = new Button(this);
        btnIdentify.setText("Identificar Planta");
        btnIdentify.setEnabled(false);
        btnIdentify.setOnClickListener(v -> identifyPlant());
        layout.addView(btnIdentify);

        // ProgressBar
        progressBar = new ProgressBar(this);
        progressBar.setVisibility(View.GONE);
        layout.addView(progressBar);

        // TextView resultados
        resultText = new TextView(this);
        resultText.setTextSize(16);
        resultText.setPadding(0, 30, 0, 0);
        layout.addView(resultText);

        scrollView.addView(layout);
        setContentView(scrollView);
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = null;
        FileInputStream inputStream = null;
        try {
            fileDescriptor = getAssets().openFd("modelo_plantas_96acc.tflite");
            inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
            FileChannel fileChannel = inputStream.getChannel();
            long startOffset = fileDescriptor.getStartOffset();
            long declaredLength = fileDescriptor.getDeclaredLength();
            MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
            return buffer;
        } catch (IOException e) {
            android.util.Log.e("CameraActivity", "Error loading model", e);
            throw new IOException("No se pudo cargar modelo: " + e.getMessage());
        } finally {
            if (inputStream != null) {
                try { inputStream.close(); } catch (IOException e) { }
            }
            if (fileDescriptor != null) {
                try { fileDescriptor.close(); } catch (IOException e) { }
            }
        }
    }

    private List<String> loadLabels() throws IOException {
        List<String> labels = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getAssets().open("labels.txt")));
        String line;
        while ((line = reader.readLine()) != null) {
            labels.add(line);
        }
        reader.close();
        return labels;
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            capturedImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(capturedImage);
            btnIdentify.setEnabled(true);
            resultText.setText("");
        }
    }

    private void identifyPlant() {
        if (capturedImage == null) {
            Toast.makeText(this, "Primero toma una foto", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tflite == null) {
            Toast.makeText(this, "Error: Modelo no cargado", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnIdentify.setEnabled(false);
        resultText.setText("Analizando imagen...");

        new Thread(() -> {
            try {
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(capturedImage, INPUT_SIZE, INPUT_SIZE, true);
                ByteBuffer inputBuffer = convertBitmapToByteBuffer(resizedBitmap);

                float[][] output = new float[1][1];
                tflite.run(inputBuffer, output);

                float prediction = output[0][0];
                String plantName;
                float confidence;

                if (prediction < 0.5) {
                    plantName = labels.get(0);
                    confidence = (1 - prediction) * 100;
                } else {
                    plantName = labels.get(1);
                    confidence = prediction * 100;
                }

                String finalResult = String.format(
                        "RESULTADO:\n\n" +
                                "Planta: %s\n" +
                                "Confianza: %.1f%%\n\n" +
                                "Modelo entrenado con ~96%% de precision",
                        plantName, confidence
                );

                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnIdentify.setEnabled(true);
                    resultText.setText(finalResult);
                });

            } catch (Exception e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnIdentify.setEnabled(true);
                    resultText.setText("Error: " + e.getMessage());
                });
                e.printStackTrace();
            }
        }).start();
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * 3);
        byteBuffer.order(ByteOrder.nativeOrder());

        int[] intValues = new int[INPUT_SIZE * INPUT_SIZE];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        int pixel = 0;
        for (int i = 0; i < INPUT_SIZE; ++i) {
            for (int j = 0; j < INPUT_SIZE; ++j) {
                final int val = intValues[pixel++];
                byteBuffer.putFloat(((val >> 16) & 0xFF) / 255.0f);
                byteBuffer.putFloat(((val >> 8) & 0xFF) / 255.0f);
                byteBuffer.putFloat((val & 0xFF) / 255.0f);
            }
        }

        return byteBuffer;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tflite != null) {
            tflite.close();
        }
    }
}