# 📱 Documentación API - App Plantas Medicinales

## 🌐 Configuración Actual

**URL Base de la API:**
```
http://192.168.18.26/plantas_api/
```

**Timeout de Conexión:**
- Connect: 30 segundos
- Read: 30 segundos
- Write: 30 segundos

---

## 🔄 FLUJO COMPLETO DE LA APLICACIÓN

### 1️⃣ **Flujo de Autenticación**

```
┌─────────────┐
│ LoginActivity│
└──────┬──────┘
       │
       │ Usuario ingresa credenciales
       ▼
┌──────────────────┐
│  POST /login.php │
└──────┬───────────┘
       │
       ▼
┌─────────────────────────────┐
│ Dashboard responde JSON     │
│ {                           │
│   "success": true,          │
│   "message": "Login exitoso"│
│   "token": "abc123xyz"      │
│ }                           │
└──────┬──────────────────────┘
       │
       ▼
┌──────────────────────┐
│ Guarda en SharedPrefs│ → MainActivity (Home)
└──────────────────────┘
```

### 2️⃣ **Flujo de Sincronización de Plantas**

```
┌──────────────┐
│ MainActivity │
│ o cualquier  │
│ Activity     │
└──────┬───────┘
       │
       │ SyncController verifica internet
       ▼
┌─────────────────────┐
│ GET /plants.php     │
└──────┬──────────────┘
       │
       ▼
┌───────────────────────────────┐
│ Dashboard responde JSON array │
│ [                             │
│   {                           │
│     "id": 1,                  │
│     "common_name": "Manzanilla"│
│     "scientific_name": "..."  │
│     "family": "Asteraceae"    │
│     "description": "..."      │
│     "medicinal_uses": "..."   │
│     "preparation": "..."      │
│     "precautions": "..."      │
│     "imageUrl": "http://..."  │
│   },                          │
│   ...                         │
│ ]                             │
└──────┬────────────────────────┘
       │
       ▼
┌────────────────────────┐
│ Guarda en SQLite local │
│ Base de datos: plantas_db│
└────────────────────────┘
```

### 3️⃣ **Flujo de Identificación de Planta (IA)**

```
┌──────────────┐
│CameraActivity│
└──────┬───────┘
       │
       │ Usuario toma/selecciona foto
       ▼
┌──────────────────────────┐
│ IdentificationController │
└──────┬───────────────────┘
       │
       ▼
┌───────────────┐
│  CNNService   │ → Usa TensorFlow Lite LOCAL
└──────┬────────┘   (NO requiere internet)
       │
       ▼
┌─────────────────┐
│ PlantClassifier │ → modelo_plantas_96acc.tflite
└──────┬──────────┘
       │
       │ Predicción: "Manzanilla" (95% confianza)
       ▼
┌──────────────────┐
│  ResultActivity  │ → Busca info en SQLite local
└──────────────────┘
```

### 4️⃣ **Flujo de Búsqueda de Plantas**

```
┌──────────────┐
│SearchActivity│
└──────┬───────┘
       │
       │ Usuario busca "manzanilla"
       ▼
┌────────────────────────┐
│ PlantService           │
│ searchPlants(query)    │
└──────┬─────────────────┘
       │
       ▼
┌────────────────────────┐
│ Busca en SQLite local  │
│ SELECT * FROM plants   │
│ WHERE name LIKE '%...' │
└──────┬─────────────────┘
       │
       ▼
┌──────────────────┐
│ PlantListActivity│ → Muestra resultados
└──────────────────┘
```

---

## 📋 ENDPOINTS QUE NECESITA TU DASHBOARD

### **1. POST /login.php**

