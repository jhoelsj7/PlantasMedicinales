package com.tuapp.plantasmedicinales;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.tuapp.plantasmedicinales.controller.IdentificationController;
import com.tuapp.plantasmedicinales.model.Prediction;

public class CameraActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_PERMISSION_CODE = 101;

    private ImageView ivPreview;
    private Button btnCapture, btnIdentify;
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

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermission();
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
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            capturedImage = (Bitmap) data.getExtras().get("data");
            ivPreview.setImageBitmap(capturedImage);
            btnIdentify.setEnabled(true);
        }
    }

    private void identifyPlant() {
        if (capturedImage == null) {
            Toast.makeText(this, "Por favor capture una imagen primero", Toast.LENGTH_SHORT).show();
            return;
        }

        Prediction prediction = identificationController.identifyPlant(capturedImage);

        if (prediction != null) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("plant_name", prediction.getPlantName());
            intent.putExtra("confidence", prediction.getConfidence());
            startActivity(intent);
        } else {
            Toast.makeText(this, "No se pudo identificar la planta", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        identificationController.cleanup();
    }
}