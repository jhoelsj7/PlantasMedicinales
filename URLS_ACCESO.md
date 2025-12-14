# 🌿 URLs de Acceso - Plantas Medicinales API

## 📱 Para la App Android (API REST)

### URL Base:
```
http://192.168.18.24/plantas_api/api/
```

### Endpoints Disponibles:

#### 🌱 Plantas
```
✅ GET  /plantas_api/api/plants.php
   → Obtener todas las plantas activas

✅ GET  /plantas_api/api/get_plant.php?id=1
   → Obtener detalles de una planta específica

✅ GET  /plantas_api/api/search_plants.php?q=manzanilla
   → Buscar plantas (soporta paginación)
   → Parámetros: q, family, page, limit

✅ POST /plantas_api/api/add_plant.php
   → Agregar nueva planta (requiere autenticación)

✅ PUT  /plantas_api/api/update_plant.php?id=1
   → Actualizar planta existente (requiere autenticación)

✅ DELETE /plantas_api/api/delete_plant.php?id=1
   → Eliminar planta (soft delete)
```

#### 👤 Autenticación
```
✅ POST /plantas_api/api/login.php
   → Login de usuario
   → Body: { "username": "admin", "password": "admin123" }
```

#### 📷 Imágenes
```
✅ POST /plantas_api/api/upload_image.php
   → Subir imagen de planta
   → Content-Type: multipart/form-data
   → Campo: image (archivo)
```

#### 🤖 Modelo IA
```
✅ GET  /plantas_api/api/model_version.php
   → Obtener versión del modelo TensorFlow Lite
```

---

## 💻 Para el Dashboard Web (Panel de Admin)

### URL Principal:
```
http://192.168.18.24/plantas_api/
```

### Páginas del Dashboard:

#### 📊 Dashboard Principal
```
http://192.168.18.24/plantas_api/views/admin/index.php
```
- Estadísticas generales
- Total de plantas, usuarios y familias
- Últimas 5 plantas agregadas
- Gráfico de distribución por familia botánica

#### 🌿 Gestionar Plantas (CRUD)
```
http://192.168.18.24/plantas_api/views/admin/manage_plants.php
```
- Agregar nueva planta
- Editar plantas existentes
- Eliminar plantas
- Buscar plantas por nombre

#### 👥 Gestión de Usuarios
```
http://192.168.18.24/plantas_api/views/admin/manage_users.php
```
- Ver todos los usuarios registrados
- Fechas de registro
- Último login

#### 📈 Estadísticas Detalladas
```
http://192.168.18.24/plantas_api/views/admin/statistics.php
```
- Distribución completa por familia botánica
- Porcentajes y conteos
- Últimas 10 plantas agregadas

#### 📸 Gestión de Imágenes
```
http://192.168.18.24/plantas_api/views/admin/upload_images.php
```
- Subir nuevas imágenes (JPG/PNG, máx 5MB)
- Galería de todas las imágenes
- Ver tamaño de archivos

---

## 🔗 Ejemplos de Uso

### JavaScript/React Native (Fetch)
```javascript
// Obtener todas las plantas
fetch('http://192.168.18.24/plantas_api/api/plants.php')
  .then(res => res.json())
  .then(data => console.log(data));

// Buscar plantas
fetch('http://192.168.18.24/plantas_api/api/search_plants.php?q=lavanda')
  .then(res => res.json())
  .then(data => console.log(data));

// Login
fetch('http://192.168.18.24/plantas_api/api/login.php', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    username: 'admin',
    password: 'admin123'
  })
})
.then(res => res.json())
.then(data => console.log(data.token));
```

### Kotlin/Android (Retrofit)
```kotlin
// Interface
interface PlantaService {
    @GET("plants.php")
    suspend fun getPlantas(): List<Planta>

    @GET("get_plant.php")
    suspend fun getPlanta(@Query("id") id: Int): Planta

    @GET("search_plants.php")
    suspend fun buscarPlantas(@Query("q") query: String): SearchResult
}

// Base URL
val retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.18.24/plantas_api/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
```

---

## 📁 Estructura de Respuestas JSON

### Lista de Plantas
```json
[
  {
    "id": 1,
    "common_name": "Manzanilla",
    "scientific_name": "Matricaria chamomilla",
    "family": "Asteraceae",
    "description": "Planta herbácea...",
    "medicinal_uses": "Propiedades antiinflamatorias...",
    "preparation": "Infusión: 1 cucharada...",
    "precautions": "No consumir en exceso...",
    "image_path": "manzanilla.jpg",
    "imageUrl": "http://192.168.18.24/plantas_api/public/uploads/manzanilla.jpg",
    "is_active": 1,
    "created_at": "2025-10-08 09:22:45",
    "updated_at": "2025-10-08 09:22:45"
  }
]
```

### Búsqueda con Paginación
```json
{
  "data": [...], // Array de plantas
  "total": 25,   // Total de resultados
  "page": 1,     // Página actual
  "pages": 3     // Total de páginas
}
```

### Login Exitoso
```json
{
  "success": true,
  "token": "a1b2c3d4e5f6...",
  "user": {
    "id": 1,
    "username": "admin",
    "email": "admin@plantas.com",
    "full_name": "Administrador"
  }
}
```

---

## ⚠️ Notas Importantes

1. **Archivos y menús eliminados** (ya no existen):
   - ❌ `test_api_connection.php` - Era solo para pruebas
   - ❌ `monitor_api.php` - No necesario para el poblador
   - ❌ `settings.php` - Configuración interna
   - ✅ **El menú del dashboard ahora solo tiene 5 opciones limpias**

2. **Cambios en URLs de imágenes**:
   - Antes: `uploads/imagen.jpg`
   - Ahora: `public/uploads/imagen.jpg`
   - La API ya incluye la URL completa en el campo `imageUrl`

3. **Seguridad**:
   - Las carpetas `/models`, `/config` están protegidas por .htaccess
   - No se puede acceder directamente a archivos de configuración

4. **CORS**:
   - Habilitado para todas las solicitudes
   - La app Android puede hacer peticiones sin problemas

---

## 🚀 Próximos Pasos

Para el poblador (usuario final):
1. Solo necesita acceder al dashboard web
2. No necesita conocer la estructura técnica
3. Puede gestionar plantas, usuarios e imágenes desde el navegador

Para tu app Android:
1. Usa la URL base: `http://192.168.18.24/plantas_api/api/`
2. Todos los endpoints funcionan correctamente
3. Las respuestas JSON son las mismas que antes
