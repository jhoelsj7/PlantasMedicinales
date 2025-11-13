# 📋 GUÍA DE TESTING MANUAL PARA TESIS
## Aplicación: Plantas Medicinales con IA

---

## 🎯 OBJETIVO

Esta guía contiene casos de prueba MANUALES que puedes ejecutar, ver resultados en pantalla y tomar capturas para tu tesis.

---

## 📱 CASO DE PRUEBA 1: VALIDACIÓN DE CREDENCIALES

### Objetivo
Verificar que el sistema valida correctamente el formato de username y password.

### Herramientas
- Aplicación instalada en dispositivo/emulador
- Capturador de pantalla (Win + Shift + S en Windows)

### Procedimiento

#### Test 1.1: Username vacío

1. Abre la aplicación
2. En pantalla de login, deja el campo **Usuario** VACÍO
3. Escribe cualquier contraseña (ejemplo: "123456")
4. Presiona "Iniciar Sesión"
5. **Resultado esperado:** Mensaje de error "Usuario no puede estar vacío"
6. 📸 **CAPTURA:** Pantalla mostrando el mensaje de error

#### Test 1.2: Username muy corto (< 3 caracteres)

1. En pantalla de login, escribe en **Usuario:** `ab` (2 caracteres)
2. Escribe en **Contraseña:** `123456`
3. Presiona "Iniciar Sesión"
4. **Resultado esperado:** Mensaje de error o login rechazado
5. 📸 **CAPTURA:** Pantalla mostrando rechazo

#### Test 1.3: Username válido (3-20 caracteres)

1. En pantalla de login, escribe en **Usuario:** `admin` (5 caracteres)
2. Escribe en **Contraseña:** `admin123`
3. Presiona "Iniciar Sesión"
4. **Resultado esperado:** Login exitoso, abre MainActivity
5. 📸 **CAPTURA:** MainActivity abierta correctamente

#### Test 1.4: Password corto (< 6 caracteres)

1. Regresa al login (cierra sesión si es necesario)
2. Escribe en **Usuario:** `admin`
3. Escribe en **Contraseña:** `12345` (5 caracteres)
4. Presiona "Iniciar Sesión"
5. **Resultado esperado:** Login rechazado
6. 📸 **CAPTURA:** Mensaje de error

### Tabla de Resultados

| Test ID | Entrada Usuario | Entrada Password | Resultado Esperado | Resultado Obtenido | Estado |
|---------|----------------|------------------|-------------------|-------------------|--------|
| 1.1 | (vacío) | 123456 | Error: "Usuario vacío" | | ✅/❌ |
| 1.2 | ab | 123456 | Login rechazado | | ✅/❌ |
| 1.3 | admin | admin123 | Login exitoso | | ✅/❌ |
| 1.4 | admin | 12345 | Login rechazado | | ✅/❌ |

---

## 🤖 CASO DE PRUEBA 2: CLASIFICACIÓN CON IA

### Objetivo
Evaluar que el modelo de IA identifica correctamente plantas medicinales.

### Herramientas
- Aplicación instalada
- Imágenes de prueba de plantas
- Cronómetro (usa el del celular)

### Procedimiento

#### Test 2.1: Clasificación de imagen de planta

1. Abre la aplicación y haz login
2. En MainActivity, presiona el botón **"Identificar Planta"**
3. ⏱️ **INICIA CRONÓMETRO**
4. Selecciona una foto de planta (o toma una foto)
5. Espera a que el modelo procese
6. ⏱️ **DETÉN CRONÓMETRO** cuando aparezca el resultado
7. **Resultado esperado:**
   - Tiempo de procesamiento < 3 segundos
   - Muestra nombre de la planta
   - Muestra porcentaje de confianza
8. 📸 **CAPTURA:** Pantalla de resultado con:
   - Imagen procesada
   - Nombre identificado
   - Porcentaje de confianza
   - (Opcional: cronómetro visible)

#### Test 2.2: Múltiples clasificaciones

1. Repite el proceso con 3 imágenes diferentes de plantas
2. Anota el tiempo para cada una
3. **Resultado esperado:** Todas se procesan en < 3 segundos
4. 📸 **CAPTURA:** Los 3 resultados

### Tabla de Resultados

| Test | Imagen | Tiempo (segundos) | Planta Identificada | Confianza (%) | Estado |
|------|--------|-------------------|---------------------|---------------|--------|
| 2.1 | Imagen 1 | | | | ✅/❌ |
| 2.2a | Imagen 2 | | | | ✅/❌ |
| 2.2b | Imagen 3 | | | | ✅/❌ |
| 2.2c | Imagen 4 | | | | ✅/❌ |

---

## ⏱️ CASO DE PRUEBA 3: GESTIÓN DE SESIÓN

### Objetivo
Verificar que la sesión se mantiene activa y expira correctamente.

### Herramientas
- Aplicación instalada
- Reloj/cronómetro

### Procedimiento

#### Test 3.1: Sesión activa con uso continuo

