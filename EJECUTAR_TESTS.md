# 🚀 EJECUTAR TESTS - GUÍA RÁPIDA

## ✅ PASO 1: Sincronizar Gradle

1. Abre el proyecto en Android Studio
2. Espera la sincronización automática
3. O ejecuta manualmente: **File → Sync Project with Gradle Files**

---

## 🧪 PASO 2: Ejecutar Tests Unitarios

### Opción A: Desde Android Studio (FÁCIL)

1. Ve a: `app/src/test/java/com/tuapp/plantasmedicinales/`
2. **Clic derecho** en `ValidationUtilsTest.java`
3. Selecciona: **"Run 'ValidationUtilsTest'"**
4. Repite con `SessionManagerTest.java`

### Opción B: Desde Terminal

```bash
./gradlew test
```

### Ver Reporte

```bash
# Windows
start app\build\reports\tests\testDebugUnitTest\index.html

# Linux/Mac
open app/build/reports/tests/testDebugUnitTest/index.html
```

---

## 📱 PASO 3: Ejecutar Tests Instrumentados

### ⚠️ IMPORTANTE: Conecta un dispositivo Android o emulador primero

```bash
# Verificar dispositivo conectado
adb devices
```

### Opción A: Desde Android Studio (FÁCIL)

1. Conecta tu dispositivo o inicia emulador
2. Ve a: `app/src/androidTest/java/com/tuapp/plantasmedicinales/`
3. **Clic derecho** en `PlantClassifierTest.java`
4. Selecciona: **"Run 'PlantClassifierTest'"**
5. Repite con `SmokeTestSuite.java`

### Opción B: Desde Terminal

```bash
./gradlew connectedAndroidTest
```

### Ver Reporte

```bash
# Windows
start app\build\reports\androidTests\connected\index.html

# Linux/Mac
open app/build/reports/androidTests/connected/index.html
```

---

## 📊 PASO 4: Recopilar Resultados para Tesis

### 4.1 Ver Métricas en Logcat

En Android Studio, abre la pestaña **Logcat** y busca:

```
System.out: Tiempo de inferencia: XXXms
System.out: Tiempo de login: XXXms
System.out: Tiempo de carga del modelo IA: XXXms
```

### 4.2 Tomar Capturas de Pantalla

1. Reporte HTML de tests unitarios
2. Reporte HTML de tests instrumentados
3. Panel de Android Studio mostrando tests en verde
4. Logcat con las métricas

### 4.3 Completar Tablas

Abre `GUIA_TESTING_TESIS.md` y completa:
- Tabla 2: Resultados de Ejecución
- Tabla 3: Métricas de Rendimiento

---

## 🎯 TESTS IMPLEMENTADOS

| Test | Tipo | # Tests | Archivo |
|------|------|---------|---------|
| **CP1** - Validación | Unitario | 16 | `ValidationUtilsTest.java` |
| **CP7** - Sesión | Unitario | 11 | `SessionManagerTest.java` |
| **CP2** - TensorFlow | Instrumentado | 11 | `PlantClassifierTest.java` |
| **CP10** - Smoke | Instrumentado | 7 | `SmokeTestSuite.java` |
| **TOTAL** | | **45** | |

---

## 🔧 COMANDOS ÚTILES

```bash
# Limpiar builds anteriores
./gradlew clean

# Ejecutar SOLO tests unitarios
./gradlew test

# Ejecutar SOLO tests instrumentados
./gradlew connectedAndroidTest

# Ejecutar TODOS los tests
./gradlew test connectedAndroidTest

# Ver todos los reportes generados
dir app\build\reports /s   # Windows
ls -R app/build/reports     # Linux/Mac
```

---

## ❌ SOLUCIÓN DE PROBLEMAS

### Error: "No tests found"
```bash
./gradlew clean
# Luego: File → Sync Project with Gradle Files en Android Studio
```

### Error: "No connected devices"
1. Conecta tu dispositivo Android via USB
2. O inicia el emulador desde Android Studio
3. Verifica con: `adb devices`

### Tests de UI fallan
1. Desbloquea el dispositivo
2. Ve a: **Ajustes → Sistema → Opciones de desarrollador**
3. Desactiva las 3 opciones de animación:
   - Escala de animación de ventana → Desactivado
   - Escala de animación de transición → Desactivado
   - Escala de duración de Animator → Desactivado

### Modelo TensorFlow no carga
Verifica que exista:
```
app/src/main/assets/plantas_medicinales_final.tflite
app/src/main/assets/labels.txt
```

---

## ⏱️ TIEMPOS ESTIMADOS

- Tests unitarios (CP1 + CP7): **3-10 segundos**
- Tests instrumentados (CP2 + CP10): **2-5 minutos**
- Total: **≈5-6 minutos**

---

## 📝 RESULTADOS ESPERADOS

### Tests Unitarios (27 tests)
```
✓ ValidationUtilsTest: 16/16 passed
✓ SessionManagerTest: 11/11 passed
✓ Duración: ~5-10 segundos
```

### Tests Instrumentados (18 tests)
```
✓ PlantClassifierTest: 11/11 passed
✓ SmokeTestSuite: 7/7 passed
✓ Duración: ~2-5 minutos
```

### Métricas Típicas
- Tiempo de inferencia IA: **100-500ms**
- Tiempo de login: **500-1500ms**
- Tiempo de carga modelo: **500-2000ms**

---

## 🎓 PARA TU TESIS

1. ✅ Ejecuta todos los tests
2. ✅ Toma capturas de los reportes HTML
3. ✅ Anota las métricas del Logcat
4. ✅ Completa las tablas en `GUIA_TESTING_TESIS.md`
5. ✅ Incluye capturas en tu documento de tesis

---

**¡Listo! Ahora tienes tests automatizados ejecutables para tu tesis.**
