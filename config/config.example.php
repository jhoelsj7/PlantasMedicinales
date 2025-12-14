<?php
/**
 * Configuration File
 * Copy this file to config.php and update with your credentials
 */

// Database Configuration
define('DB_HOST', 'localhost');
define('DB_NAME', 'plantas_db');
define('DB_USER', 'your_username');
define('DB_PASS', 'your_password');

// API Configuration
define('BASE_URL', 'http://localhost/plantas_api/');
define('API_VERSION', '1.0.0');

// Paths
define('UPLOAD_PATH', __DIR__ . '/../public/uploads/');
define('UPLOAD_URL', BASE_URL . 'public/uploads/');

// Upload Settings
define('MAX_UPLOAD_SIZE', 5 * 1024 * 1024); // 5MB
define('ALLOWED_EXTENSIONS', ['jpg', 'jpeg', 'png']);

// Timezone
date_default_timezone_set('America/Mexico_City');
