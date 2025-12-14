# 🔧 Configuración del Backend API

Esta guía explica cómo configurar el backend PHP para la aplicación de Plantas Medicinales.

## 📋 Requisitos del Servidor

- PHP 7.4 o superior
- MySQL 5.7 o superior
- Apache con mod_rewrite habilitado
- Conexión a Internet (para desarrollo)

## 🗄️ Estructura de la Base de Datos

### Tabla: plants

```sql
CREATE DATABASE plantas_medicinales CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE plantas_medicinales;

CREATE TABLE plants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    common_name VARCHAR(100) NOT NULL,
    scientific_name VARCHAR(150),
    family VARCHAR(100),
    description TEXT,
    medicinal_uses TEXT,
    preparation TEXT,
    precautions TEXT,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_common_name (common_name),
    INDEX idx_scientific_name (scientific_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### Tabla: users

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## 📁 Estructura de Archivos PHP

```
plantas_api/
├── config/
│   └── database.php
├── models/
│   ├── Plant.php
│   └── User.php
├── login.php
├── plants.php
└── .htaccess
```

## 📝 Archivos PHP de Ejemplo

### 1. config/database.php

```php
<?php
class Database {
    private $host = "localhost";
    private $db_name = "plantas_medicinales";
    private $username = "root";
    private $password = "";
    public $conn;

    public function getConnection() {
        $this->conn = null;
        try {
            $this->conn = new PDO(
                "mysql:host=" . $this->host . ";dbname=" . $this->db_name,
                $this->username,
                $this->password
            );
            $this->conn->exec("set names utf8mb4");
            $this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch(PDOException $exception) {
            echo "Error de conexión: " . $exception->getMessage();
        }
        return $this->conn;
    }
}
?>
```

### 2. plants.php

```php
<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET, POST");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once 'config/database.php';

$database = new Database();
$db = $database->getConnection();

$query = "SELECT
    id,
    common_name,
    scientific_name,
    family,
    description,
    medicinal_uses,
    preparation,
    precautions,
    image_url
    FROM plants
    ORDER BY common_name ASC";

$stmt = $db->prepare($query);
$stmt->execute();

$plants_arr = array();

while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
    extract($row);

    $plant_item = array(
        "id" => $id,
        "common_name" => $common_name,
        "scientific_name" => $scientific_name,
        "family" => $family,
        "description" => $description,
        "medicinal_uses" => $medicinal_uses,
        "preparation" => $preparation,
        "precautions" => $precautions,
        "imageUrl" => $image_url
    );

    array_push($plants_arr, $plant_item);
}

http_response_code(200);
echo json_encode($plants_arr);
?>
```

### 3. login.php

```php
<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once 'config/database.php';

$database = new Database();
$db = $database->getConnection();

// Obtener datos enviados
$data = json_decode(file_get_contents("php://input"));

if (!empty($data->username) && !empty($data->password)) {
    $username = $data->username;
    $password = $data->password;

    // Verificar credenciales (en producción usar password_hash)
    if ($username === "admin" && $password === "admin123") {
        $response = array(
            "success" => true,
            "message" => "Login exitoso",
            "token" => "dummy_token_" . time(),
            "user" => array(
                "id" => 1,
                "username" => $username,
                "email" => "admin@example.com"
            )
        );
        http_response_code(200);
    } else {
        $response = array(
            "success" => false,
            "message" => "Credenciales incorrectas"
        );
        http_response_code(401);
    }

    echo json_encode($response);
} else {
    $response = array(
        "success" => false,
        "message" => "Datos incompletos"
    );
    http_response_code(400);
    echo json_encode($response);
}
?>
```

### 4. .htaccess

```apache
RewriteEngine On
RewriteCond %{REQUEST_FILENAME} !-f
RewriteCond %{REQUEST_FILENAME} !-d
RewriteRule ^ index.php [QSA,L]

