package com.tuapp.plantasmedicinales;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.tuapp.plantasmedicinales.controller.IdentificationController;
import com.tuapp.plantasmedicinales.model.Prediction;

public class CameraActivity extends BaseActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 101;
    private static final int CAMERA_PERMISSION_CODE = 102;
    private static final int STORAGE_PERMISSION_CODE = 103;

    private ImageView ivPreview;
    private Button btnCapture, btnIdentify, btnGallery;
    private android.widget.ImageButton btnBack;
    private Bitmap capturedImage;
    private IdentificationController identificationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        identificationController = new IdentificationController(this);

        ivPreview = findViewById(R.id.ivPreview);
        btnCapture = findViewById(R.id.btnCapture);
        btnIdentify = findViewById(R.id.btnIdentify);
        btnGallery = findViewById(R.id.btnGallery);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermission();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStoragePermission();
            }
        });

        btnIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identifyPlant();
            }
        });

        btnIdentify.setEnabled(false);
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

    private void openCamera() {
        // Marcar que vamos a usar una actividad externa (cámara)
        markExternalActivityUsage();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    private void checkStoragePermission() {
        // Para Android 13+ (API 33+) usar READ_MEDIA_IMAGES
        // Para versiones anteriores usar READ_EXTERNAL_STORAGE
        String permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, STORAGE_PERMISSION_CODE);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        // Marcar que vamos a usar una actividad externa (galería)
        markExternalActivityUsage();
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                capturedImage = (Bitmap) data.getExtras().get("data");
                ivPreview.setImageBitmap(capturedImage);
                btnIdentify.setEnabled(true);
            } else if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                try {
                    capturedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ivPreview.setImageBitmap(capturedImage);
                    btnIdentify.setEnabled(true);
                } catch (Exception e) {
                    Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == CAMERA_PERMISSION_CODE) {
                openCamera();
            } else if (requestCode == STORAGE_PERMISSION_CODE) {
                openGallery();
            }
        } else {
            Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void identifyPlant() {
        if (capturedImage == null) {
            Toast.makeText(this, "Por favor capture una imagen primero", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Analizando imagen...", Toast.LENGTH_SHORT).show();
        android.util.Log.d("CameraActivity", "Iniciando identificación de planta...");

        Prediction prediction = identificationController.identifyPlant(capturedImage);

        if (prediction != null) {
            // Mostrar siempre el resultado, incluso con baja confianza
            android.util.Log.d("CameraActivity", "Predicción recibida: " + prediction.getPlantName() + " (" + prediction.getConfidence() + ")");
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("plant_name", prediction.getPlantName());
            intent.putExtra("confidence", prediction.getConfidence());

            // Pasar la imagen capturada a ResultActivity
            intent.putExtra("captured_image", capturedImage);

            startActivity(intent);
        } else {
            android.util.Log.e("CameraActivity", "La predicción es null - modelo no funcionó");
            Toast.makeText(this, "Error: No se pudo procesar la imagen.\n\nRevisa los logs de Android Studio (Logcat) para más detalles.\nBusca: PlantClassifier, CNNService", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        identificationController.cleanup();
    }
}