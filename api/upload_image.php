<?php
/**
 * Upload Image API Endpoint
 */
require_once __DIR__ . '/../controllers/UploadController.php';

$controller = new UploadController();
$controller->uploadImage();