# Habilitar CORS
Header set Access-Control-Allow-Origin "*"
Header set Access-Control-Allow-Methods "GET, POST, PUT, DELETE, OPTIONS"
Header set Access-Control-Allow-Headers "Content-Type, Authorization"
```

## 🌱 Datos de Ejemplo para Insertar

```sql
INSERT INTO plants (common_name, scientific_name, family, description, medicinal_uses, preparation, precautions) VALUES
('Muña', 'Minthostachys mollis', 'Lamiaceae', 'Arbusto aromático andino con hojas pequeñas',
 'Digestiva, carminativa, alivia el mal de altura, antiparasitaria',
 'Infusión: 1 cucharada de hojas por taza de agua hirviendo',
 'No usar durante el embarazo'),

('Astromeria', 'Alstroemeria aurantiaca', 'Alstroemeriaceae', 'Planta ornamental y medicinal',
 'Antiinflamatoria, cicatrizante, ayuda en problemas respiratorios',
 'Cataplasma de hojas frescas machacadas',
 'Uso externo principalmente'),

('Manzanilla', 'Matricaria chamomilla', 'Asteraceae', 'Hierba aromática con flores blancas',
 'Antiinflamatoria, calmante, digestiva, ayuda con el insomnio',
 'Infusión: 1-2 cucharaditas por taza, 3 veces al día',
 'Puede causar reacciones alérgicas en personas sensibles'),

('Aloe Vera', 'Aloe barbadensis', 'Asphodelaceae', 'Planta suculenta con gel medicinal',
 'Cicatrizante, hidratante, antiinflamatoria, para quemaduras',
 'Gel fresco aplicado directamente sobre la piel',
 'No consumir el látex amarillo, puede ser tóxico'),

('Menta', 'Mentha piperita', 'Lamiaceae', 'Planta aromática refrescante',
 'Digestiva, descongestionante, alivia náuseas y dolores de cabeza',
 'Infusión: hojas frescas o secas, 2-3 veces al día',
 'Evitar en personas con reflujo gastroesofágico');
```

## 🔧 Configuración en la App Android

1. Encontrar tu IP local:
   - Windows: `ipconfig` (buscar IPv4)
   - Linux/Mac: `ifconfig` o `ip addr`

2. Editar `RetrofitClient.java`:
```java
private static final String BASE_URL = "http://192.168.X.X/plantas_api/";
```

3. Asegurarse que el dispositivo/emulador esté en la misma red WiFi

## 🧪 Probar los Endpoints

### Con cURL:

```bash
# Obtener plantas
curl http://localhost/plantas_api/plants.php

# Login
curl -X POST http://localhost/plantas_api/login.php \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Con Postman:

1. GET `http://localhost/plantas_api/plants.php`
2. POST `http://localhost/plantas_api/login.php`
   - Body (JSON): `{"username":"admin","password":"admin123"}`

## 🛡️ Seguridad para Producción

1. **Usar HTTPS**
2. **Hash de contraseñas con bcrypt**:
```php
$hashed = password_hash($password, PASSWORD_BCRYPT);
password_verify($password, $hashed);
```

3. **Validar y sanitizar inputs**
4. **Implementar JWT para autenticación**
5. **Rate limiting**
6. **Prepared statements** (ya implementado)

## 🚨 Troubleshooting

### Error: "Connection refused"
- Verificar que Apache esté corriendo
- Verificar firewall
- Verificar que la IP sea correcta

### Error: "Access denied for user"
- Verificar credenciales de MySQL en database.php
- Asegurar que el usuario tenga permisos

### Error: "No data received"
- Verificar que el JSON sea válido
- Verificar headers CORS
- Revisar logs de PHP

## 📚 Referencias

- [PHP PDO](https://www.php.net/manual/es/book.pdo.php)
- [REST API Best Practices](https://restfulapi.net/)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

Para más información, consultar el README principal del proyecto.
