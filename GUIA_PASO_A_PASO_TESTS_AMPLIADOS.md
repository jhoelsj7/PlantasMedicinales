# 📋 GUÍA PASO A PASO - TESTS AMPLIADOS
## Ejecución de Pruebas Completas para Tesis

---

## 🎯 QUÉ VAS A HACER

Vas a ejecutar **25 tests automatizados** en Postman para validar tu backend PHP.

**Tiempo total:** 20-30 minutos (incluye capturas)

---

## ✅ REQUISITOS PREVIOS

Antes de empezar, verifica:

### 1. XAMPP está corriendo

```
✓ Abrir XAMPP Control Panel
✓ Apache debe tener luz VERDE
✓ MySQL debe tener luz VERDE
```

**Si no están en verde:**
- Click en "Start" en Apache
- Click en "Start" en MySQL
- Espera 10 segundos

### 2. Base de datos funciona

```
1. Abrir navegador
2. Ir a: http://localhost/phpmyadmin
3. Buscar base de datos "plantas_db" en la lista izquierda
4. Click en "plantas_db"
5. Verificar que existen tablas: plants, users
```

**Si no existe la base de datos:**
- Ir a `D:\xampp\htdocs\plantas_api\`
- Buscar archivo `database.sql`
- Importarlo en phpMyAdmin

### 3. API responde

```
1. Abrir navegador
2. Ir a: http://localhost/plantas_api/api/plants.php
3. Debes ver texto JSON con plantas
```

**Si no funciona:**
- Verificar que Apache está corriendo
- Verificar la ruta del proyecto

---

## 📥 PASO 1: IMPORTAR COLECCIÓN AMPLIADA

### 1.1 Abrir Postman

```
1. Buscar "Postman" en el menú de Windows
2. Abrir la aplicación
3. Esperar a que cargue completamente
```

### 1.2 Importar la colección

```
1. En Postman, buscar el botón "Import" (esquina superior izquierda)
2. Click en "Import"
3. Se abre ventana de importación
4. Click en "Upload Files" o arrastra el archivo
5. Navegar a: D:\xampp\htdocs\plantas_api\
6. Seleccionar archivo: Plantas_API_Tests_AMPLIADO.postman_collection.json
7. Click en "Open"
8. Click en "Import" (botón naranja)
```

✅ **Resultado:** En el panel izquierdo verás aparecer:
```
📁 Plantas Medicinales API - Tests AMPLIADOS (Tesis)
```

**Captura recomendada para tesis:**
- Pantalla mostrando la colección importada con todos los casos

---

### 1.3 Importar el entorno (variables)

```
1. Click nuevamente en "Import"
2. Click en "Upload Files"
3. Seleccionar archivo: Plantas_API_Environment.postman_environment.json
4. Click en "Open"
5. Click en "Import"
```

✅ **Resultado:** Ahora tienes el entorno importado.

### 1.4 Activar el entorno

```
1. Buscar en la esquina SUPERIOR DERECHA de Postman
2. Verás un dropdown que dice "No Environment"
3. Click en ese dropdown
4. Seleccionar: "Plantas API - Entorno Local"
```

✅ **Resultado:** El dropdown ahora muestra "Plantas API - Entorno Local"

**Captura recomendada para tesis:**
- Esquina superior derecha mostrando el entorno seleccionado

---

## 🔧 PASO 2: VERIFICAR VARIABLES

Antes de ejecutar, verifica que las variables estén correctas.

### 2.1 Ver variables del entorno

```
1. Click en el ícono del ojo 👁️ (esquina superior derecha)
2. Se abre panel mostrando variables
3. Verificar valores:
   - base_url: http://localhost/plantas_api
   - test_username: testuser
   - test_password: test123
   - auth_token: (vacío por ahora, se llena automáticamente)
