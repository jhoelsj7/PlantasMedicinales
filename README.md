# 🌿 Dashboard Backend - Plantas Medicinales

## 📋 PASOS DE INSTALACIÓN

### 1️⃣ Mover carpeta al servidor web

**IMPORTANTE:** Debes mover esta carpeta `plantas_api` a la carpeta de XAMPP:

```
DESDE: C:\Users\yovan\plantas_api
HASTA: C:\xampp\htdocs\plantas_api
```

### 2️⃣ Crear la base de datos

1. Abre **XAMPP Control Panel**
2. Inicia **Apache** y **MySQL**
3. Abre phpMyAdmin: `http://localhost/phpmyadmin`
4. Click en **"SQL"** en el menú superior
5. Copia TODO el contenido del archivo `database.sql`
6. Pégalo en el editor SQL
7. Click en **"Continuar"**

### 3️⃣ Configurar la IP del servidor

Edita el archivo `config.php` línea 16:

```php
// Cambia esta IP a la de tu servidor
define('BASE_URL', 'http://TU_IP_AQUI/plantas_api/');

// Por ejemplo:
define('BASE_URL', 'http://192.168.18.24/plantas_api/');
```

**¿Cómo saber tu IP?**

Abre CMD y ejecuta:
```bash
ipconfig
```

Busca **"Dirección IPv4"** en tu adaptador de red WiFi/Ethernet.

### 4️⃣ Agregar imágenes de plantas

Coloca las imágenes de las plantas en la carpeta `uploads/`:

```
plantas_api/uploads/
├── manzanilla.jpg
├── eucalipto.jpg
├── aloe_vera.jpg
├── hierba_buena.jpg
├── romero.jpg
├── lavanda.jpg
├── jengibre.jpg
├── valeriana.jpg
├── tila.jpg
└── calendula.jpg
```

**IMPORTANTE:** Los nombres de archivo deben coincidir exactamente con los que están en la base de datos.

### 5️⃣ Probar los endpoints

Abre tu navegador y prueba:

**Test 1: Listar plantas**
```
http://localhost/plantas_api/plants.php
```

Debe retornar un JSON con el array de plantas.

**Test 2: Login (usar Postman o cURL)**
```bash
curl -X POST http://localhost/plantas_api/login.php \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"testuser\",\"password\":\"test123\"}"
```

Debe retornar:
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

### 6️⃣ Configurar la app Android

Edita en tu proyecto Android el archivo `RetrofitClient.java`:

```java
private static final String BASE_URL = "http://TU_IP_AQUI/plantas_api/";
```

**IMPORTANTE:** Usa la misma IP que configuraste en `config.php`.

---

## 📡 ENDPOINTS DISPONIBLES

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/login.php` | Autenticación de usuarios |
| GET | `/plants.php` | Listar todas las plantas |
| GET | `/get_plant.php?id=X` | Obtener una planta específica |
| GET | `/model_version.php` | Versión actual del modelo IA |

---

## 👤 CREDENCIALES DE PRUEBA

**Usuario de prueba:**
- Username: `testuser`
- Password: `test123`

**Administrador:**
- Username: `admin`
- Password: `test123`

---

## 🔍 TROUBLESHOOTING

### ❌ Error: "Error de conexión a base de datos"

**Solución:**
1. Verifica que MySQL esté corriendo en XAMPP
2. Verifica credenciales en `config.php` (líneas 11-14)
3. Revisa el archivo `logs/db_errors.log`

### ❌ Error: "CORS error" en Android

**Solución:**
Ya están configurados los headers CORS en `config.php`. Si persiste, verifica que Apache tenga habilitado `mod_headers`.

### ❌ Imágenes no se muestran

**Solución:**
1. Verifica que las imágenes existan en `uploads/`
2. Verifica que los nombres coincidan con los de la BD
3. Verifica que la URL base sea correcta

---

## 📂 ESTRUCTURA DE CARPETAS

```
plantas_api/
├── config.php              # Configuración general
├── helpers.php             # Funciones auxiliares
├── login.php               # Endpoint de login
├── plants.php              # Endpoint de plantas
├── get_plant.php           # Endpoint de planta específica
├── model_version.php       # Endpoint de versión de modelo
├── database.sql            # Script SQL
├── README.md               # Este archivo
│
├── uploads/                # Imágenes de plantas
│   └── (tus imágenes aquí)
│
├── uploads/predictions/    # Imágenes de identificaciones
│
├── logs/                   # Logs del sistema
│   ├── api.log
│   └── db_errors.log
│
└── models/                 # Modelos de TensorFlow
    └── modelo_plantas_96acc.tflite
```

---

## ✅ CHECKLIST

- [ ] Carpeta movida a `C:\xampp\htdocs\plantas_api`
- [ ] Apache y MySQL corriendo en XAMPP
- [ ] Base de datos `plantas_db` creada
- [ ] IP configurada en `config.php`
- [ ] Imágenes subidas a `uploads/`
- [ ] Test de `plants.php` exitoso
- [ ] Test de `login.php` exitoso
- [ ] App Android configurada con la IP correcta

---

## 🎯 PRÓXIMOS PASOS

Una vez que el dashboard funcione:

1. **Sincronizar app Android:**
   - Abre la app
   - Haz pull-to-refresh en la lista de plantas
   - Verifica que se sincronicen desde el servidor

2. **Probar identificación:**
   - Toma una foto de una planta
   - Verifica que el resultado muestre los campos:
     - `preparation`
     - `precautions`

3. **Implementar gestión de inactividad:**
   - Cierre de sesión después de 3 segundos de inactividad
   - Tooltips en flechas de navegación

---

**¿Necesitas ayuda?** Revisa los logs en `logs/` para depurar errores.

¡Tu sistema está listo para usarse! 🚀
