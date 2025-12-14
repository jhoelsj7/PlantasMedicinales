# 🌿 GUÍA COMPLETA PARA CONSTRUIR EL DASHBOARD

## 📋 ÍNDICE
1. [Requisitos del Sistema](#requisitos)
2. [Estructura de Carpetas](#estructura)
3. [Base de Datos MySQL](#base-de-datos)
4. [Archivos PHP del Backend](#archivos-php)
5. [Configuración](#configuración)
6. [Testing y Verificación](#testing)
7. [Troubleshooting](#troubleshooting)

---

## 🔧 1. REQUISITOS DEL SISTEMA {#requisitos}

### Software Necesario:
- ✅ **XAMPP** o **WAMP** (Apache + MySQL + PHP)
- ✅ **PHP 7.4+** (incluido en XAMPP)
- ✅ **MySQL 8.0+** (incluido en XAMPP)
- ✅ **Editor de código** (Visual Studio Code recomendado)

### Verificar instalación:
```bash
# En terminal/cmd:
php -v          # Debe mostrar PHP 7.4 o superior
mysql --version # Debe mostrar MySQL 8.0 o superior
```

---

## 📁 2. ESTRUCTURA DE CARPETAS {#estructura}

### Crear esta estructura en tu servidor web:

```
C:/xampp/htdocs/plantas_api/
│
├── config.php              # Configuración de BD y constantes
├── helpers.php             # Funciones auxiliares
├── login.php               # Endpoint: POST /login.php
├── plants.php              # Endpoint: GET /plants.php
├── get_plant.php           # Endpoint: GET /get_plant.php?id=X
├── save_prediction.php     # Endpoint: POST /save_prediction.php
├── get_predictions.php     # Endpoint: GET /get_predictions.php
├── model_version.php       # Endpoint: GET /model_version.php
│
├── uploads/                # Imágenes de plantas
│   └── manzanilla.jpg
│   └── eucalipto.jpg
│   └── ...
│
├── uploads/predictions/    # Imágenes de identificaciones
│   └── prediction_123.jpg
│   └── ...
│
├── logs/                   # Archivos de log
│   └── api.log
│   └── db_errors.log
│
└── models/                 # Modelos de TensorFlow Lite
    └── modelo_plantas_96acc.tflite
```

### Crear carpetas:
```bash
cd C:/xampp/htdocs
mkdir plantas_api
cd plantas_api
mkdir uploads uploads/predictions logs models
```

---

## 🗄️ 3. BASE DE DATOS MYSQL {#base-de-datos}

### Paso 1: Abrir phpMyAdmin
```
http://localhost/phpmyadmin
```

### Paso 2: Crear Base de Datos

Ejecuta este SQL completo:

```sql
-- =====================================================
-- CREAR BASE DE DATOS
-- =====================================================
CREATE DATABASE IF NOT EXISTS plantas_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE plantas_db;

-- =====================================================
-- TABLA: users (Usuarios de la app móvil)
-- =====================================================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL COMMENT 'Hash bcrypt',
    email VARCHAR(100),
    full_name VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: sessions (Tokens de autenticación)
-- =====================================================
CREATE TABLE IF NOT EXISTS sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(255) UNIQUE NOT NULL,
    device_info VARCHAR(255),
    ip_address VARCHAR(45),
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_token (token),
    INDEX idx_user_id (user_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: plants (Catálogo de plantas medicinales)
-- =====================================================
CREATE TABLE IF NOT EXISTS plants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    common_name VARCHAR(100) NOT NULL,
    scientific_name VARCHAR(150),
    family VARCHAR(100),
    description TEXT,
    medicinal_uses TEXT,
    preparation TEXT,
    precautions TEXT,
    image_path VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_common_name (common_name),
    INDEX idx_scientific_name (scientific_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: predictions (Historial de identificaciones)
-- =====================================================
CREATE TABLE IF NOT EXISTS predictions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    plant_id INT,
    predicted_name VARCHAR(100),
    confidence DECIMAL(5,4),
    image_path VARCHAR(255),
    is_correct BOOLEAN NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_plant_id (plant_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: model_versions (Versiones del modelo IA)
-- =====================================================
CREATE TABLE IF NOT EXISTS model_versions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    version VARCHAR(50) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    accuracy DECIMAL(5,2),
    file_size INT,
    is_active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_version (version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- INSERTAR DATOS DE PRUEBA
-- =====================================================

-- Usuario de prueba (password: test123)
INSERT INTO users (username, password, email, full_name) VALUES
('testuser', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'test@plantas.com', 'Usuario Prueba'),
('admin', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'admin@plantas.com', 'Administrador');

-- Plantas medicinales
INSERT INTO plants (common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, image_path) VALUES

('Manzanilla', 'Matricaria chamomilla', 'Asteraceae',
'Planta herbácea anual con flores blancas y amarillas muy aromáticas.',
'Propiedades antiinflamatorias, sedantes y digestivas. Útil para insomnio, ansiedad y problemas digestivos.',
'Infusión: 1 cucharada de flores secas por taza de agua hirviendo. Dejar reposar 5-10 minutos.',
'No consumir en exceso durante el embarazo. Puede causar alergias.',
'manzanilla.jpg'),

('Eucalipto', 'Eucalyptus globulus', 'Myrtaceae',
'Árbol perenne con hojas aromáticas ricas en aceites esenciales.',
'Expectorante, antiséptico. Alivia problemas respiratorios como tos y bronquitis.',
'Inhalación: Hervir 5-10 hojas en agua y respirar el vapor por 10 minutos.',
'No consumir aceite esencial sin supervisión. Evitar en niños menores de 6 años.',
'eucalipto.jpg'),

('Aloe Vera', 'Aloe barbadensis', 'Asphodelaceae',
'Planta suculenta con hojas carnosas que contienen gel transparente.',
'Cicatrizante, hidratante, antiinflamatorio. Útil para quemaduras y problemas digestivos.',
'Uso tópico: Aplicar gel fresco sobre la piel. Uso interno: 1-2 cucharadas en ayunas.',
'No consumir la corteza (látex amarillo). Evitar en embarazo.',
'aloe_vera.jpg'),

('Hierba Buena', 'Mentha spicata', 'Lamiaceae',
'Planta aromática con hojas verdes y flores pequeñas.',
'Digestiva, antiespasmódica. Alivia náuseas e indigestión.',
'Infusión: 1 cucharada de hojas por taza. Tomar después de comidas.',
'No usar aceite esencial en bebés. Evitar en reflujo severo.',
'hierba_buena.jpg'),

('Romero', 'Rosmarinus officinalis', 'Lamiaceae',
'Arbusto aromático con hojas aciculares y flores azuladas.',
'Estimulante circulatorio, mejora memoria. Antioxidante y antiinflamatorio.',
'Infusión: 1 cucharadita por taza. Tomar 2-3 tazas al día.',
'Evitar en embarazo. Puede elevar presión arterial.',
'romero.jpg');

-- Modelo IA actual
INSERT INTO model_versions (version, file_name, file_path, accuracy, file_size, is_active) VALUES
('1.0.0', 'modelo_plantas_96acc.tflite', 'models/modelo_plantas_96acc.tflite', 96.00, 2507472, TRUE);
```

---

## 📄 4. ARCHIVOS PHP DEL BACKEND {#archivos-php}

### 📌 Archivo 1: `config.php`

```php
<?php
/**
 * Configuración global del sistema
 */

// Configuración de errores (desarrollo)
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Configuración de base de datos
define('DB_HOST', 'localhost');
define('DB_NAME', 'plantas_db');
define('DB_USER', 'root');
define('DB_PASS', ''); // Dejar vacío para XAMPP por defecto

// Configuración de URLs
define('BASE_URL', 'http://192.168.18.24/plantas_api/');
define('UPLOADS_DIR', __DIR__ . '/uploads/');
define('PREDICTIONS_DIR', __DIR__ . '/uploads/predictions/');
define('MODELS_DIR', __DIR__ . '/models/');
define('LOGS_DIR', __DIR__ . '/logs/');

// Configuración de archivos
define('MAX_FILE_SIZE', 5 * 1024 * 1024); // 5MB
define('ALLOWED_EXTENSIONS', ['jpg', 'jpeg', 'png']);

// Configuración de sesiones
define('TOKEN_EXPIRY_DAYS', 7);

// Headers de seguridad
header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type, Authorization');

// Manejar preflight requests
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit;
}

// Conexión a base de datos
function getDBConnection() {
    try {
        $pdo = new PDO(
            "mysql:host=" . DB_HOST . ";dbname=" . DB_NAME . ";charset=utf8mb4",
            DB_USER,
            DB_PASS,
            [
                PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
                PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
                PDO::ATTR_EMULATE_PREPARES => false
            ]
        );
        return $pdo;
    } catch (PDOException $e) {
        logError("DB Connection Error: " . $e->getMessage());
        jsonResponse(false, "Error de conexión a base de datos");
        exit;
    }
}

// Función para logging
function logError($message) {
    $logFile = LOGS_DIR . 'db_errors.log';
    $timestamp = date('Y-m-d H:i:s');
    file_put_contents($logFile, "[$timestamp] $message\n", FILE_APPEND);
}

// Función para respuestas JSON
function jsonResponse($success, $message, $data = null) {
    $response = [
        'success' => $success,
        'message' => $message
    ];
    if ($data !== null) {
        $response['data'] = $data;
    }
    echo json_encode($response, JSON_UNESCAPED_UNICODE);
    exit;
}
?>
```

---

### 📌 Archivo 2: `helpers.php`

```php
<?php
/**
 * Funciones auxiliares
 */

require_once 'config.php';

/**
 * Validar token de autenticación
 */
function validateToken($token) {
    if (empty($token)) {
        return false;
    }

    $pdo = getDBConnection();
    $stmt = $pdo->prepare("
        SELECT s.*, u.username, u.email
        FROM sessions s
        JOIN users u ON s.user_id = u.id
        WHERE s.token = ? AND s.expires_at > NOW()
    ");
    $stmt->execute([$token]);
    $session = $stmt->fetch();

    return $session ? $session : false;
}

/**
 * Obtener token del header Authorization
 */
function getAuthToken() {
    $headers = getallheaders();
    if (isset($headers['Authorization'])) {
        $parts = explode(' ', $headers['Authorization']);
        if (count($parts) === 2 && $parts[0] === 'Bearer') {
            return $parts[1];
        }
    }
    return null;
}

/**
 * Sanitizar input
 */
function sanitizeInput($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data, ENT_QUOTES, 'UTF-8');
    return $data;
}

/**
 * Validar email
 */
function isValidEmail($email) {
    return filter_var($email, FILTER_VALIDATE_EMAIL) !== false;
}

/**
 * Guardar imagen desde base64
 */
function saveBase64Image($base64String, $directory, $prefix = 'img') {
    // Remover el prefijo "data:image/...;base64,"
    if (preg_match('/^data:image\/(\w+);base64,/', $base64String, $type)) {
        $base64String = substr($base64String, strpos($base64String, ',') + 1);
        $type = strtolower($type[1]);

        if (!in_array($type, ALLOWED_EXTENSIONS)) {
            return ['success' => false, 'message' => 'Tipo de imagen no permitido'];
        }

        $base64String = base64_decode($base64String);

        if ($base64String === false) {
            return ['success' => false, 'message' => 'Error al decodificar imagen'];
        }

        $fileName = $prefix . '_' . uniqid() . '.' . $type;
        $filePath = $directory . $fileName;

        if (file_put_contents($filePath, $base64String)) {
            return ['success' => true, 'fileName' => $fileName];
        } else {
            return ['success' => false, 'message' => 'Error al guardar imagen'];
        }
    }

    return ['success' => false, 'message' => 'Formato de imagen inválido'];
}

/**
 * Log de actividad
 */
function logActivity($message) {
    $logFile = LOGS_DIR . 'api.log';
    $timestamp = date('Y-m-d H:i:s');
    $ip = $_SERVER['REMOTE_ADDR'] ?? 'unknown';
    file_put_contents($logFile, "[$timestamp] [$ip] $message\n", FILE_APPEND);
}
?>
```

---

### 📌 Archivo 3: `login.php`

```php
<?php
/**
 * Endpoint: POST /login.php
 * Autenticación de usuarios
 */

require_once 'config.php';
require_once 'helpers.php';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    jsonResponse(false, 'Método no permitido');
}

// Obtener datos JSON
$input = json_decode(file_get_contents('php://input'), true);

if (!isset($input['username']) || !isset($input['password'])) {
    jsonResponse(false, 'Faltan credenciales');
}

$username = sanitizeInput($input['username']);
$password = $input['password'];

try {
    $pdo = getDBConnection();

    // Buscar usuario
    $stmt = $pdo->prepare("SELECT * FROM users WHERE username = ?");
    $stmt->execute([$username]);
    $user = $stmt->fetch();

    if (!$user || !password_verify($password, $user['password'])) {
        logActivity("Login fallido para usuario: $username");
        jsonResponse(false, 'Usuario o contraseña incorrectos');
    }

    // Generar token
    $token = bin2hex(random_bytes(32));
    $expiresAt = date('Y-m-d H:i:s', strtotime('+' . TOKEN_EXPIRY_DAYS . ' days'));

    // Guardar sesión
    $stmt = $pdo->prepare("
        INSERT INTO sessions (user_id, token, device_info, ip_address, expires_at)
        VALUES (?, ?, ?, ?, ?)
    ");

    $deviceInfo = $_SERVER['HTTP_USER_AGENT'] ?? 'unknown';
    $ipAddress = $_SERVER['REMOTE_ADDR'] ?? 'unknown';

    $stmt->execute([
        $user['id'],
        $token,
        $deviceInfo,
        $ipAddress,
        $expiresAt
    ]);

    // Actualizar last_login
    $stmt = $pdo->prepare("UPDATE users SET last_login = NOW() WHERE id = ?");
    $stmt->execute([$user['id']]);

    logActivity("Login exitoso para usuario: $username");

    jsonResponse(true, 'Login exitoso', [
        'token' => $token,
        'user' => [
            'id' => $user['id'],
            'username' => $user['username'],
            'email' => $user['email'],
            'full_name' => $user['full_name']
        ]
    ]);

} catch (PDOException $e) {
    logError("Login error: " . $e->getMessage());
    jsonResponse(false, 'Error del servidor');
}
?>
```

---

### 📌 Archivo 4: `plants.php`

**IMPORTANTE: Tu app Android espera un ARRAY DIRECTO, no un wrapper.**

```php
<?php
/**
 * Endpoint: GET /plants.php
 * Listar todas las plantas
 * RETORNA: Array directo de plantas (sin wrapper)
 */

require_once 'config.php';
require_once 'helpers.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    http_response_code(405);
    echo json_encode(['error' => 'Método no permitido']);
    exit;
}

try {
    $pdo = getDBConnection();

    // Query base
    $sql = "SELECT * FROM plants WHERE is_active = 1 ORDER BY common_name ASC";

    // Ejecutar query
    $stmt = $pdo->query($sql);
    $plants = $stmt->fetchAll();

    // Agregar URL completa de imagen
    foreach ($plants as &$plant) {
        if (!empty($plant['image_path'])) {
            $plant['imageUrl'] = BASE_URL . 'uploads/' . $plant['image_path'];
        } else {
            $plant['imageUrl'] = null;
        }
    }

    logActivity("Plantas listadas: " . count($plants));

    // RETORNAR ARRAY DIRECTO (sin wrapper)
    header('Content-Type: application/json; charset=utf-8');
    echo json_encode($plants, JSON_UNESCAPED_UNICODE);

} catch (PDOException $e) {
    logError("Plants list error: " . $e->getMessage());
    http_response_code(500);
    echo json_encode(['error' => 'Error del servidor']);
}
?>
```

---

### 📌 Archivo 5: `get_plant.php`

```php
<?php
/**
 * Endpoint: GET /get_plant.php?id=X
 * Obtener detalles de una planta específica
 */

require_once 'config.php';
require_once 'helpers.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    jsonResponse(false, 'Método no permitido');
}

if (!isset($_GET['id'])) {
    jsonResponse(false, 'ID de planta requerido');
}

$plantId = intval($_GET['id']);

try {
    $pdo = getDBConnection();

    $stmt = $pdo->prepare("SELECT * FROM plants WHERE id = ? AND is_active = 1");
    $stmt->execute([$plantId]);
    $plant = $stmt->fetch();

    if (!$plant) {
        jsonResponse(false, 'Planta no encontrada');
    }

    // Agregar URL de imagen
    if (!empty($plant['image_path'])) {
        $plant['imageUrl'] = BASE_URL . 'uploads/' . $plant['image_path'];
    }

    jsonResponse(true, 'Planta encontrada', $plant);

} catch (PDOException $e) {
    logError("Get plant error: " . $e->getMessage());
    jsonResponse(false, 'Error del servidor');
}
?>
```

---

### 📌 Archivo 6: `model_version.php`

```php
<?php
/**
 * Endpoint: GET /model_version.php
 * Obtener versión actual del modelo IA
 */

require_once 'config.php';

try {
    $pdo = getDBConnection();

    $stmt = $pdo->query("SELECT * FROM model_versions WHERE is_active = 1 LIMIT 1");
    $model = $stmt->fetch();

    if (!$model) {
        jsonResponse(false, 'No hay modelo activo');
    }

    jsonResponse(true, 'Modelo activo', [
        'version' => $model['version'],
        'file_name' => $model['file_name'],
        'accuracy' => floatval($model['accuracy']),
        'file_size' => intval($model['file_size']),
        'download_url' => BASE_URL . $model['file_path']
    ]);

} catch (PDOException $e) {
    logError("Model version error: " . $e->getMessage());
    jsonResponse(false, 'Error del servidor');
}
?>
```

---

## ⚙️ 5. CONFIGURACIÓN {#configuración}

### Paso 1: Editar `config.php`

Cambia la IP a la de tu servidor:

```php
// Línea 11 de config.php
define('BASE_URL', 'http://TU_IP_AQUI/plantas_api/');

// Ejemplo:
define('BASE_URL', 'http://192.168.18.24/plantas_api/');
```

### Paso 2: Permisos de Carpetas (Linux/Mac)

```bash
chmod 755 uploads uploads/predictions logs models
chmod 644 *.php
```

### Paso 3: Agregar Imágenes de Plantas

Coloca imágenes en `uploads/`:
- `manzanilla.jpg`
- `eucalipto.jpg`
- `aloe_vera.jpg`
- `hierba_buena.jpg`
- `romero.jpg`

---

## 🧪 6. TESTING Y VERIFICACIÓN {#testing}

### Test 1: Verificar Conexión a BD

Crea `test_db.php`:

```php
<?php
require_once 'config.php';
$pdo = getDBConnection();
echo "Conexión exitosa!";
?>
```

Accede: `http://192.168.18.24/plantas_api/test_db.php`

---

### Test 2: Login

**Con cURL:**
```bash
curl -X POST http://192.168.18.24/plantas_api/login.php \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"test123"}'
```

**Respuesta esperada:**
```json
{
  "success": true,
  "message": "Login exitoso",
  "data": {
    "token": "abc123...",
    "user": {...}
  }
}
```

---

### Test 3: Listar Plantas

**Con navegador:**
```
http://192.168.18.24/plantas_api/plants.php
```

**Respuesta esperada (ARRAY DIRECTO):**
```json
[
  {
    "id": 1,
    "common_name": "Manzanilla",
    "scientific_name": "Matricaria chamomilla",
    "family": "Asteraceae",
    "description": "...",
    "medicinal_uses": "...",
    "preparation": "...",
    "precautions": "...",
    "imageUrl": "http://192.168.18.24/plantas_api/uploads/manzanilla.jpg"
  },
  ...
]
```

---

### Test 4: Obtener Planta por ID

```
http://192.168.18.24/plantas_api/get_plant.php?id=1
```

---

## 🔍 7. TROUBLESHOOTING {#troubleshooting}

### Problema: "Error de conexión a BD"

**Solución:**
1. Verifica que MySQL esté corriendo
2. Verifica credenciales en `config.php`
3. Revisa `logs/db_errors.log`

---

### Problema: "CORS error" en Android

**Solución:**
Verifica que `config.php` tenga:
```php
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST');
header('Access-Control-Allow-Headers: Content-Type, Authorization');
```

---

### Problema: Imágenes no se muestran

**Solución:**
1. Verifica que las imágenes existan en `uploads/`
2. Verifica permisos de carpeta (755)
3. Verifica que `BASE_URL` sea correcta

---

## ✅ CHECKLIST FINAL

- [ ] XAMPP instalado y corriendo
- [ ] Base de datos `plantas_db` creada
- [ ] Tablas creadas con script SQL
- [ ] Carpetas creadas (uploads, logs, models)
- [ ] Archivos PHP en `C:/xampp/htdocs/plantas_api/`
- [ ] IP configurada en `config.php`
- [ ] Imágenes subidas a `uploads/`
- [ ] Test de login funciona
- [ ] Test de plantas funciona
- [ ] App Android apunta a la IP correcta

---

## 🎯 RESUMEN

### URLs de tu API:

```
POST   http://192.168.18.24/plantas_api/login.php
GET    http://192.168.18.24/plantas_api/plants.php
GET    http://192.168.18.24/plantas_api/get_plant.php?id=1
GET    http://192.168.18.24/plantas_api/model_version.php
```

### Credenciales de prueba:

**Usuario app:**
- Username: `testuser`
- Password: `test123`

**Admin:**
- Username: `admin`
- Password: `test123`

---

**¡Tu dashboard está listo para funcionar con tu app Android! 🚀**