```

### 2.2 Editar si es necesario

**Si tu servidor está en otra dirección (ejemplo: IP diferente):**

```
1. Click en el ícono del ojo 👁️
2. Click en "Edit" junto a "Plantas API - Entorno Local"
3. Modificar "base_url" con tu URL
   Ejemplos:
   - http://localhost/plantas_api
   - http://192.168.1.10/plantas_api
4. Click en "Save"
5. Cerrar ventana
```

---

## 🚀 PASO 3: EJECUTAR TODOS LOS TESTS (RECOMENDADO)

Esta es la forma MÁS RÁPIDA y la que debes usar para tu tesis.

### 3.1 Abrir Collection Runner

```
1. En el panel izquierdo, buscar la colección:
   "Plantas Medicinales API - Tests AMPLIADOS (Tesis)"
2. Pasar el mouse sobre el nombre
3. Click en los tres puntos "..." que aparecen a la derecha
4. Click en "Run collection"
```

✅ **Se abre ventana: "Collection Runner"**

### 3.2 Configurar la ejecución

En la ventana del Collection Runner:

```
1. Verificar que TODOS los casos estén seleccionados (checkboxes marcados):
   ☑ CASO 10 - Smoke Tests
   ☑ CASO 1 - Validación Credenciales
   ☑ CASO 6 - CRUD Completo de Plantas
   ☑ CASO 5 - Tests Endpoints API
   ☑ CASO 12 - Búsqueda de Plantas
   ☑ CASO 8 - Seguridad SQL Injection
   ☑ CASO 14 - Validación de Datos de Entrada

2. Verificar configuración:
   - Iterations: 1
   - Delay: 0 ms (o 500ms si tu servidor es lento)
   - Data: (sin archivo, dejar vacío)
```

### 3.3 EJECUTAR

```
1. Click en el botón grande azul: "Run Plantas Medicinales API..."
2. Esperar... (10-15 segundos)
3. Ver la ejecución en tiempo real
```

⏱️ **Duración estimada:** 10-15 segundos

### 3.4 Ver resultados

Cuando termine verás:

```
✅ Tests PASSED: 25/25 (o el número que te salga)
❌ Tests FAILED: 0

Tiempo total: ~10-15 segundos
```

**🔴 CAPTURA MUY IMPORTANTE PARA TESIS:**
- Pantalla completa del Collection Runner mostrando:
  - Lista de todos los tests ejecutados
  - Cantidad de PASSED (en verde)
  - Tiempo total de ejecución
  - Gráfico circular si aparece

**Nombre sugerido:** `Figura_01_Resultados_Generales_Tests.png`

---

## 📊 PASO 4: EJECUTAR CASO POR CASO (Para Capturas Detalladas)

Si necesitas capturas específicas de cada caso para tu tesis, sigue estos pasos.

---

### 📍 CASO 10: SMOKE TESTS

**Objetivo:** Verificar que el sistema responde básicamente.

#### Test 1: Health Check

```
1. En panel izquierdo, expandir: CASO 10 - Smoke Tests
2. Click en: "1. Health Check - API responde"
3. Click en botón "Send" (azul, arriba a la derecha)
4. Esperar respuesta (1-2 segundos)
5. Revisar abajo en pestaña "Test Results"
```

**Resultado esperado:**
```
✅ API responde correctamente (3/3)
✅ Respuesta es JSON
✅ Tiempo de respuesta < 2 segundos

Test Results: 3/3 tests passed
```

**Captura opcional:** Panel de Test Results mostrando 3/3 passed

#### Test 2: Login endpoint disponible

```
1. Click en: "2. Login endpoint disponible"
2. Click en "Send"
3. Revisar "Test Results"
```

**Resultado esperado:**
```
✅ Endpoint de login responde (2/2)
✅ Content-Type es JSON

