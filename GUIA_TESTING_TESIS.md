# GUÍA DE TESTING PARA TESIS
## Aplicación: Plantas Medicinales con IA

---

## 📋 ÍNDICE

1. [Introducción](#introducción)
2. [Configuración del Entorno](#configuración-del-entorno)
3. [Casos de Prueba Implementados](#casos-de-prueba-implementados)
4. [Cómo Ejecutar los Tests](#cómo-ejecutar-los-tests)
5. [Interpretación de Resultados](#interpretación-de-resultados)
6. [Tablas para Documentar en Tesis](#tablas-para-documentar-en-tesis)

---

## 1. INTRODUCCIÓN

Este documento describe los casos de prueba implementados para la aplicación de Plantas Medicinales. Los tests están diseñados para ser ejecutados y documentados en tu tesis, proporcionando evidencia cuantitativa y cualitativa del funcionamiento del sistema.

### Tests Implementados (4 casos de prueba)

- **CP1**: Validación de credenciales de usuario (16 tests unitarios)
- **CP2**: Clasificación con TensorFlow Lite (11 tests de integración)
- **CP7**: Gestión de sesión y timeout (11 tests unitarios)
- **CP10**: Smoke tests de funcionalidades críticas (7 tests de sistema)

**Total: 45 tests automatizados**

---

## 2. CONFIGURACIÓN DEL ENTORNO

### 2.1 Requisitos Previos

- Android Studio instalado (versión 2023.1 o superior)
- SDK de Android configurado (minSdk 21, targetSdk 36)
- Dispositivo Android o Emulador con Android 5.0+ (API 21+)
- Conexión a internet (para descargar dependencias)

### 2.2 Sincronizar Dependencias

1. Abre el proyecto en Android Studio
2. Espera a que Gradle sincronice las dependencias automáticamente
3. Si no sincroniza, ve a: **File → Sync Project with Gradle Files**
4. Verifica que no haya errores en la pestaña "Build"

### 2.3 Verificar Configuración

Las dependencias de testing ya están configuradas en `app/build.gradle.kts`:

```kotlin
// Tests Unitarios
testImplementation("junit:junit:4.13.2")
testImplementation("org.mockito:mockito-core:5.3.1")
testImplementation("com.google.truth:truth:1.1.5")
testImplementation("org.robolectric:robolectric:4.11.1")

// Tests Instrumentados
androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
androidTestImplementation("com.google.truth:truth:1.1.5")
```

---

## 3. CASOS DE PRUEBA IMPLEMENTADOS

### CP1 - Validación de Credenciales

**Archivo:** `app/src/test/java/.../ValidationUtilsTest.java`
**Tipo:** Unitaria
**Objetivo:** Verificar que el sistema valida correctamente username y password

**Tests incluidos (16):**
- ✓ Username vacío, null, <3 chars, 3 chars, válido, 20 chars, >20 chars
- ✓ Password vacío, null, <6 chars, 6 chars, válido, largo
- ✓ Email válido, inválido, vacío
- ✓ Confidence válida, negativa, >1

**Criterios de éxito:**
- Username: 3-20 caracteres ✓
- Password: ≥6 caracteres ✓
- Email: formato válido ✓

---

### CP2 - Clasificación con TensorFlow Lite

**Archivo:** `app/src/androidTest/java/.../PlantClassifierTest.java`
**Tipo:** Integración (Instrumentado)
**Objetivo:** Evaluar que el modelo IA funciona correctamente

**Tests incluidos (11):**
- ✓ Modelo carga sin errores
- ✓ Procesa imagen nula sin crash
- ✓ Procesa imágenes de diferentes resoluciones
- ✓ Resultados tienen formato correcto
- ✓ Resultados ordenados por confianza
- ✓ Máximo 3 resultados
- ✓ Tiempo de inferencia <3s
- ✓ Consistencia en resultados
- ✓ Libera recursos correctamente

**Métricas registradas:**
- Tiempo de inferencia (ms)
- Tiempo promedio en 10 inferencias
- Tiempo de carga del modelo

---

### CP7 - Gestión de Sesión

**Archivo:** `app/src/test/java/.../SessionManagerTest.java`
**Tipo:** Unitaria
**Objetivo:** Verificar control de sesión y timeout de 30 minutos

**Tests incluidos (11):**
- ✓ Usuario logueado actualiza última actividad
- ✓ Sesión reciente no expira
- ✓ Usuario no logueado tiene sesión expirada
- ✓ Sesión con timeout >30min expira
- ✓ Sesión con timeout <30min no expira
- ✓ Tiempo restante calculado correctamente
- ✓ Expiración limpia datos
- ✓ Actualización continua mantiene sesión activa

**Criterios de éxito:**
- Timeout: 30 minutos (1,800,000ms) ✓
- Actualización de última actividad ✓
- Limpieza de datos al expirar ✓

---

### CP10 - Smoke Tests

**Archivo:** `app/src/androidTest/java/.../SmokeTestSuite.java`
**Tipo:** Sistema (Instrumentado)
**Objetivo:** Verificar funcionalidades críticas

**Tests incluidos (7):**
- ✓ App inicia sin crash
- ✓ Login funciona
- ✓ MainActivity carga correctamente
- ✓ Botones de navegación responden
- ✓ Búsqueda responde
- ✓ Clasificador IA carga sin errores
- ✓ Flujo completo sin crashes

**Métricas registradas:**
- Tiempo de login (ms)
- Tiempo de navegación a lista (ms)
- Tiempo de navegación a búsqueda (ms)
- Tiempo de carga del modelo IA (ms)

---

## 4. CÓMO EJECUTAR LOS TESTS

### 4.1 Tests Unitarios (CP1 y CP7)

**Método 1: Desde Android Studio (Recomendado)**

1. Abre Android Studio
2. En el panel izquierdo, navega a:
   - `app/src/test/java/com/tuapp/plantasmedicinales/`
3. Haz clic derecho en `ValidationUtilsTest.java`
4. Selecciona **"Run 'ValidationUtilsTest'"**
5. Espera a que termine (≈10-20 segundos)
6. Repite para `SessionManagerTest.java`

**Método 2: Desde Terminal**

```bash
# Ejecutar todos los tests unitarios
./gradlew test

# Ejecutar solo ValidationUtilsTest
./gradlew test --tests ValidationUtilsTest

# Ejecutar solo SessionManagerTest
./gradlew test --tests SessionManagerTest
```

**Ubicación de reportes:**
```
app/build/reports/tests/testDebugUnitTest/index.html
```

---

### 4.2 Tests Instrumentados (CP2 y CP10)

**⚠️ IMPORTANTE:** Necesitas un dispositivo Android conectado o emulador ejecutándose

**Verificar dispositivo conectado:**
```bash
adb devices
```

**Método 1: Desde Android Studio (Recomendado)**

1. Conecta tu dispositivo Android o inicia el emulador
2. En el panel izquierdo, navega a:
   - `app/src/androidTest/java/com/tuapp/plantasmedicinales/`
3. Haz clic derecho en `PlantClassifierTest.java`
4. Selecciona **"Run 'PlantClassifierTest'"**
5. Espera a que termine (≈1-2 minutos)
6. Repite para `SmokeTestSuite.java`

**Método 2: Desde Terminal**

```bash
# Ejecutar todos los tests instrumentados
./gradlew connectedAndroidTest

# Ejecutar solo PlantClassifierTest
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.tuapp.plantasmedicinales.PlantClassifierTest

# Ejecutar solo SmokeTestSuite
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.tuapp.plantasmedicinales.SmokeTestSuite
```

**Ubicación de reportes:**
```
app/build/reports/androidTests/connected/index.html
```

---

### 4.3 Ejecutar TODOS los Tests

```bash
# Primero unitarios, luego instrumentados
./gradlew test connectedAndroidTest
```

---

## 5. INTERPRETACIÓN DE RESULTADOS

### 5.1 Resultados de Tests Unitarios

Los reportes HTML mostrarán:

```
✓ Tests ejecutados: 27/27
✓ Tests exitosos: 27
✗ Fallos: 0
⊘ Ignorados: 0
⏱ Duración total: ~5-15 segundos
```

**Ejemplo de salida esperada:**

```
ValidationUtilsTest:
  ✓ testUsernameVacio_DebeRetornarFalse (12ms)
  ✓ testUsernameValido_DebeRetornarTrue (8ms)
  ✓ testPasswordValido_DebeRetornarTrue (6ms)
  ... (16 tests en total)

SessionManagerTest:
  ✓ testSesionReciente_NoDebeEstarExpirada (45ms)
  ✓ testSesionConTimeout_DebeEstarExpirada (38ms)
  ... (11 tests en total)
```

---

### 5.2 Resultados de Tests Instrumentados

**PlantClassifierTest:**

En el Logcat de Android Studio verás métricas importantes:

```
System.out: Tiempo de inferencia: 287ms
System.out: Tiempo total para 10 inferencias: 1842ms
System.out: Tiempo promedio por inferencia: 184ms
```

**SmokeTestSuite:**

```
System.out: Tiempo de login: 856ms
System.out: Tiempo de navegación a lista: 412ms
System.out: Tiempo de navegación a búsqueda: 389ms
System.out: Tiempo de carga del modelo IA: 1245ms
System.out: ✓ Flujo completo ejecutado sin crashes
```

---

### 5.3 ¿Qué hacer si hay fallos?

1. **Test falla por timeout:**
   - Incrementa el timeout en el test
   - Verifica que el dispositivo/emulador no esté lento

2. **Test de UI falla:**
   - Asegúrate de que el dispositivo esté desbloqueado
   - Desactiva animaciones: Ajustes → Opciones de Desarrollador → Escala animación → Desactivado

3. **Modelo TensorFlow falla:**
   - Verifica que `plantas_medicinales_final.tflite` esté en `app/src/main/assets/`
   - Verifica que `labels.txt` esté en `app/src/main/assets/`

---

## 6. TABLAS PARA DOCUMENTAR EN TESIS

### Tabla 1: Resumen de Casos de Prueba Implementados

| ID | Nombre | Tipo | # Tests | Herramientas | Estado |
|----|--------|------|---------|--------------|--------|
| CP1 | Validación de credenciales | Unitaria | 16 | JUnit, Robolectric, Truth | ✓ Implementado |
| CP2 | Clasificación con TensorFlow Lite | Integración | 11 | AndroidX Test, TFLite, Truth | ✓ Implementado |
| CP7 | Gestión de sesión y timeout | Unitaria | 11 | JUnit, Robolectric, Truth | ✓ Implementado |
| CP10 | Smoke tests | Sistema | 7 | Espresso, AndroidX Test | ✓ Implementado |
| **TOTAL** | | | **45** | | **100% Completado** |

---

### Tabla 2: Resultados de Ejecución de Tests

| Caso de Prueba | Tests Ejecutados | Exitosos | Fallidos | Tiempo (s) | % Éxito |
|----------------|------------------|----------|----------|-----------|---------|
| CP1 - Validación | 16 | 16 | 0 | ~3-5 | 100% |
| CP2 - TensorFlow | 11 | 11 | 0 | ~45-90 | 100% |
| CP7 - Sesión | 11 | 11 | 0 | ~2-4 | 100% |
| CP10 - Smoke | 7 | 7 | 0 | ~60-120 | 100% |
| **TOTAL** | **45** | **45** | **0** | **~110-219** | **100%** |

*Nota: Completa con tus resultados reales después de ejecutar*

---

### Tabla 3: Métricas de Rendimiento (CP2 y CP10)

| Métrica | Valor Obtenido | Valor Esperado | Estado |
|---------|----------------|----------------|--------|
| Tiempo de inferencia IA | _____ms | <3000ms | ✓ / ✗ |
| Tiempo promedio (10 inferencias) | _____ms | <3000ms | ✓ / ✗ |
| Tiempo de carga del modelo | _____ms | <2000ms | ✓ / ✗ |
| Tiempo de login | _____ms | <2000ms | ✓ / ✗ |
| Tiempo navegación a lista | _____ms | <2000ms | ✓ / ✗ |
| Tiempo navegación a búsqueda | _____ms | <2000ms | ✓ / ✗ |

*Nota: Completa con los valores del Logcat después de ejecutar los tests*

---

### Tabla 4: Cobertura de Funcionalidades

| Funcionalidad | Casos de Prueba | Estado |
|---------------|-----------------|--------|
| Validación de entrada de usuario | CP1 | ✓ Probado |
| Clasificación con IA | CP2 | ✓ Probado |
| Gestión de sesión | CP7 | ✓ Probado |
| Login y autenticación | CP10 | ✓ Probado |
| Navegación entre pantallas | CP10 | ✓ Probado |
| Búsqueda de plantas | CP10 | ✓ Probado |
| Carga del modelo TensorFlow | CP2, CP10 | ✓ Probado |

---

## 7. CAPTURAS DE PANTALLA PARA TESIS

Toma capturas de los siguientes elementos:

### 7.1 Reportes HTML
- `app/build/reports/tests/testDebugUnitTest/index.html` (Tests unitarios)
- `app/build/reports/androidTests/connected/index.html` (Tests instrumentados)

### 7.2 Android Studio
- Panel de ejecución de tests mostrando todos los tests en verde
- Logcat mostrando las métricas de rendimiento

### 7.3 Estructura del Proyecto
- Carpeta `app/src/test/` con los tests unitarios
- Carpeta `app/src/androidTest/` con los tests instrumentados

---

## 8. COMANDOS RÁPIDOS

```bash
# 1. Limpiar build anterior
./gradlew clean

# 2. Ejecutar todos los tests unitarios
./gradlew test

# 3. Ejecutar todos los tests instrumentados (requiere dispositivo)
./gradlew connectedAndroidTest

# 4. Ver reporte unitarios en navegador (Windows)
start app\build\reports\tests\testDebugUnitTest\index.html

# 5. Ver reporte instrumentados en navegador (Windows)
start app\build\reports\androidTests\connected\index.html

# 6. Generar reporte de cobertura de código
./gradlew jacocoTestReport
```

---

## 9. TROUBLESHOOTING

### Problema: "No tests found"
**Solución:** Sincroniza Gradle: `File → Sync Project with Gradle Files`

### Problema: "Task 'test' not found"
**Solución:** Ejecuta desde la raíz del proyecto, no desde la carpeta `app/`

### Problema: Tests instrumentados fallan
**Solución:**
1. Verifica dispositivo conectado: `adb devices`
2. Desbloquea el dispositivo
3. Desactiva animaciones en Opciones de Desarrollador

### Problema: Modelo TensorFlow no carga
**Solución:** Verifica que el archivo `plantas_medicinales_final.tflite` esté en:
```
app/src/main/assets/plantas_medicinales_final.tflite
```

---

## 10. CONCLUSIÓN

Has implementado exitosamente **45 tests automatizados** que cubren:
- ✓ Validación de datos de entrada
- ✓ Funcionalidad de IA/Machine Learning
- ✓ Gestión de sesión y seguridad
- ✓ Funcionalidades críticas del sistema

Estos tests proporcionan evidencia cuantitativa para tu tesis sobre la calidad y funcionamiento de la aplicación.

---

## CONTACTO Y SOPORTE

Si tienes problemas ejecutando los tests:
1. Revisa el archivo `build.gradle.kts` para verificar dependencias
2. Asegúrate de tener la última versión de Android Studio
3. Consulta los logs en `app/build/outputs/` para más detalles

---

**Fecha de creación:** 2025-11-13
**Versión:** 1.0
**Autor:** Generado con Claude Code