**Request:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Response (Éxito):**
```json
{
  "success": true,
  "message": "Login exitoso",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response (Error):**
```json
{
  "success": false,
  "message": "Usuario o contraseña incorrectos",
  "token": null
}
```

**Códigos HTTP:**
- `200 OK` - Login exitoso
- `401 Unauthorized` - Credenciales incorrectas
- `500 Internal Server Error` - Error del servidor

---

### **2. GET /plants.php**

**Request:**
```
GET http://192.168.18.26/plantas_api/plants.php
```

**Headers (Opcional):**
```
Authorization: Bearer {token}
```

**Response:**
```json
[
  {
    "id": 1,
    "common_name": "Manzanilla",
    "scientific_name": "Matricaria chamomilla",
    "family": "Asteraceae",
    "description": "Planta herbácea anual de la familia de las asteráceas...",
    "medicinal_uses": "Propiedades antiinflamatorias, sedantes y digestivas...",
    "preparation": "Infusión: 1 cucharada de flores secas por taza de agua hirviendo...",
    "precautions": "No consumir en exceso durante el embarazo...",
    "imageUrl": "http://192.168.18.26/plantas_api/images/manzanilla.jpg"
  },
  {
    "id": 2,
    "common_name": "Eucalipto",
    "scientific_name": "Eucalyptus globulus",
    "family": "Myrtaceae",
    "description": "Árbol perenne de gran tamaño...",
    "medicinal_uses": "Expectorante, antiséptico, alivia problemas respiratorios...",
    "preparation": "Inhalación: Hervir hojas en agua y respirar el vapor...",
    "precautions": "No consumir aceite esencial internamente sin supervisión...",
    "imageUrl": "http://192.168.18.26/plantas_api/images/eucalipto.jpg"
  }
]
```

**Códigos HTTP:**
- `200 OK` - Plantas obtenidas exitosamente
- `401 Unauthorized` - Token inválido (si usas autenticación)
- `500 Internal Server Error` - Error del servidor

---

## 🗄️ ESTRUCTURA DE BASE DE DATOS (MySQL para Dashboard)

### **Tabla: users**
```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,  -- Hash (bcrypt recomendado)
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### **Tabla: plants**
```sql
CREATE TABLE plants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    common_name VARCHAR(100) NOT NULL,
    scientific_name VARCHAR(150),
    family VARCHAR(100),
    description TEXT,
    medicinal_uses TEXT,
    preparation TEXT,
    precautions TEXT,
    imageUrl VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_common_name (common_name),
    INDEX idx_scientific_name (scientific_name)
);
```

### **Tabla: sessions (Opcional - para tokens)**
```sql
CREATE TABLE sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(255) UNIQUE NOT NULL,
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

---

## 🔐 EJEMPLO DE CÓDIGO PHP PARA EL DASHBOARD

### **login.php**
```php
<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Content-Type');

// Conexión a base de datos
$host = 'localhost';
$dbname = 'plantas_db';
$username = 'root';
$password = '';

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Obtener datos JSON
    $input = json_decode(file_get_contents('php://input'), true);

    if (!isset($input['username']) || !isset($input['password'])) {
        echo json_encode([
            'success' => false,
            'message' => 'Faltan credenciales',
            'token' => null
        ]);
        exit;
    }

    $username = $input['username'];
    $password = $input['password'];

    // Buscar usuario
    $stmt = $pdo->prepare("SELECT * FROM users WHERE username = ?");
    $stmt->execute([$username]);
    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($user && password_verify($password, $user['password'])) {
        // Generar token
        $token = bin2hex(random_bytes(32));

        // Guardar sesión (opcional)
        $stmt = $pdo->prepare("INSERT INTO sessions (user_id, token, expires_at) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 7 DAY))");
        $stmt->execute([$user['id'], $token]);

        echo json_encode([
            'success' => true,
            'message' => 'Login exitoso',
            'token' => $token
        ]);
    } else {
        echo json_encode([
            'success' => false,
            'message' => 'Usuario o contraseña incorrectos',
            'token' => null
        ]);
    }

} catch (PDOException $e) {
    echo json_encode([
        'success' => false,
        'message' => 'Error del servidor: ' . $e->getMessage(),
        'token' => null
    ]);
}
?>
```

### **plants.php**
```php
<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET');
header('Access-Control-Allow-Headers: Content-Type, Authorization');

// Conexión a base de datos
$host = 'localhost';
$dbname = 'plantas_db';
$username = 'root';
$password = '';

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Obtener todas las plantas
    $stmt = $pdo->query("SELECT * FROM plants ORDER BY common_name ASC");
    $plants = $stmt->fetchAll(PDO::FETCH_ASSOC);

    echo json_encode($plants);

} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode([
        'error' => 'Error del servidor',
        'message' => $e->getMessage()
    ]);
}
?>
```

---

## 🧪 TESTING DE LA API

### **Usando cURL (Consola)**

**Login:**
```bash
curl -X POST http://192.168.18.26/plantas_api/login.php \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

**Obtener plantas:**
```bash
curl -X GET http://192.168.18.26/plantas_api/plants.php
```

### **Usando Postman:**

1. **Login:**
   - Method: `POST`
   - URL: `http://192.168.18.26/plantas_api/login.php`
   - Body (raw JSON):
     ```json
     {
       "username": "admin",
       "password": "admin123"
     }
     ```

2. **Get Plants:**
   - Method: `GET`
   - URL: `http://192.168.18.26/plantas_api/plants.php`

---

## 📊 DATOS DE EJEMPLO PARA INSERTAR