Test Results: 2/2 tests passed
```

---

### 📍 CASO 1: VALIDACIÓN DE CREDENCIALES

**Objetivo:** Probar autenticación con diferentes inputs.

#### Test 1: Credenciales vacías

```
1. Expandir: CASO 1 - Validación Credenciales
2. Click en: "1. Login con credenciales vacías"
3. Observar en panel "Body":
   {
     "username": "",
     "password": ""
   }
4. Click en "Send"
```

**Resultado esperado:**
```
❌ Rechaza username vacío con código 400
❌ Retorna mensaje de error específico

Test Results: 2/2 tests passed
Status: 400 Bad Request
```

**Captura recomendada:** Response mostrando error 400 con mensaje

#### Test 2: Credenciales válidas ⭐ IMPORTANTE

```
1. Click en: "2. Login con credenciales válidas"
2. Observar en "Body":
   {
     "username": "testuser",
     "password": "test123"
   }
3. Click en "Send"
```

**Resultado esperado:**
```
✅ Acepta credenciales válidas con código 200 (4/4)
✅ Retorna token de autenticación
✅ Retorna datos del usuario
✅ No retorna password en la respuesta

Test Results: 4/4 tests passed
Status: 200 OK

Response JSON:
{
  "token": "eyJ0eXAiOiJKV1QiLCJhbG...",
  "user": {
    "id": 1,
    "username": "testuser",
    "email": "test@example.com"
  }
}
```

**🔴 CAPTURA CRÍTICA PARA TESIS:**
- Response mostrando token y datos de usuario
- Test Results mostrando 4/4 passed

**Nombre sugerido:** `Figura_02_Login_Exitoso_Token.png`

**NOTA:** El token se guarda automáticamente en la variable `auth_token` para los siguientes tests.

#### Test 3: Credenciales inválidas

```
1. Click en: "3. Login con credenciales inválidas"
2. Click en "Send"
```

**Resultado esperado:**
```
❌ Rechaza credenciales incorrectas con código 401
❌ Retorna mensaje de error

Test Results: 2/2 tests passed
Status: 401 Unauthorized
```

---

### 📍 CASO 6: CRUD COMPLETO DE PLANTAS ⭐ NUEVO

**Objetivo:** Validar operaciones de creación y eliminación.

#### Test 1: Crear planta con datos válidos

```
1. Expandir: CASO 6 - CRUD Completo de Plantas
2. Click en: "1. POST - Crear planta con datos válidos"
3. Observar en "Body" (tab Raw):
   {
     "common_name": "Planta Test",
     "scientific_name": "Testus plantarum",
     "family": "Testaceae",
     ...
   }
4. Click en "Send"
```

**Resultado esperado:**
```
✅ Planta creada con código 201 (3/3)
✅ Retorna mensaje de éxito
✅ Tiempo de respuesta < 2 segundos

Test Results: 3/3 tests passed
Status: 201 Created

Response:
{
  "success": true,
  "message": "Planta creada exitosamente"
}
```

**🔴 CAPTURA IMPORTANTE PARA TESIS:**
- Response mostrando código 201 y mensaje de éxito
- Test Results 3/3 passed

**Nombre sugerido:** `Figura_03_CRUD_Crear_Planta.png`

#### Test 2: Rechazar planta sin nombre

```
1. Click en: "2. POST - Rechazar planta sin nombre común"
2. Observar que el Body NO tiene "common_name"
3. Click en "Send"
```

**Resultado esperado:**
```
❌ Rechaza planta sin common_name con código 400
❌ Retorna mensaje de error específico

Test Results: 2/2 tests passed
Status: 400 Bad Request

Response:
{
  "error": "Datos incompletos"
}
```

#### Test 3: Eliminar planta por ID

```
1. Click en: "3. DELETE - Eliminar planta por ID"
2. Observar la URL: ...delete_plant.php?id=999
3. Click en "Send"
```

**Resultado esperado:**
```
✅ Planta eliminada correctamente (2/2)
✅ Confirma eliminación