1. Haz login en la aplicación
2. Usa la app continuamente durante 5 minutos:
   - Navega entre pantallas
   - Busca plantas
   - Ve detalles
3. **Resultado esperado:** La sesión permanece activa, no te expulsa
4. 📸 **CAPTURA:** App funcionando después de 5 minutos

#### Test 3.2: Sesión expira por inactividad

1. Haz login en la aplicación
2. Deja la app SIN TOCAR por 30 minutos
3. Después de 30 minutos, intenta navegar o realizar una acción
4. **Resultado esperado:**
   - Mensaje "Sesión expirada"
   - Te redirige al login
5. 📸 **CAPTURA:** Mensaje de sesión expirada

### Tabla de Resultados

| Test | Condición | Tiempo | Resultado Esperado | Resultado Obtenido | Estado |
|------|-----------|--------|-------------------|-------------------|--------|
| 3.1 | Uso continuo | 5 min | Sesión activa | | ✅/❌ |
| 3.2 | Inactividad | 30 min | Sesión expira | | ✅/❌ |

---

## 🔍 CASO DE PRUEBA 4: FUNCIONALIDADES PRINCIPALES (SMOKE TEST)

### Objetivo
Verificar que todas las funciones principales funcionan sin crashes.

### Herramientas
- Aplicación instalada
- Cronómetro

### Procedimiento

#### Test 4.1: App inicia correctamente

1. Abre la aplicación desde el launcher
2. ⏱️ Mide el tiempo de carga
3. **Resultado esperado:**
   - App abre en < 2 segundos
   - Muestra pantalla de login sin errores
4. 📸 **CAPTURA:** Pantalla de login

#### Test 4.2: Login funciona

1. Ingresa credenciales válidas (admin / admin123)
2. ⏱️ Mide tiempo desde presionar botón hasta que abre MainActivity
3. **Resultado esperado:**
   - Login completa en < 2 segundos
   - Abre MainActivity correctamente
4. 📸 **CAPTURA:** MainActivity con botones visibles

#### Test 4.3: Navegación a lista de plantas

1. Desde MainActivity, presiona **"Ver Catálogo"** o **"Lista de Plantas"**
2. ⏱️ Mide tiempo de navegación
3. **Resultado esperado:**
   - Navegación en < 2 segundos
   - Muestra lista de plantas
4. 📸 **CAPTURA:** Pantalla de lista de plantas

#### Test 4.4: Búsqueda de plantas

1. Desde MainActivity, presiona **"Buscar"**
2. ⏱️ Mide tiempo de navegación
3. Escribe el nombre de una planta (ej: "muña")
4. **Resultado esperado:**
   - Navegación en < 2 segundos
   - Campo de búsqueda funciona
   - Muestra resultados
5. 📸 **CAPTURA:** Pantalla de búsqueda con resultados

#### Test 4.5: Ver detalle de planta

1. Desde la lista, selecciona una planta
2. **Resultado esperado:**
   - Abre pantalla de detalle
   - Muestra información completa (nombre, imagen, propiedades)
3. 📸 **CAPTURA:** Pantalla de detalle de planta

### Tabla de Resultados

| Test | Funcionalidad | Tiempo (s) | Resultado Esperado | Obtenido | Estado |
|------|---------------|-----------|-------------------|----------|--------|
| 4.1 | Inicio app | | < 2s, muestra login | | ✅/❌ |
| 4.2 | Login | | < 2s, abre MainActivity | | ✅/❌ |
| 4.3 | Navegar a lista | | < 2s, muestra lista | | ✅/❌ |
| 4.4 | Búsqueda | | < 2s, muestra resultados | | ✅/❌ |
| 4.5 | Ver detalle | | Muestra info completa | | ✅/❌ |

---

## 🌐 CASO DE PRUEBA 5: SINCRONIZACIÓN CON API (POSTMAN)

### Objetivo
Probar las APIs REST del backend usando Postman.

### Herramientas
- Postman (descarga: https://www.postman.com/downloads/)
- URL del servidor API

### Configuración Inicial en Postman

1. Abre Postman
2. Crea una nueva **Collection** llamada "Plantas Medicinales API"
3. Define una **Environment Variable:**
   - Variable: `API_URL`
   - Value: `http://tu-servidor.com/api` (reemplaza con tu URL real)

### Test 5.1: GET - Listar todas las plantas

**Request:**
```
Method: GET
URL: {{API_URL}}/plants.php
```

**Resultado Esperado:**
- Status Code: 200 OK
- Response: JSON con array de plantas
- Estructura esperada:
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "nombre_cientifico": "Matricaria chamomilla",
      "nombre_comun": "Manzanilla",
      "familia": "Asteraceae",
      ...
    }
  ]
}
```

📸 **CAPTURA:** Postman mostrando:
- Request enviado
- Status 200 OK
- JSON de respuesta

### Test 5.2: GET - Buscar planta específica

**Request:**
```
Method: GET
URL: {{API_URL}}/plants.php?search=manzanilla
```

**Resultado Esperado:**
- Status Code: 200 OK
- Response: Array con plantas que coinciden con "manzanilla"

📸 **CAPTURA:** Resultado de búsqueda

### Test 5.3: POST - Registrar identificación

**Request:**
```
Method: POST
URL: {{API_URL}}/identifications.php
Headers:
  Content-Type: application/json
