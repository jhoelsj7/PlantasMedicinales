# 🖥️ COMANDOS DE TESTING PARA EJECUTAR EN TERMINAL
## Para Documentar en Tesis con Capturas de Pantalla

---

## 🎯 OBJETIVO

Ejecutar tests desde **TERMINAL** y tomar **CAPTURAS DE PANTALLA** de la salida en consola.
Así se ve que TÚ ejecutaste los tests manualmente, no automatizado.

---

## 📋 PREPARACIÓN

### Abrir Terminal en el Proyecto

**Opción 1: Terminal de Windows**
```cmd
cd C:\Users\yovan\AndroidStudioProjects\PlantasMedicinales
```

**Opción 2: Desde Android Studio**
```
View → Tool Windows → Terminal
```

---

## 🧪 COMANDOS DE TESTING

### 1️⃣ TESTS UNITARIOS - ValidationUtilsTest (CP1)

**Comando:**
```bash
./gradlew test --tests ValidationUtilsTest
```

**Qué hace:**
- Ejecuta los 19 tests de validación de credenciales
- Muestra resultado en consola con detalles

**📸 CAPTURA:**
- Toda la salida de la terminal
- Asegúrate de que se vea: "19 tests completed"
- Captura el tiempo de ejecución

**Salida esperada:**
```
> Task :app:testDebugUnitTest
ValidationUtilsTest > testUsernameVacio_DebeRetornarFalse() PASSED
ValidationUtilsTest > testUsernameNull_DebeRetornarFalse() PASSED
ValidationUtilsTest > testUsernameMenorA3Caracteres_DebeRetornarFalse() PASSED
...
19 tests completed, 19 passed
BUILD SUCCESSFUL in 5s
```

---

### 2️⃣ TESTS UNITARIOS - SessionManagerTest (CP7)

**Comando:**
```bash
./gradlew test --tests SessionManagerTest
```

**Qué hace:**
- Ejecuta los 11 tests de gestión de sesión
- Muestra resultado en consola

**📸 CAPTURA:**
- Toda la salida de la terminal
- Se ve "11 tests completed"

**Salida esperada:**
```
> Task :app:testDebugUnitTest
SessionManagerTest > testSesionReciente_NoDebeEstarExpirada() PASSED
SessionManagerTest > testSesionConTimeout_DebeEstarExpirada() PASSED
...
11 tests completed, 11 passed
BUILD SUCCESSFUL in 18s
```

---

### 3️⃣ TODOS LOS TESTS UNITARIOS JUNTOS

**Comando:**
```bash
./gradlew test --console=verbose
```

**Qué hace:**
- Ejecuta TODOS los tests unitarios (30 tests)
- Muestra salida detallada en terminal
- `--console=verbose` muestra MÁS información

**📸 CAPTURA:**
- Resumen final que muestra:
  - Total tests ejecutados
  - Tests passed
  - Tests failed
  - Duración total

**Salida esperada:**
```
> Task :app:testDebugUnitTest
ValidationUtilsTest PASSED (19 tests)
SessionManagerTest PASSED (11 tests)
ExampleUnitTest PASSED (1 test)

31 tests completed, 31 passed
BUILD SUCCESSFUL in 19s
```

---

### 4️⃣ TESTS INSTRUMENTADOS - PlantClassifierTest (CP2)

⚠️ **IMPORTANTE:** Conecta tu dispositivo Android o inicia el emulador PRIMERO

**Verificar dispositivo:**
```bash
adb devices
```

**Comando:**
```bash
./gradlew connectedAndroidTest --tests PlantClassifierTest
```

**Qué hace:**
- Ejecuta los 11 tests del clasificador de IA
- Ejecuta EN EL DISPOSITIVO/EMULADOR
- Muestra métricas de rendimiento en consola

**📸 CAPTURA:**
- La salida completa
- Líneas que digan "System.out: Tiempo de inferencia: XXXms"
- Resultado final con 11 tests

**Salida esperada:**
```
> Task :app:connectedDebugAndroidTest
Starting 11 tests on Pixel 5 API 30

PlantClassifierTest > testModeloCargaCorrectamente[Pixel 5 API 30] PASSED
System.out: Tiempo de inferencia: 287ms
PlantClassifierTest > testTiempoInferenciaMenorA3Segundos[Pixel 5 API 30] PASSED
...
11 tests completed, 11 passed
BUILD SUCCESSFUL in 2m 15s
```