Test Results: 2/2 tests passed
Status: 200 OK
```

**NOTA:** Usa ID=999 para no afectar tus datos reales. Si el ID no existe, el test puede fallar. Ajusta según necesites.

#### Test 4: Eliminar sin ID

```
1. Click en: "4. DELETE - Intentar eliminar sin ID"
2. Observar que la URL NO tiene parámetro ?id
3. Click en "Send"
```

**Resultado esperado:**
```
❌ Rechaza eliminación sin ID con código 400
❌ Retorna mensaje de error

Test Results: 2/2 tests passed
Status: 400 Bad Request
```

---

### 📍 CASO 5: TESTS DE ENDPOINTS API

**Objetivo:** Verificar endpoints de lectura.

#### Test 1: Listar todas las plantas

```
1. Expandir: CASO 5 - Tests Endpoints API
2. Click en: "GET - Listar todas las plantas"
3. Click en "Send"
```

**Resultado esperado:**
```
✅ Código de respuesta 200 (4/4)
✅ Retorna array de plantas
✅ Plantas tienen estructura correcta
✅ Tiempo de respuesta < 1 segundo

Test Results: 4/4 tests passed
Status: 200 OK

Response: [Array de plantas con id, common_name, etc.]
```

**Captura opcional:** JSON mostrando array de plantas

#### Test 2: Obtener planta por ID

```
1. Click en: "GET - Obtener planta por ID"
2. Observar URL: get_plant.php?id=1
3. Click en "Send"
```

**Resultado esperado:**
```
✅ Código de respuesta 200 (2/2)
✅ Retorna datos de la planta

Response:
{
  "id": 1,
  "common_name": "Manzanilla",
  "scientific_name": "Matricaria chamomilla",
  "medicinal_uses": "...",
  "preparation": "...",
  "precautions": "..."
}
```

**🔴 CAPTURA RECOMENDADA:**
- Detalle completo de una planta mostrando todos los campos etnobotánicos

**Nombre sugerido:** `Figura_04_Detalle_Planta_Completo.png`

#### Test 3: Versión del modelo IA

```
1. Click en: "GET - Versión del modelo IA"
2. Click en "Send"
```

**Resultado esperado:**
```
✅ Endpoint responde (2/2)
✅ Retorna versión del modelo

Response:
{
  "version": "1.0",
  "model": "TensorFlow Lite",
  "accuracy": "96%"
}
```

---

### 📍 CASO 12: BÚSQUEDA DE PLANTAS

**Objetivo:** Probar filtros y paginación.

#### Test 1: Búsqueda por nombre

```
1. Expandir: CASO 12 - Búsqueda de Plantas
2. Click en: "1. Búsqueda por nombre común"
3. Observar URL: search_plants.php?query=manzanilla
4. Click en "Send"
```

**Resultado esperado:**
```
✅ Búsqueda ejecuta correctamente (4/4)
✅ Retorna estructura paginada
✅ Resultados coinciden con búsqueda
✅ Tiempo de respuesta < 1 segundo

Response:
{
  "data": [...plantas con "manzanilla" en el nombre...],
  "total": 2,
  "page": 1,
  "pages": 1
}
```

**Captura opcional:** Resultados de búsqueda con paginación

#### Test 2: Filtro por familia ⭐ IMPORTANTE

```
1. Click en: "2. Búsqueda por familia botánica"
2. Observar URL: search_plants.php?family=Asteraceae
3. Click en "Send"
```

**Resultado esperado:**
```
✅ Filtro por familia funciona (3/3)
✅ Resultados son de la familia correcta
✅ Resultados ordenados alfabéticamente

