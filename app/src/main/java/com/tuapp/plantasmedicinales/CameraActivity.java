package com.tuapp.plantasmedicinales;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.tuapp.plantasmedicinales.controller.IdentificationController;
import com.tuapp.plantasmedicinales.model.Prediction;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraActivity extends BaseActivity {

    private static final String TAG = "CameraActivity";
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 101;
    private static final int CAMERA_PERMISSION_CODE = 102;
    private static final int STORAGE_PERMISSION_CODE = 103;
    
    // Tamaño máximo de imagen para procesamiento (evita OutOfMemory)
    private static final int MAX_IMAGE_SIZE = 1024;

    private ImageView ivPreview;
    private Button btnCapture, btnIdentify, btnGallery;
    private android.widget.ImageButton btnBack;
    private Bitmap capturedImage;
    private IdentificationController identificationController;
    
    // URI para guardar la imagen de alta resolución
    private Uri photoUri;
    private String currentPhotoPath;

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

    /**
     * Abre la cámara con captura de imagen en ALTA RESOLUCIÓN
     */
    private void openCamera() {
        markExternalActivityUsage();
        
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        
        // Crear archivo temporal para guardar la imagen en alta resolución
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Log.e(TAG, "Error al crear archivo de imagen: " + ex.getMessage());
            Toast.makeText(this, "Error al preparar la cámara", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (photoFile != null) {
            // Obtener URI usando FileProvider (requerido para Android 7+)
            photoUri = FileProvider.getUriForFile(this,
                    getPackageName() + ".fileprovider",
                    photoFile);
            
            // Indicar a la cámara dónde guardar la imagen completa
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            
            Log.d(TAG, "Iniciando cámara con URI: " + photoUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }
    
    /**
     * Crea un archivo temporal para guardar la imagen
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "PLANT_" + timeStamp + "_";
        
        // Usar directorio de caché de la app
        File storageDir = new File(getCacheDir(), "images");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        
        Log.d(TAG, "Archivo de imagen creado: " + currentPhotoPath);
        return image;
    }

    private void checkStoragePermission() {
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
                // Cargar imagen de ALTA RESOLUCIÓN desde el archivo
                loadHighResolutionImage();
            } else if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                // Cargar imagen de la galería en alta resolución
                loadImageFromGallery(data.getData());
            }
        }
    }
    
    /**
     * Carga la imagen de alta resolución capturada por la cámara
     */
    private void loadHighResolutionImage() {
        if (currentPhotoPath == null) {
            Log.e(TAG, "currentPhotoPath es null");
            Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            return;
        }
        
        try {
            Log.d(TAG, "Cargando imagen de alta resolución: " + currentPhotoPath);
            
            // Obtener dimensiones de la imagen sin cargarla en memoria
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(currentPhotoPath, options);
            
            int originalWidth = options.outWidth;
            int originalHeight = options.outHeight;
            Log.d(TAG, "Tamaño original: " + originalWidth + "x" + originalHeight);
            
            // Calcular factor de escala para no exceder MAX_IMAGE_SIZE
            int scaleFactor = 1;
            while ((originalWidth / scaleFactor) > MAX_IMAGE_SIZE || 
                   (originalHeight / scaleFactor) > MAX_IMAGE_SIZE) {
                scaleFactor *= 2;
            }
            
            // Cargar imagen con el factor de escala
            options.inJustDecodeBounds = false;
            options.inSampleSize = scaleFactor;
            capturedImage = BitmapFactory.decodeFile(currentPhotoPath, options);
            
            if (capturedImage != null) {
                // Corregir orientación de la imagen
                capturedImage = rotateImageIfRequired(capturedImage, currentPhotoPath);
                
                Log.d(TAG, "Imagen cargada: " + capturedImage.getWidth() + "x" + capturedImage.getHeight() + 
                      " (escala: 1/" + scaleFactor + ")");
                
                ivPreview.setImageBitmap(capturedImage);
                btnIdentify.setEnabled(true);
                
                Toast.makeText(this, "Imagen HD capturada: " + capturedImage.getWidth() + "x" + capturedImage.getHeight(), 
                        Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Error: capturedImage es null después de decodificar");
                Toast.makeText(this, "Error al procesar la imagen", Toast.LENGTH_SHORT).show();
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error al cargar imagen: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * Carga una imagen de la galería en alta resolución
     */
    private void loadImageFromGallery(Uri imageUri) {
        try {
            Log.d(TAG, "Cargando imagen de galería: " + imageUri);
            
            // Obtener dimensiones sin cargar la imagen
            InputStream input = getContentResolver().openInputStream(imageUri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, options);
            input.close();
            
            int originalWidth = options.outWidth;
            int originalHeight = options.outHeight;
            Log.d(TAG, "Tamaño original galería: " + originalWidth + "x" + originalHeight);
            
            // Calcular factor de escala
            int scaleFactor = 1;
            while ((originalWidth / scaleFactor) > MAX_IMAGE_SIZE || 
                   (originalHeight / scaleFactor) > MAX_IMAGE_SIZE) {
                scaleFactor *= 2;
            }
            
            // Cargar imagen escalada
            input = getContentResolver().openInputStream(imageUri);
            options.inJustDecodeBounds = false;
            options.inSampleSize = scaleFactor;
            capturedImage = BitmapFactory.decodeStream(input, null, options);
            input.close();
            
            if (capturedImage != null) {
                Log.d(TAG, "Imagen de galería cargada: " + capturedImage.getWidth() + "x" + capturedImage.getHeight());
                
                // Guardar la imagen en archivo temporal para poder pasarla a ResultActivity
                saveGalleryImageToTemp();
                
                ivPreview.setImageBitmap(capturedImage);
                btnIdentify.setEnabled(true);
                
                Toast.makeText(this, "Imagen cargada: " + capturedImage.getWidth() + "x" + capturedImage.getHeight(), 
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error al cargar imagen de galería: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * Guarda la imagen de galería en un archivo temporal
     */
    private void saveGalleryImageToTemp() {
        try {
            File storageDir = new File(getCacheDir(), "images");
            if (!storageDir.exists()) {
                storageDir.mkdirs();
            }
            
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            File tempFile = new File(storageDir, "GALLERY_" + timeStamp + ".jpg");
            
            java.io.FileOutputStream out = new java.io.FileOutputStream(tempFile);
            capturedImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            
            currentPhotoPath = tempFile.getAbsolutePath();
            Log.d(TAG, "Imagen de galería guardada en: " + currentPhotoPath);
            
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar imagen de galería: " + e.getMessage());
        }
    }
    
    /**
     * Corrige la orientación de la imagen basándose en los datos EXIF
     */
    private Bitmap rotateImageIfRequired(Bitmap img, String imagePath) {
        try {
            ExifInterface exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(img, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(img, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(img, 270);
                default:
                    return img;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error al leer EXIF: " + e.getMessage());
            return img;
        }
    }
    
    /**
     * Rota una imagen el número de grados especificado
     */
    private Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
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

        Log.d(TAG, "Iniciando identificación con imagen de " + 
              capturedImage.getWidth() + "x" + capturedImage.getHeight());
        Toast.makeText(this, "Analizando imagen HD...", Toast.LENGTH_SHORT).show();

        btnIdentify.setEnabled(false);

        identificationController.identifyPlantAsync(capturedImage, new IdentificationController.IdentificationCallback() {
            @Override
            public void onProgress(String message) {
                Toast.makeText(CameraActivity.this, message, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Progreso: " + message);
            }

            @Override
            public void onComplete(Prediction prediction) {
                btnIdentify.setEnabled(true);

                if (prediction != null) {
                    Log.d(TAG, "Predicción: " + prediction.getPlantName() + 
                          " (" + String.format("%.2f%%", prediction.getConfidence() * 100) + ")");
                    
                    Intent intent = new Intent(CameraActivity.this, ResultActivity.class);
                    intent.putExtra("plant_name", prediction.getPlantName());
                    intent.putExtra("confidence", prediction.getConfidence());
                    
                    // Pasar la RUTA de la imagen en lugar del Bitmap (evita error de tamaño)
                    if (currentPhotoPath != null) {
                        intent.putExtra("image_path", currentPhotoPath);
                    }
                    
                    startActivity(intent);
                } else {
                    Log.e(TAG, "Predicción es null");
                    Toast.makeText(CameraActivity.this, 
                            "Error al identificar la planta. Intenta con otra foto.", 
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        identificationController.cleanup();
        
        // Limpiar archivo temporal si existe
        if (currentPhotoPath != null) {
            File file = new File(currentPhotoPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