---

### 5️⃣ TESTS INSTRUMENTADOS - SmokeTestSuite (CP10)

**Comando:**
```bash
./gradlew connectedAndroidTest --tests SmokeTestSuite
```

**Qué hace:**
- Ejecuta los 7 smoke tests
- Prueba funcionalidades críticas
- Mide tiempos de respuesta

**📸 CAPTURA:**
- Métricas de tiempo en consola
- "System.out: Tiempo de login: XXXms"
- Resultado final

**Salida esperada:**
```
> Task :app:connectedDebugAndroidTest
SmokeTestSuite > smokeTest1_AppIniciaSinCrash PASSED
System.out: Tiempo de login: 856ms
SmokeTestSuite > smokeTest2_LoginFunciona PASSED
System.out: Tiempo de navegación a lista: 412ms
...
7 tests completed, 7 passed
BUILD SUCCESSFUL in 1m 45s
```

---

### 6️⃣ COBERTURA DE CÓDIGO con JaCoCo

**Comando 1: Ejecutar tests con cobertura**
```bash
./gradlew testDebugUnitTest jacocoTestReport
```

**Comando 2: Ver resumen de cobertura en terminal**
```bash
cat app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.csv
```

**📸 CAPTURA:**
- Salida del comando cat mostrando el CSV
- Muestra líneas cubiertas vs total

**Salida esperada:**
```
GROUP,PACKAGE,CLASS,INSTRUCTION_MISSED,INSTRUCTION_COVERED,BRANCH_MISSED,BRANCH_COVERED,LINE_MISSED,LINE_COVERED
app,com.tuapp.plantasmedicinales.utils,ValidationUtils,12,145,3,15,5,42
...
TOTAL,,,156,1843,25,98,45,285
```

**Interpretación:**
- INSTRUCTION_COVERED / (INSTRUCTION_MISSED + INSTRUCTION_COVERED) = % cobertura
- Ejemplo: 1843 / (156 + 1843) = 92.2% de cobertura

---

### 7️⃣ VER SOLO RESUMEN (Sin detalles)

**Comando:**
```bash
./gradlew test --console=plain | grep -E "(tests|passed|failed|BUILD)"
```

**📸 CAPTURA:**
- Salida limpia solo con resumen

**Salida esperada:**
```
31 tests completed, 31 passed
BUILD SUCCESSFUL in 19s
```

---

## 🌐 NEWMAN (Tests de API desde Terminal)

### Instalación de Newman

```bash
# 1. Instalar Node.js desde: https://nodejs.org/
# 2. Instalar Newman
npm install -g newman
```

### Comando para ejecutar tests de API

**Comando:**
```bash
newman run Plantas_Medicinales_API.postman_collection.json --environment tu_environment.json --reporters cli
```

**Sin environment (más simple):**
```bash
newman run Plantas_Medicinales_API.postman_collection.json --reporters cli
```

**📸 CAPTURA:**
- Toda la salida de newman
- Tabla con resultados de cada request
- Resumen final con estadísticas

**Salida esperada:**
```
┌─────────────────────────┬────────────┬────────────┐
│                         │   executed │     failed │
├─────────────────────────┼────────────┼────────────┤
│              iterations │          1 │          0 │
├─────────────────────────┼────────────┼────────────┤
│                requests │          8 │          0 │
├─────────────────────────┼────────────┼────────────┤
│            test-scripts │          8 │          0 │
├─────────────────────────┼────────────┼────────────┤
│      prerequest-scripts │          0 │          0 │
├─────────────────────────┼────────────┼────────────┤
│              assertions │          8 │          0 │
└─────────────────────────┴────────────┴────────────┘

8 requests completed, 8 passed
```

---

## 📊 COMANDOS ESPECIALES PARA MÉTRICAS

### Ver solo tests que fallaron (si hay)

```bash
./gradlew test | grep FAILED
```

### Ver tiempo de cada test individual

```bash
./gradlew test --info | grep -E "(Test|Duration)"
```

### Ver memoria usada durante tests

```bash
./gradlew test --info | grep -i memory
```

---

## 🎬 ORDEN RECOMENDADO DE EJECUCIÓN PARA TESIS

### Sesión de Testing 1: Tests Unitarios (10 minutos)