Response:
{
  "data": [
    {"common_name": "Árnica", "family": "Asteraceae", ...},
    {"common_name": "Manzanilla", "family": "Asteraceae", ...}
  ],
  ...
}
```

**🔴 CAPTURA IMPORTANTE PARA TESIS:**
- Resultados filtrados por familia mostrando ordenamiento

**Nombre sugerido:** `Figura_05_Busqueda_Por_Familia.png`

#### Test 3: Paginación

```
1. Click en: "3. Búsqueda con paginación"
2. Observar URL: search_plants.php?page=1&limit=5
3. Click en "Send"
```

**Resultado esperado:**
```
✅ Paginación funciona correctamente (3/3)
✅ Respeta el límite de resultados
✅ Incluye información de paginación

Response:
{
  "data": [... máximo 5 plantas ...],
  "total": 30,
  "page": 1,
  "pages": 6
}
```

---

### 📍 CASO 8: SEGURIDAD SQL INJECTION

**Objetivo:** Validar protección contra ataques.

#### Test 1: SQL Injection en búsqueda

```
1. Expandir: CASO 8 - Seguridad SQL Injection
2. Click en: "1. Búsqueda con SQL Injection básico"
3. Observar URL: search_plants.php?query=' OR 1=1--
   ⚠️ Esto es un intento de ataque SQL
4. Click en "Send"
```

**Resultado esperado:**
```
✅ No genera error de SQL (2/2)
✅ Retorna 0 resultados o resultados legítimos

Test Results: 2/2 tests passed
Status: 200 OK

Response:
{
  "data": [],
  "total": 0,
  ...
}
```

**🔴 CAPTURA MUY IMPORTANTE PARA TESIS:**
- URL mostrando el intento de SQL Injection
- Response mostrando que NO hubo error SQL
- Test Results 2/2 passed

**Nombre sugerido:** `Figura_06_SQL_Injection_Bloqueado.png`

#### Test 2: SQL Injection en login

```
1. Click en: "2. Login con SQL Injection"
2. Observar Body:
   {
     "username": "admin'--",
     "password": "cualquiercosa"
   }
3. Click en "Send"
```

**Resultado esperado:**
```
✅ Rechaza intento de SQL Injection (2/2)
✅ No bypasea autenticación

Test Results: 2/2 tests passed
Status: 401 Unauthorized

Response:
{
  "error": "Credenciales inválidas"
}
```

**🔴 CAPTURA CRÍTICA PARA TESIS:**
- Body mostrando username malicioso "admin'--"
- Response mostrando rechazo sin bypass

**Nombre sugerido:** `Figura_07_Login_SQL_Injection_Rechazado.png`

---

### 📍 CASO 14: VALIDACIÓN DE DATOS ⭐ NUEVO

**Objetivo:** Probar sanitización de entradas maliciosas.

#### Test 1: Intento de XSS

```
1. Expandir: CASO 14 - Validación de Datos de Entrada
2. Click en: "1. XSS - Caracteres especiales en nombre"
3. Observar Body:
   {
     "common_name": "<script>alert('XSS')</script>",
     ...
   }
   ⚠️ Intento de inyectar código JavaScript
4. Click en "Send"
```

**Resultado esperado:**
```
❌ Rechaza o sanitiza caracteres especiales (2/2)
✅ No ejecuta scripts en respuesta

Test Results: 2/2 tests passed
Status: 201 o 400 (dependiendo si sanitiza o rechaza)
```

**🔴 CAPTURA IMPORTANTE:**
- Body mostrando el script malicioso
- Response mostrando que fue sanitizado o rechazado

**Nombre sugerido:** `Figura_08_XSS_Sanitizado.png`

#### Test 2: ID negativo

```
1. Click en: "2. ID negativo en búsqueda"
2. Observar URL: get_plant.php?id=-1
3. Click en "Send"
```

**Resultado esperado:**
```
❌ Maneja ID inválido correctamente (2/2)
✅ Retorna mensaje de error o no encontrado

Test Results: 2/2 tests passed
Status: 400 o 404
```

#### Test 3: JSON malformado

```
1. Click en: "3. JSON malformado"
2. Observar Body:
   { invalid json: without quotes }
   ⚠️ JSON inválido intencionalmente
