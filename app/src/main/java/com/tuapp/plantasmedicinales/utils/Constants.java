package com.tuapp.plantasmedicinales.utils;

public class Constants {
    // API Configuration (actualizar si cambia la IP)
    public static final String BASE_URL = "http://192.168.18.35/plantas_api/";
    public static final String API_URL = BASE_URL + "api/";

    // API Endpoints
    public static final String ENDPOINT_PLANTS = "plants.php";
    public static final String ENDPOINT_LOGIN = "login.php";
    public static final String ENDPOINT_PREDICTIONS = "predictions.php";
    public static final String ENDPOINT_GET_PLANT = "get_plant.php";
    public static final String ENDPOINT_SEARCH_PLANTS = "search_plants.php";
    public static final String ENDPOINT_MODEL_VERSION = "model_version.php";

    // CNN Model Configuration (TFLite)
    public static final String MODEL_PATH = "plantas_medicinales_final.tflite";
    public static final String LABEL_PATH = "labels.txt";
    public static final int INPUT_SIZE = 224;
    public static final float CONFIDENCE_THRESHOLD = 0.6f;

    // Database
    public static final String DATABASE_NAME = "plantas_db";
    public static final int DATABASE_VERSION = 1;

    // Shared Preferences
    public static final String PREF_AUTH = "PlantasAuth";
    public static final String PREF_KEY_TOKEN = "token";
    public static final String PREF_KEY_USER = "user";
    public static final String PREF_KEY_IS_LOGGED = "isLogged";

    // Request Codes
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int GALLERY_REQUEST_CODE = 101;
    public static final int CAMERA_PERMISSION_CODE = 102;
    public static final int STORAGE_PERMISSION_CODE = 103;

    // Intent Extras
    public static final String EXTRA_PLANT_ID = "plant_id";
    public static final String EXTRA_PLANT_NAME = "plant_name";
    public static final String EXTRA_PLANT_SCIENTIFIC = "plant_scientific";
    public static final String EXTRA_CONFIDENCE = "confidence";
    public static final String EXTRA_IMAGE_URI = "image_uri";

    // Default Login Credentials (for testing)
    public static final String DEFAULT_USERNAME = "admin";
    public static final String DEFAULT_PASSWORD = "admin123";
}