```bash
# Test 1
./gradlew test --tests ValidationUtilsTest
# 📸 CAPTURA 1: Validación de credenciales

# Test 2
./gradlew test --tests SessionManagerTest
# 📸 CAPTURA 2: Gestión de sesión

# Test 3
./gradlew test
# 📸 CAPTURA 3: Todos los tests unitarios
```

---

### Sesión de Testing 2: Tests Instrumentados (30 minutos)

⚠️ **Conectar dispositivo o iniciar emulador ANTES**

```bash
# Verificar dispositivo
adb devices
# 📸 CAPTURA 4: Lista de dispositivos conectados

# Test 4
./gradlew connectedAndroidTest --tests PlantClassifierTest
# 📸 CAPTURA 5: Tests del modelo IA

# Test 5
./gradlew connectedAndroidTest --tests SmokeTestSuite
# 📸 CAPTURA 6: Smoke tests
```

---

### Sesión de Testing 3: Cobertura de Código (5 minutos)

```bash
# Test 6
./gradlew testDebugUnitTest jacocoTestReport

# Ver reporte
cat app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.csv
# 📸 CAPTURA 7: Reporte de cobertura CSV
```

---

### Sesión de Testing 4: Tests de API con Newman (10 minutos)

```bash
# Test 7
newman run Plantas_Medicinales_API.postman_collection.json --reporters cli
# 📸 CAPTURA 8: Resultados de API tests
```

---

## 📸 CHECKLIST DE CAPTURAS PARA TESIS

- [ ] **Captura 1:** ValidationUtilsTest - 19 tests passed
- [ ] **Captura 2:** SessionManagerTest - 11 tests passed
- [ ] **Captura 3:** Todos tests unitarios - 31 tests passed
- [ ] **Captura 4:** adb devices mostrando dispositivo conectado
- [ ] **Captura 5:** PlantClassifierTest con métricas de tiempo
- [ ] **Captura 6:** SmokeTestSuite con tiempos de respuesta
- [ ] **Captura 7:** Reporte CSV de cobertura de código
- [ ] **Captura 8:** Newman ejecutando tests de API

**Total: 8 capturas de pantalla desde terminal**

---

## 💡 TIPS PARA BUENAS CAPTURAS

### En Windows (PowerShell o CMD)

1. **Maximiza la terminal** a pantalla completa
2. **Aumenta el tamaño de fuente** (Ctrl + rueda del mouse)
3. **Limpia la pantalla antes** con: `cls` (Windows) o `clear` (Git Bash)
4. **Ejecuta el comando**
5. **Captura con:** Win + Shift + S
6. **Guarda con nombre descriptivo:** `CP1_ValidationUtils_Terminal.png`

### Hacer que se vea profesional

```bash
# Antes de capturar, ejecuta:
echo "=================================="
echo "CASO DE PRUEBA 1: VALIDACIÓN"
echo "=================================="
./gradlew test --tests ValidationUtilsTest
```

Esto añade un encabezado bonito a tu captura.

---

## 🚫 LO QUE NO VAS A HACER

❌ NO generar reportes HTML (muy automatizado)
❌ NO usar herramientas GUI para tests (quieres terminal)
❌ NO copiar/pegar resultados (quieres capturas reales)

## ✅ LO QUE SÍ VAS A HACER

✅ Ejecutar comandos en terminal
✅ Capturar pantallas de la salida
✅ Documentar tiempos y resultados
✅ Mostrar que TÚ ejecutaste manualmente

---

## 📝 TABLA PARA TU TESIS

Después de ejecutar todo, completa esta tabla:

| Comando Ejecutado | Tests | Passed | Failed | Duración | Captura |
|-------------------|-------|--------|--------|----------|---------|
| `./gradlew test --tests ValidationUtilsTest` | 19 | | | | CP1_Terminal.png |
| `./gradlew test --tests SessionManagerTest` | 11 | | | | CP7_Terminal.png |
| `./gradlew connectedAndroidTest --tests PlantClassifierTest` | 11 | | | | CP2_Terminal.png |
| `./gradlew connectedAndroidTest --tests SmokeTestSuite` | 7 | | | | CP10_Terminal.png |
| `newman run ...` | 8 | | | | API_Terminal.png |

---

## ⏱️ TIEMPO TOTAL ESTIMADO

- Configurar ambiente: 5 min
- Tests unitarios: 10 min
- Tests instrumentados: 30 min
- Cobertura: 5 min
- API Newman: 10 min
- **TOTAL: ~60 minutos**

---

**LISTO PARA EJECUTAR Y CAPTURAR** ✅