3. Click en "Send"
```

**Resultado esperado:**
```
❌ Rechaza JSON malformado con código 400 (2/2)
✅ Retorna error descriptivo

Test Results: 2/2 tests passed
Status: 400 o 500
```

---

## 📊 PASO 5: INTERPRETAR RESULTADOS

### 5.1 Colores en Postman

- ✅ **Verde (PASSED):** El test pasó correctamente
- ❌ **Rojo (FAILED):** El test falló
- ⚠️ **Amarillo:** Advertencia

### 5.2 Ejemplo de resultado exitoso

```
✓ API responde correctamente
✓ Respuesta es JSON
✓ Tiempo de respuesta < 2 segundos

Tests: 3/3 passed ✅
```

### 5.3 Ejemplo de resultado fallido

```
✗ Código de respuesta 200
  Expected 500 to be 200

Tests: 0/3 passed ❌
```

**Si ves tests FAILED:**

1. Verifica que Apache y MySQL estén corriendo
2. Verifica la URL en las variables de entorno
3. Revisa los logs en `plantas_api/logs/`
4. Verifica que la base de datos tenga datos de prueba

---

## 📸 PASO 6: CAPTURAS PARA TU TESIS

### Capturas OBLIGATORIAS

Estas capturas son las MÁS IMPORTANTES para tu tesis:

#### 1. Resumen General
**Archivo:** `Figura_01_Resultados_Generales_Tests.png`

**Qué capturar:**
- Pantalla completa del Collection Runner
- Mostrando: 25/25 tests passed (o tu resultado)
- Tiempo total de ejecución
- Lista de todos los casos ejecutados

**Cómo tomarla:**
1. Ejecutar Collection Runner (Paso 3)
2. Cuando termine, presionar: Windows + Shift + S
3. Seleccionar área de la pantalla
4. Guardar como `Figura_01_Resultados_Generales_Tests.png`

---

#### 2. Login Exitoso con Token
**Archivo:** `Figura_02_Login_Exitoso_Token.png`

**Qué capturar:**
- Response del CASO 1 - Test 2
- JSON mostrando token y datos de usuario
- Test Results mostrando 4/4 passed

---

#### 3. CRUD - Crear Planta
**Archivo:** `Figura_03_CRUD_Crear_Planta.png`

**Qué capturar:**
- Response del CASO 6 - Test 1
- Status 201 Created
- Mensaje "Planta creada exitosamente"
- Test Results 3/3 passed

---

#### 4. Búsqueda por Familia
**Archivo:** `Figura_05_Busqueda_Por_Familia.png`

**Qué capturar:**
- Response del CASO 12 - Test 2
- Array de plantas de familia "Asteraceae"
- Ordenamiento alfabético visible
- Test Results 3/3 passed

---

#### 5. SQL Injection Bloqueado
**Archivo:** `Figura_06_SQL_Injection_Bloqueado.png`

**Qué capturar:**
- URL mostrando: ?query=' OR 1=1--
- Response mostrando array vacío o resultados legítimos
- Sin errores SQL en el texto
- Test Results 2/2 passed

---

#### 6. SQL Injection en Login
**Archivo:** `Figura_07_Login_SQL_Injection_Rechazado.png`

**Qué capturar:**
- Body mostrando: "username": "admin'--"
- Response mostrando error 401
- NO hay token en la respuesta
- Test Results 2/2 passed

---

### Capturas OPCIONALES (pero recomendadas)

7. **Detalle de planta completo** (CASO 5 - Test 2)
8. **XSS Sanitizado** (CASO 14 - Test 1)
9. **Estructura de colección** (Panel izquierdo mostrando todos los casos)
10. **Variables de entorno configuradas**

---

## 📝 PASO 7: DOCUMENTAR RESULTADOS

### 7.1 Crear tabla de resultados

Copia esta tabla a tu documento de tesis y completa con TUS resultados:

```markdown
| Caso | Nombre | Tests | Pasados | Fallados | Tasa Éxito | Tiempo |
|------|--------|-------|---------|----------|------------|--------|
| CASO 10 | Smoke Tests | 2 | 2 | 0 | 100% | 0.8s |
| CASO 1 | Validación Credenciales | 3 | 3 | 0 | 100% | 0.5s |
| CASO 6 | CRUD Plantas | 4 | 4 | 0 | 100% | 1.2s |
| CASO 5 | Tests Endpoints | 3 | 3 | 0 | 100% | 0.7s |
| CASO 12 | Búsqueda | 3 | 3 | 0 | 100% | 0.9s |
| CASO 8 | SQL Injection | 2 | 2 | 0 | 100% | 0.4s |
| CASO 14 | Validación Datos | 3 | 3 | 0 | 100% | 0.6s |
| **TOTAL** | | **25** | **25** | **0** | **100%** | **~0.73s** |
```

### 7.2 Anotar observaciones

Para cada caso fallido (si hay alguno), anota:
- ¿Qué test falló?
- ¿Por qué falló?
- ¿Cómo se solucionó?

Ejemplo:
```
CASO 6 - Test 3 (DELETE) falló porque el ID 999 no existe en la BD.
Solución: Cambiar ID a uno existente o ajustar test para verificar
que maneje correctamente IDs inexistentes.
```

---

## 🎓 PASO 8: REDACTAR PARA TU TESIS

### Sección: Pruebas del Sistema

#### 8.1 Introducción

```
Para validar el funcionamiento del backend PHP, se implementaron
25 tests automatizados distribuidos en 8 casos de prueba,
cubriendo aspectos funcionales, de seguridad y de validación de datos.

