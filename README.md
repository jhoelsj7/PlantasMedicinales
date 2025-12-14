# Plantas Medicinales - App Android con IA

Aplicación Android para identificar plantas medicinales usando Inteligencia Artificial (TensorFlow Lite) y gestionar información sobre sus propiedades curativas.

## Características

- **Identificación por IA**: Reconoce plantas medicinales mediante CNN con 96% de precisión
- **Captura de Imágenes**: Toma fotos con la cámara o selecciona desde galería
- **Base de Datos Local**: Almacenamiento offline con Room Database
- **Sincronización**: Actualiza datos desde API REST
- **Búsqueda Avanzada**: Busca plantas por nombre común, científico o usos medicinales
- **Detalles Completos**: Información detallada sobre cada planta
- **Autenticación**: Sistema de login seguro

## Arquitectura

### Capas de la Aplicación

```
├── Presentation Layer
│   ├── Activities (LoginActivity, MainActivity, CameraActivity, etc.)
│   └── Adapters (PlantAdapter)
│
├── Business Logic Layer
│   ├── Controllers (PlantController, IdentificationController, SyncController)
│   └── Services (AuthService, PlantService, CNNService)
│
├── Data Layer
│   ├── Database (Room: AppDatabase, PlantDao)
│   ├── Models (Plant, Prediction, LoginRequest, LoginResponse)
│   └── API (Retrofit: ApiService, RetrofitClient)
│
└── Utils
    ├── Constants
    ├── ImageUtils (Glide)
    ├── NetworkUtils
    └── ValidationUtils
```

## Tecnologías Utilizadas

- **Lenguaje**: Java
- **SDK**: Android 21-36 (Android 5.0 - 14)
- **IA/ML**: TensorFlow Lite 2.13.0
- **Base de Datos**: Room 2.5.2
- **Networking**: Retrofit 2.9.0 + OkHttp 4.10.0
- **Imágenes**: Glide 4.15.1
- **Permisos**: Dexter 6.2.3

## Estructura del Proyecto

```
PlantasMedicinales/
├── app/                    # Aplicación Android
│   └── src/main/
│       ├── java/           # Código fuente Java
│       ├── res/            # Recursos (layouts, strings, etc.)
│       └── assets/         # Modelo TFLite y labels
│
└── plantas_api/            # Backend PHP (API REST)
    ├── api/                # Endpoints de la API
    ├── config/             # Configuración
    ├── controllers/        # Controladores
    ├── models/             # Modelos de datos
    ├── views/admin/        # Panel de administración
    └── public/             # Archivos públicos (uploads)
```

## Instalación

### Backend (API PHP)

1. **Mover carpeta al servidor web**
```
Copiar plantas_api/ a C:\xampp\htdocs\plantas_api
```

2. **Crear la base de datos**
   - Abre XAMPP e inicia Apache y MySQL
   - Abre phpMyAdmin: `http://localhost/phpmyadmin`
   - Ejecuta el contenido de `database.sql`

3. **Configurar**
   - Copia `config/config.example.php` a `config/config.php`
   - Edita la IP y credenciales de BD

### App Android

1. **Configurar la API**
   - Editar `RetrofitClient.java` línea 12:
```java
private static final String BASE_URL = "http://TU_IP_LOCAL/plantas_api/";
```

2. **Sincronizar y compilar**
```bash
./gradlew sync
./gradlew assembleDebug
```

## Credenciales de Prueba

- **Usuario**: `testuser` / **Contraseña**: `test123`
- **Admin**: `admin` / **Contraseña**: `test123`

## API Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/login.php` | Autenticación |
| GET | `/api/plants.php` | Listar plantas |
| GET | `/api/get_plant.php?id=X` | Obtener planta |
| POST | `/api/add_plant.php` | Agregar planta |
| PUT | `/api/update_plant.php` | Actualizar planta |
| DELETE | `/api/delete_plant.php` | Eliminar planta |
| GET | `/api/model_version.php` | Versión del modelo IA |

## Plantas Reconocidas

El modelo puede identificar: Astromeria, Muña, Manzanilla, Aloe Vera, Menta, Eucalipto, Romero, Jengibre, Lavanda, Boldo, Uña de Gato, Hierba Luisa.

## Modelo de IA

- **Arquitectura**: CNN (Convolutional Neural Network)
- **Precisión**: 96%
- **Input Size**: 224x224 pixels
- **Framework**: TensorFlow Lite
- **Tamaño**: 2.4 MB

## Licencia

Este proyecto es parte de un trabajo académico.

---

**Versión**: 1.0 | **Última actualización**: 2025