Body (JSON):
{
  "user_id": 1,
  "plant_id": 5,
  "confidence": 0.95,
  "image_path": "/storage/test.jpg"
}
```

**Resultado Esperado:**
- Status Code: 201 Created
- Response:
```json
{
  "success": true,
  "message": "Identificación registrada",
  "id": 123
}
```

📸 **CAPTURA:** Request y response exitoso

### Test 5.4: GET - Verificar sincronización

**Request:**
```
Method: GET
URL: {{API_URL}}/sync_status.php?last_sync=2024-01-01
```

**Resultado Esperado:**
- Status Code: 200 OK
- Response con datos actualizados desde la fecha

📸 **CAPTURA:** Datos de sincronización

### Tabla de Resultados de API

| Test | Endpoint | Method | Status Code Esperado | Obtenido | Estado |
|------|----------|--------|---------------------|----------|--------|
| 5.1 | /plants.php | GET | 200 | | ✅/❌ |
| 5.2 | /plants.php?search= | GET | 200 | | ✅/❌ |
| 5.3 | /identifications.php | POST | 201 | | ✅/❌ |
| 5.4 | /sync_status.php | GET | 200 | | ✅/❌ |

---

## 📊 FORMATO PARA DOCUMENTAR EN TESIS

### Plantilla de Tabla de Resultados Generales

```
| Caso de Prueba | Tests Ejecutados | Exitosos | Fallidos | % Éxito |
|----------------|------------------|----------|----------|---------|
| CP1 - Validación de credenciales | 4 | | | |
| CP2 - Clasificación con IA | 4 | | | |
| CP3 - Gestión de sesión | 2 | | | |
| CP4 - Funcionalidades principales | 5 | | | |
| CP5 - APIs con Postman | 4 | | | |
| **TOTAL** | **19** | | | |
```

### Plantilla de Tabla de Métricas de Rendimiento

```
| Métrica | Valor Obtenido | Valor Esperado | Cumple |
|---------|----------------|----------------|--------|
| Tiempo de inicio de app | _____ s | < 2s | ✅/❌ |
| Tiempo de login | _____ s | < 2s | ✅/❌ |
| Tiempo de clasificación IA | _____ s | < 3s | ✅/❌ |
| Tiempo de navegación | _____ s | < 2s | ✅/❌ |
| Timeout de sesión | _____ min | 30 min | ✅/❌ |
```

---

## 📸 CHECKLIST DE CAPTURAS PARA TESIS

### Capturas Obligatorias:

- [ ] Pantalla de login
- [ ] Login con error de validación
- [ ] Login exitoso (MainActivity)
- [ ] Resultado de clasificación de IA con porcentaje
- [ ] Lista de plantas
- [ ] Detalle de una planta
- [ ] Pantalla de búsqueda con resultados
- [ ] Postman - GET exitoso mostrando JSON
- [ ] Postman - POST exitoso mostrando Status 201
- [ ] Mensaje de sesión expirada (opcional)

### Capturas Opcionales:

- [ ] Cronómetro midiendo tiempo de clasificación
- [ ] Configuración de Postman (Environment variables)
- [ ] Múltiples resultados de clasificación
- [ ] Historial de identificaciones

---

## 🔧 HERRAMIENTAS RECOMENDADAS

### Para Capturar Pantalla en PC:
- **Windows:** Win + Shift + S (Recorte de pantalla)
- **Mac:** Cmd + Shift + 4
- **Software:** ShareX, Greenshot, Lightshot

### Para Capturar Pantalla en Android:
- Botón Power + Volumen Abajo
- O desde la barra de notificaciones

### Para Medir Tiempos:
- Cronómetro del celular
- Stopwatch online: https://www.online-stopwatch.com/
- Screen recorder con timestamp

### Para Pruebas de API:
- **Postman:** https://www.postman.com/downloads/
- Alternativa: **Insomnia:** https://insomnia.rest/download

---

## 📝 NOTAS IMPORTANTES

1. **Ejecuta cada test 2-3 veces** para asegurar resultados consistentes
2. **Anota observaciones** si algo no funciona como esperado
3. **Guarda todas las capturas** en una carpeta organizada por caso de prueba
4. **Nombra las capturas** descriptivamente: `CP1_Test1.1_Login_Error.png`

---

## ✅ RESUMEN DE EJECUCIÓN

Total de pruebas manuales: **19 tests**

- CP1 - Validación: 4 tests
- CP2 - Clasificación IA: 4 tests
- CP3 - Gestión de sesión: 2 tests
- CP4 - Funcionalidades: 5 tests
- CP5 - APIs Postman: 4 tests

**Tiempo estimado total: 1-2 horas**

---

**Creado para tesis - 2025-11-13**