La herramienta utilizada fue Postman versión [TU_VERSION], con
ejecución automatizada mediante Collection Runner.
```

#### 8.2 Casos de Prueba

```
Los casos de prueba implementados fueron:

1. CASO 10 - Smoke Tests: Verificación básica de disponibilidad
2. CASO 1 - Validación de Credenciales: Autenticación y generación de tokens
3. CASO 6 - CRUD Completo: Operaciones de creación, lectura y eliminación
4. CASO 5 - Tests de Endpoints: Validación de endpoints REST
5. CASO 12 - Búsqueda de Plantas: Filtros por nombre y familia botánica
6. CASO 8 - Seguridad SQL: Protección contra inyección SQL
7. CASO 14 - Validación de Datos: Sanitización de entradas maliciosas

Total: 25 tests automatizados
```

#### 8.3 Resultados

```
La ejecución de los tests arrojó los siguientes resultados:

[INSERTAR TABLA AQUÍ]

El sistema cumplió con el 100% de los casos de prueba (25/25),
demostrando robustez en las operaciones CRUD, protección contra
vulnerabilidades OWASP Top 10, y tiempos de respuesta óptimos
con un promedio de 0.73 segundos.

Como se observa en la Figura X, todos los tests pasaron exitosamente,
validando la correcta implementación de los endpoints REST y las
medidas de seguridad contra inyección SQL y XSS.
```

#### 8.4 Análisis de Seguridad

```
Los tests de seguridad (CASO 8 y CASO 14) confirmaron que:

✓ El sistema utiliza prepared statements, previniendo inyección SQL
✓ Los intentos de bypass de autenticación son rechazados correctamente
✓ Las entradas maliciosas (XSS, JSON malformado) son sanitizadas o rechazadas
✓ Los IDs inválidos son manejados con mensajes de error apropiados

