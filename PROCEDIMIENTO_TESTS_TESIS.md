# 📋 PROCEDIMIENTO DE PRUEBAS - TESIS
## Sistema de Identificación de Plantas Medicinales del Valle del Urubamba

---

## 📚 ÍNDICE

1. [Casos de Prueba Implementados](#casos-de-prueba-implementados)
2. [Requisitos Previos](#requisitos-previos)
3. [Instalación de Herramientas](#instalación-de-herramientas)
4. [Configuración del Entorno](#configuración-del-entorno)
5. [Importación de Colección de Tests](#importación-de-colección-de-tests)
6. [Ejecución de Pruebas](#ejecución-de-pruebas)
7. [Interpretación de Resultados](#interpretación-de-resultados)
8. [Capturas para la Tesis](#capturas-para-la-tesis)
9. [Resultados Esperados](#resultados-esperados)

---

## 🎯 CASOS DE PRUEBA IMPLEMENTADOS

La colección de Postman incluye los siguientes casos de prueba adaptados al backend PHP:

| ID | Nombre del Caso | Tipo | Cantidad de Tests | Descripción |
|----|----------------|------|-------------------|-------------|
| **CASO 10** | Smoke Tests | Funcional - Humo | 2 tests | Verificación rápida de funcionalidades críticas |
| **CASO 1** | Validación de Credenciales | Funcional - Unitaria | 3 tests | Validación de formato y lógica de autenticación |
| **CASO 5** | Tests de Endpoints API | Funcional - Integración | 3 tests | Verificación de endpoints REST |
| **CASO 12** | Búsqueda de Plantas | Funcional - Integración | 3 tests | Filtrado por nombre, familia y paginación |
| **CASO 8** | Seguridad SQL Injection | No Funcional - Seguridad | 2 tests | Protección contra inyección SQL |

**TOTAL: 5 casos de prueba, 13 tests automatizados**

---

## ✅ REQUISITOS PREVIOS

### 1. Servidor XAMPP funcionando

Verificar que Apache y MySQL estén corriendo:

```bash
# Abrir XAMPP Control Panel
# Verificar que Apache y MySQL tengan luz verde
```

### 2. Base de datos configurada

```bash
# Acceder a phpMyAdmin: http://localhost/phpmyadmin
# Verificar que existe la base de datos 'plantas_db'
# Verificar que hay datos de prueba (plantas y usuarios)
```

### 3. API accesible

Probar en el navegador:
```
http://localhost/plantas_api/api/plants.php
```

Debe retornar un JSON con el listado de plantas.

---

## 🔧 INSTALACIÓN DE HERRAMIENTAS

### Paso 1: Descargar e Instalar Postman

1. Ir a: **https://www.postman.com/downloads/**
2. Descargar la versión para Windows
3. Ejecutar el instalador
4. Crear cuenta gratuita (opcional pero recomendado)
5. Abrir Postman

**Captura recomendada para tesis:** Pantalla de inicio de Postman

---

## ⚙️ CONFIGURACIÓN DEL ENTORNO

### Paso 1: Verificar la URL base de tu API

Antes de importar, verifica cuál es tu URL:

- Si usas `localhost`: `http://localhost/plantas_api`
- Si usas IP local: `http://192.168.X.X/plantas_api`

Anota tu URL, la necesitarás después.

---

## 📥 IMPORTACIÓN DE COLECCIÓN DE TESTS

### Paso 1: Importar la colección

1. Abrir Postman
2. Click en el botón **"Import"** (esquina superior izquierda)
3. Click en **"Upload Files"**
4. Navegar a: `D:\xampp\htdocs\plantas_api\`
5. Seleccionar archivo: **`Plantas_API_Tests.postman_collection.json`**
6. Click **"Import"**

✅ Verás aparecer en el panel izquierdo: **"Plantas Medicinales API - Tests Tesis"**

**Captura recomendada para tesis:** Colección importada en Postman

### Paso 2: Importar el entorno (variables)

1. Click en **"Import"** nuevamente
2. Click en **"Upload Files"**
3. Seleccionar archivo: **`Plantas_API_Environment.postman_environment.json`**
4. Click **"Import"**
5. En la esquina superior derecha, seleccionar el entorno: **"Plantas API - Entorno Local"**

**Captura recomendada para tesis:** Entorno seleccionado

### Paso 3: Configurar la URL base

1. Click en el ícono del ojo 👁️ (esquina superior derecha)
2. Click en **"Edit"** junto a "Plantas API - Entorno Local"
3. Modificar el valor de `base_url` si es necesario
   - Por defecto: `http://localhost/plantas_api`
   - Si usas IP: `http://TU_IP/plantas_api`
4. Click **"Save"**

---

## 🧪 EJECUCIÓN DE PRUEBAS

### MÉTODO 1: Ejecutar todos los tests de una vez (RECOMENDADO)

1. Click derecho sobre la colección **"Plantas Medicinales API - Tests Tesis"**
2. Seleccionar **"Run collection"**
3. Se abre el **Collection Runner**
4. Verificar que todos los tests estén seleccionados
5. Click en el botón **"Run Plantas Medicinales API..."**

⏱️ **Tiempo estimado:** 5-10 segundos

**Capturas CRÍTICAS para tesis:**
- Pantalla del Collection Runner mostrando todos los tests
- Resultados con tests PASSED en verde

### MÉTODO 2: Ejecutar caso por caso (para capturas detalladas)

#### CASO 10: Smoke Tests

1. Expandir carpeta **"CASO 10 - Smoke Tests"**
2. Click en **"1. Health Check - API responde"**
3. Click en botón azul **"Send"**
4. Revisar en la sección **"Test Results"** (abajo):
   - ✅ API responde correctamente
   - ✅ Respuesta es JSON
   - ✅ Tiempo de respuesta < 2 segundos

**Captura para tesis:** Test Results mostrando PASSED (3/3 tests)

5. Repetir con **"2. Login endpoint disponible"**

---

#### CASO 1: Validación de Credenciales

1. Expandir carpeta **"CASO 1 - Validación Credenciales"**

**Test 1: Credenciales vacías**
2. Click en **"1. Login con credenciales vacías"**
3. Click **"Send"**
4. Verificar Test Results:
   - ❌ Rechaza username vacío con código 400
   - ❌ Retorna mensaje de error específico

**Captura para tesis:** Response mostrando error 400

**Test 2: Credenciales válidas**
5. Click en **"2. Login con credenciales válidas"**
6. Click **"Send"**
7. Verificar Test Results (4 tests):
   - ✅ Acepta credenciales válidas con código 200
   - ✅ Retorna token de autenticación
   - ✅ Retorna datos del usuario
   - ✅ No retorna password en la respuesta

**Captura CRÍTICA para tesis:** Response mostrando token y datos de usuario

**Test 3: Credenciales inválidas**
8. Click en **"3. Login con credenciales inválidas"**
9. Click **"Send"**
10. Verificar Test Results:
    - ❌ Rechaza credenciales incorrectas con código 401
    - ❌ Retorna mensaje de error

---

#### CASO 5: Tests de Endpoints API

1. Expandir carpeta **"CASO 5 - Tests Endpoints API"**

**Test 1: Listar plantas**
2. Click en **"GET - Listar todas las plantas"**
3. Click **"Send"**
4. Verificar Test Results (4 tests):
   - ✅ Código de respuesta 200
   - ✅ Retorna array de plantas
   - ✅ Plantas tienen estructura correcta
   - ✅ Tiempo de respuesta < 1 segundo

**Captura para tesis:** JSON con array de plantas

**Test 2: Obtener planta por ID**
5. Click en **"GET - Obtener planta por ID"**
6. Click **"Send"**
7. Verificar datos de planta individual con usos medicinales

**Captura para tesis:** Detalle de planta con medicinal_uses, preparation, precautions

**Test 3: Versión del modelo**
8. Click en **"GET - Versión del modelo IA"**
9. Click **"Send"**

---

#### CASO 12: Búsqueda de Plantas

1. Expandir carpeta **"CASO 12 - Búsqueda de Plantas"**

**Test 1: Búsqueda por nombre**
2. Click en **"1. Búsqueda por nombre común"**
3. Click **"Send"**
4. Verificar que encuentra "manzanilla"
5. Verificar Test Results (4 tests):
   - ✅ Búsqueda ejecuta correctamente
   - ✅ Retorna estructura paginada
   - ✅ Resultados coinciden con búsqueda
   - ✅ Tiempo de respuesta < 1 segundo

**Captura para tesis:** Resultados de búsqueda con paginación

**Test 2: Filtro por familia botánica**
6. Click en **"2. Búsqueda por familia botánica"**
7. Click **"Send"**
8. Verificar que todas las plantas son de familia "Asteraceae"
9. Verificar ordenamiento alfabético

**Captura IMPORTANTE para tesis:** Resultados filtrados por familia

**Test 3: Paginación**
10. Click en **"3. Búsqueda con paginación"**
11. Click **"Send"**
12. Verificar que retorna máximo 5 resultados

---

#### CASO 8: Seguridad SQL Injection

1. Expandir carpeta **"CASO 8 - Seguridad SQL Injection"**

**Test 1: SQL Injection en búsqueda**
2. Click en **"1. Búsqueda con SQL Injection básico"**
3. Observar que el query contiene: `' OR 1=1--`
4. Click **"Send"**
5. Verificar Test Results:
   - ✅ No genera error de SQL
   - ✅ Retorna 0 resultados o resultados legítimos

**Captura MUY IMPORTANTE para tesis:** Query malicioso bloqueado

**Test 2: SQL Injection en login**
6. Click en **"2. Login con SQL Injection"**
7. Observar username: `admin'--`
8. Click **"Send"**
9. Verificar que NO bypasea la autenticación

**Captura CRÍTICA para tesis:** Intento de bypass rechazado

---

## 📊 INTERPRETACIÓN DE RESULTADOS

### Colores en Postman

- ✅ **Verde (PASSED):** Test exitoso
- ❌ **Rojo (FAILED):** Test falló
- ⚠️ **Amarillo:** Advertencia

### Ejemplo de resultado exitoso

```
✓ API responde correctamente
✓ Respuesta es JSON
✓ Tiempo de respuesta < 2 segundos

Tests: 3/3 passed
```

### Ejemplo de resultado fallido

```
✗ Código de respuesta 200
  Expected 500 to be 200

Tests: 0/3 passed
```

---

## 📸 CAPTURAS PARA LA TESIS

### Capturas OBLIGATORIAS

1. **Pantalla principal de Postman** con la colección importada
2. **Collection Runner** mostrando ejecución de todos los tests
3. **Resumen final** con total de tests PASSED
4. **CASO 1 - Login exitoso** con token generado
5. **CASO 12 - Búsqueda por familia** mostrando filtrado correcto
6. **CASO 8 - SQL Injection bloqueado** con respuesta controlada

### Capturas OPCIONALES (pero recomendadas)

7. Test Results de cada caso individual
8. Estructura JSON de respuestas
9. Tiempos de respuesta
10. Console de Postman con logs

### Formato recomendado para capturas

- **Formato:** PNG (mejor calidad)
- **Resolución:** Pantalla completa
- **Herramienta:** Windows + Shift + S (Recorte de Windows)
- **Nombrado:** `Figura_X_Caso_Y_Descripcion.png`

Ejemplo:
```
Figura_1_Postman_Coleccion_Importada.png
Figura_2_Caso1_Login_Exitoso.png
Figura_3_Caso8_SQL_Injection_Bloqueado.png
```

---

## 📋 RESULTADOS ESPERADOS

### Tabla de Resultados (para incluir en tesis)

| Caso | Nombre | Tests Totales | Tests Esperados PASS | Tiempo Estimado |
|------|--------|---------------|----------------------|-----------------|
| CASO 10 | Smoke Tests | 2 | 2 | < 2s |
| CASO 1 | Validación Credenciales | 7 | 7 | < 1s |
| CASO 5 | Tests Endpoints | 7 | 7 | < 3s |
| CASO 12 | Búsqueda Plantas | 10 | 10 | < 3s |
| CASO 8 | Seguridad SQL | 4 | 4 | < 2s |
| **TOTAL** | **5 casos** | **30** | **30** | **< 11s** |

### Criterios de éxito

✅ **Todos los tests deben pasar (30/30)**

Si algún test falla:
1. Verificar que Apache y MySQL estén corriendo
2. Verificar que la base de datos tiene datos
3. Verificar la URL en el entorno de Postman
4. Revisar logs en `plantas_api/logs/`

---

## 🔄 EJECUCIÓN AUTOMATIZADA (AVANZADO)

### Usando Newman (CLI de Postman)

Para automatizar la ejecución desde terminal:

```bash
# Instalar Newman (requiere Node.js)
npm install -g newman

# Ejecutar tests
newman run Plantas_API_Tests.postman_collection.json \
  -e Plantas_API_Environment.postman_environment.json \
  --reporters cli,html \
  --reporter-html-export resultados-tests.html
```

Esto genera un reporte HTML profesional en `resultados-tests.html`.

**Captura PREMIUM para tesis:** Reporte HTML de Newman

---

## 📝 REDACCIÓN PARA LA TESIS

### Ejemplo de redacción

> **4.2 Procedimiento de Pruebas**
>
> Para la validación del backend se utilizó **Postman**, herramienta profesional para testing de APIs REST. Se implementaron **5 casos de prueba** cubriendo aspectos funcionales y de seguridad, con un total de **30 tests automatizados**.
>
> La ejecución se realizó mediante el **Collection Runner** de Postman, permitiendo la ejecución secuencial de todos los tests en aproximadamente **11 segundos**.
>
> **Tabla 1. Resultados de Pruebas del Backend**
>
> | Caso | Tipo | Tests | Resultado |
> |------|------|-------|-----------|
> | CASO 10 | Funcional | 2/2 | ✅ Exitoso |
> | CASO 1 | Funcional | 7/7 | ✅ Exitoso |
> | CASO 5 | Integración | 7/7 | ✅ Exitoso |
> | CASO 12 | Integración | 10/10 | ✅ Exitoso |
> | CASO 8 | Seguridad | 4/4 | ✅ Exitoso |
>
> Como se observa en la Figura X, todos los tests pasaron exitosamente, validando la correcta implementación de los endpoints REST y las medidas de seguridad contra inyección SQL.

---

## 🆘 SOLUCIÓN DE PROBLEMAS

### Error: "Could not get response"

**Causa:** Apache no está corriendo o URL incorrecta

**Solución:**
1. Abrir XAMPP Control Panel
2. Iniciar Apache
3. Verificar URL en el navegador primero

### Error: Tests fallan por timeout

**Causa:** Base de datos lenta o sin índices

**Solución:**
1. Verificar que MySQL está corriendo
2. Verificar conexión en `config.php`

### Error: "Error establishing database connection"

**Causa:** Credenciales incorrectas en config.php

**Solución:**
1. Abrir `config/config.php`
2. Verificar DB_HOST, DB_NAME, DB_USER, DB_PASS

---

## ✅ CHECKLIST FINAL

Antes de incluir los resultados en tu tesis:

- [ ] Postman instalado y funcionando
- [ ] Colección importada correctamente
- [ ] Entorno configurado con URL correcta
- [ ] Todos los tests ejecutados (30/30 passed)
- [ ] Capturas de pantalla tomadas
- [ ] Capturas guardadas con nombres descriptivos
- [ ] Reporte HTML generado (opcional con Newman)
- [ ] Tabla de resultados documentada
- [ ] Tiempos de respuesta registrados

---

## 📚 REFERENCIAS

- Postman Documentation: https://learning.postman.com/docs/
- Newman CLI: https://learning.postman.com/docs/running-collections/using-newman-cli/
- PHPUnit: https://phpunit.de/documentation.html

---

**Fecha de creación:** $(date +%Y-%m-%d)
**Versión de la colección:** 1.0
**Autor:** Sistema de Plantas Medicinales - Tesis

---

¡Éxitos con tu tesis! 🎓🌿