```sql
-- Insertar usuario de prueba
INSERT INTO users (username, password, email) VALUES
('admin', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'admin@plantas.com');
-- Password: admin123

-- Insertar plantas de ejemplo
INSERT INTO plants (common_name, scientific_name, family, description, medicinal_uses, preparation, precautions, imageUrl) VALUES
('Manzanilla', 'Matricaria chamomilla', 'Asteraceae',
 'Planta herbácea anual de la familia de las asteráceas, con flores blancas y amarillas muy aromáticas.',
 'Propiedades antiinflamatorias, sedantes y digestivas. Útil para tratar insomnio, ansiedad, problemas digestivos y dolores menstruales.',
 'Infusión: 1 cucharada de flores secas por taza de agua hirviendo. Dejar reposar 5-10 minutos y colar.',
 'No consumir en exceso durante el embarazo. Puede causar alergias en personas sensibles a las asteráceas.',
 'http://192.168.18.26/plantas_api/images/manzanilla.jpg'),

('Eucalipto', 'Eucalyptus globulus', 'Myrtaceae',
 'Árbol perenne de gran tamaño originario de Australia, con hojas aromáticas ricas en aceites esenciales.',
 'Expectorante, antiséptico, alivia problemas respiratorios como tos, bronquitis y sinusitis. Propiedades antibacterianas.',
 'Inhalación: Hervir 5-10 hojas en agua y respirar el vapor por 10 minutos. Infusión: 1-2 hojas por taza, no más de 2 tazas al día.',
 'No consumir aceite esencial internamente sin supervisión médica. Evitar en niños menores de 6 años.',
 'http://192.168.18.26/plantas_api/images/eucalipto.jpg'),

('Aloe Vera', 'Aloe barbadensis', 'Asphodelaceae',
 'Planta suculenta con hojas carnosas que contienen un gel transparente rico en nutrientes.',
 'Cicatrizante, hidratante, antiinflamatorio. Útil para quemaduras, heridas, problemas digestivos y cuidado de la piel.',
 'Uso tópico: Aplicar gel fresco directamente sobre la piel. Uso interno: 1-2 cucharadas de gel puro en ayunas (consultar médico).',
 'No consumir la corteza (látex amarillo) ya que es laxante fuerte. Evitar uso interno durante embarazo y lactancia.',
 'http://192.168.18.26/plantas_api/images/aloe_vera.jpg');
```

---

## 📱 CONFIGURACIÓN EN LA APP

Para cambiar la URL del servidor en la app, edita:

**Archivo:** `app/src/main/java/com/tuapp/plantasmedicinales/RetrofitClient.java`

```java
// Línea 12
private static final String BASE_URL = "http://TU_IP_AQUI/plantas_api/";
```

**Ejemplos:**
- Servidor local (WiFi): `http://192.168.1.100/plantas_api/`
- Servidor remoto: `https://tudominio.com/api/`
- Localhost (emulador): `http://10.0.2.2/plantas_api/`

---

## ✅ CHECKLIST PARA TU DASHBOARD

- [ ] Servidor web (Apache/Nginx) corriendo
- [ ] PHP 7.4+ instalado
- [ ] MySQL 8.0+ instalado
- [ ] Base de datos `plantas_db` creada
- [ ] Tablas `users`, `plants`, `sessions` creadas
- [ ] Usuario admin insertado (password hasheado)
- [ ] Plantas de ejemplo insertadas
- [ ] Archivos `login.php` y `plants.php` en `/plantas_api/`
- [ ] CORS habilitado (headers en PHP)
- [ ] Permisos de carpeta correctos (755)
- [ ] Firewall permite conexiones en puerto 80/443
- [ ] App apunta a la IP correcta en `RetrofitClient.java`

---

## 🚀 ENDPOINTS ADICIONALES RECOMENDADOS (Futuro)

```
POST /plantas_api/register.php          # Registro de usuarios
GET  /plantas_api/plant/{id}            # Obtener planta por ID
POST /plantas_api/plant                 # Crear nueva planta (admin)
PUT  /plantas_api/plant/{id}            # Actualizar planta (admin)
DELETE /plantas_api/plant/{id}          # Eliminar planta (admin)
POST /plantas_api/identification        # Guardar historial de identificaciones
GET  /plantas_api/statistics            # Estadísticas de uso
POST /plantas_api/upload_model          # Actualizar modelo IA
GET  /plantas_api/model_version         # Obtener versión del modelo
```

---

## 📞 CONTACTO Y SOPORTE

**Desarrollador:** [Tu Nombre]
**Email:** [Tu Email]
**Fecha:** Octubre 2025
**Versión App:** 1.0
**Versión API:** 1.0

---

**¡Éxito con tu proyecto de tesis! 🌿📱**
