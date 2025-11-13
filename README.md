# 🌿 Plantas Medicinales - App Android con IA

Aplicación Android para identificar plantas medicinales usando Inteligencia Artificial (TensorFlow Lite) y gestionar información sobre sus propiedades curativas.

## 📱 Características

- **Identificación por IA**: Reconoce plantas medicinales mediante CNN con 96% de precisión
- **Captura de Imágenes**: Toma fotos con la cámara o selecciona desde galería
- **Base de Datos Local**: Almacenamiento offline con Room Database
- **Sincronización**: Actualiza datos desde API REST
- **Búsqueda Avanzada**: Busca plantas por nombre común, científico o usos medicinales
- **Detalles Completos**: Información detallada sobre cada planta
- **Autenticación**: Sistema de login seguro

## 🏗️ Arquitectura

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

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Java
- **SDK**: Android 21-36 (Android 5.0 - 14)
- **IA/ML**: TensorFlow Lite 2.13.0
- **Base de Datos**: Room 2.5.2
- **Networking**: Retrofit 2.9.0 + OkHttp 4.10.0
- **Imágenes**: Glide 4.15.1
- **Permisos**: Dexter 6.2.3

## 📦 Estructura del Proyecto

```
app/src/main/
├── java/com/tuapp/plantasmedicinales/
│   ├── controller/
│   │   ├── IdentificationController.java
│   │   ├── PlantController.java
│   │   └── SyncController.java
│   │
│   ├── database/
│   │   ├── AppDatabase.java
│   │   └── PlantDao.java
│   │
│   ├── model/
│   │   ├── LoginRequest.java
│   │   ├── LoginResponse.java
│   │   └── Prediction.java
│   │
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── CNNService.java
│   │   └── PlantService.java
│   │
│   ├── utils/
│   │   ├── Constants.java
│   │   ├── ImageUtils.java
│   │   ├── NetworkUtils.java
│   │   └── ValidationUtils.java
│   │
│   └── Activities & UI Components
│
├── res/
│   ├── layout/
│   ├── menu/
│   └── values/
│
└── assets/
    ├── modelo_plantas_96acc.tflite (2.4 MB)
    └── labels.txt
```

## 🚀 Instalación

### Requisitos Previos

- Android Studio Arctic Fox o superior
- JDK 11
- Android SDK 21+
- Dispositivo físico o emulador Android

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone <repository-url>
cd PlantasMedicinales
```

2. **Configurar la API Backend**
   - Editar `RetrofitClient.java` línea 12
   - Cambiar la IP por tu servidor local:
```java
private static final String BASE_URL = "http://TU_IP_LOCAL/plantas_api/";
```

3. **Sincronizar Gradle**
```bash
./gradlew sync
```

4. **Compilar y Ejecutar**
```bash
./gradlew assembleDebug
./gradlew installDebug
```

## 🔑 Credenciales de Prueba

- **Usuario**: `admin`
- **Contraseña**: `admin123`

## 🌿 Plantas Reconocidas

El modelo actual puede identificar:

1. Astromeria
2. Muña
3. Manzanilla
4. Aloe Vera
5. Menta
6. Eucalipto
7. Romero
8. Jengibre
9. Lavanda
10. Boldo
11. Uña de Gato
12. Hierba Luisa

## 📡 API Endpoints

La app consume los siguientes endpoints:

- `GET /plants.php` - Obtener todas las plantas
- `POST /login.php` - Autenticación de usuario

## 🔒 Permisos Requeridos

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

## 🎯 Funcionalidades Principales

### 1. Identificación con IA
- Captura imagen desde cámara o galería
- Procesamiento con TensorFlow Lite
- Resultado con nivel de confianza
- Recomendaciones basadas en precisión

### 2. Gestión de Plantas
- Lista completa de plantas medicinales
- Búsqueda en tiempo real
- Detalles con propiedades curativas
- Sincronización con backend

### 3. Modo Offline
- Base de datos local con Room
- Funciona sin conexión
- Sincronización automática al conectarse

## 🧪 Testing

Para ejecutar los tests:

```bash
./gradlew test
./gradlew connectedAndroidTest
```

## 📈 Modelo de IA

- **Arquitectura**: CNN (Convolutional Neural Network)
- **Precisión**: 96%
- **Input Size**: 224x224 pixels
- **Framework**: TensorFlow Lite
- **Tamaño del Modelo**: 2.4 MB

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Notas de Desarrollo

- Usa `Constants.java` para valores configurables
- Sigue la arquitectura MVC establecida
- Implementa validaciones con `ValidationUtils`
- Usa `ImageUtils` para carga de imágenes con Glide
- Verifica conectividad con `NetworkUtils`

## 🐛 Problemas Conocidos

- La cámara devuelve thumbnail, no imagen completa (para producción considerar usar Camera2 API)
- Las imágenes de plantas dependen del backend (implementar cache local)

## 📄 Licencia

Este proyecto es parte de un trabajo académico.

## 👨‍💻 Autor

Desarrollado como aplicación de plantas medicinales con IA

## 📞 Soporte

Para reportar problemas o solicitar features, abre un issue en el repositorio.

---

**Versión**: 1.0
**Última actualización**: 2025
