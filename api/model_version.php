<?php
/**
 * Model Version API Endpoint
 */
header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Origin: *');

echo json_encode([
    'version' => '1.0.0',
    'model_name' => 'plantas_medicinales_model.tflite',
    'accuracy' => 96,
    'last_updated' => '2024-01-15',
    'download_url' => 'http://192.168.18.24/plantas_api/public/assets/plantas_medicinales_model.tflite'
]);