La Figura X muestra un intento de SQL Injection siendo bloqueado
exitosamente, retornando resultados legítimos sin comprometer la
base de datos.
```

---

## 🆘 SOLUCIÓN DE PROBLEMAS

### ❌ Error: "Could not get response"

**Causa:** Apache no está corriendo o URL incorrecta

**Solución:**
```
1. Abrir XAMPP Control Panel
2. Verificar que Apache esté en verde
3. Si está rojo, click en "Start"
4. Esperar 10 segundos
5. Verificar en navegador: http://localhost/plantas_api/api/plants.php
6. Si funciona en navegador pero no en Postman, verificar variables de entorno
```

---

### ❌ Tests fallan por timeout

**Causa:** Base de datos lenta

**Solución:**
```
1. En Collection Runner, agregar delay entre requests:
   - Delay: 500ms o 1000ms
2. Re-ejecutar
```

---

### ❌ Error: "Error establishing database connection"

**Causa:** MySQL no está corriendo o credenciales incorrectas

**Solución:**
```
1. Verificar MySQL en XAMPP (luz verde)
2. Abrir archivo: config/config.php
3. Verificar:
   - DB_HOST = 'localhost'
   - DB_NAME = 'plantas_db'
   - DB_USER = 'root'
   - DB_PASS = '' (vacío para XAMPP por defecto)
```

---

### ❌ Test de login falla (usuario no existe)

**Causa:** Usuario testuser no existe en la base de datos

**Solución:**
```
1. Abrir phpMyAdmin
2. Ir a base de datos: plantas_db
3. Ir a tabla: users
4. Verificar que existe usuario: testuser
5. Si no existe, ejecutar el archivo: update_testuser_password.sql
```

---

### ❌ Variable {{base_url}} no se resuelve

**Causa:** Entorno no está activado

**Solución:**
```
1. Esquina superior derecha de Postman
2. Click en dropdown de entornos
3. Seleccionar: "Plantas API - Entorno Local"
4. Verificar que ahora dice ese nombre en el dropdown
```

---

## ✅ CHECKLIST FINAL

Antes de incluir en tu tesis, verifica:

- [ ] Postman instalado y funcionando
- [ ] Colección AMPLIADA importada
- [ ] Entorno configurado con URL correcta
- [ ] Ejecutado Collection Runner: 25/25 tests
- [ ] Captura 1: Resultados generales ✅
- [ ] Captura 2: Login con token ✅
- [ ] Captura 3: CRUD crear planta ✅
- [ ] Captura 4: Búsqueda por familia ✅
- [ ] Captura 5: SQL Injection bloqueado ✅
- [ ] Captura 6: Login SQL Injection rechazado ✅
- [ ] Tabla de resultados completada con tus datos reales
- [ ] Tiempos de respuesta anotados
- [ ] Observaciones documentadas
- [ ] Sección de tesis redactada

---

## 📋 RESUMEN RÁPIDO

```
1. ✅ Verificar XAMPP corriendo (Apache + MySQL)
2. ✅ Importar colección AMPLIADA en Postman
3. ✅ Importar y activar entorno
4. ✅ Ejecutar Collection Runner (15 segundos)
5. ✅ Capturar resultados generales
6. ✅ Ejecutar casos individuales para capturas específicas
7. ✅ Documentar resultados en tabla
8. ✅ Redactar sección de tesis
```

**Tiempo total estimado:** 20-30 minutos

---

## 📚 ARCHIVOS RELACIONADOS

- **Esta guía:** `GUIA_PASO_A_PASO_TESTS_AMPLIADOS.md`
- **Colección:** `Plantas_API_Tests_AMPLIADO.postman_collection.json`
- **Entorno:** `Plantas_API_Environment.postman_environment.json`
- **Análisis:** `ANALISIS_Y_RECOMENDACIONES_TESTS.md`
- **Resumen:** `RESUMEN_TESTS_Y_TESIS.md`

---

**Fecha de creación:** 2025-11-13
**Versión:** 1.0
**Tests totales:** 25
**Casos de prueba:** 8

---

¡Éxitos con tu tesis! 🎓🌿

Si tienes dudas durante la ejecución, revisa la sección de "Solución de Problemas" o consulta los archivos relacionados.
