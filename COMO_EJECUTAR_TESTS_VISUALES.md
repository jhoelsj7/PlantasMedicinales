# 🎯 CÓMO EJECUTAR LOS TESTS VISUALES PARA TU TESIS

## ✅ LO QUE YA TIENES LISTO

He creado **3 archivos** para que puedas hacer los tests de forma visual y documentarlos en tu tesis:

### 📄 Archivos Creados:

1. **`TESTING_MANUAL_TESIS.md`**
   - 📋 Casos de prueba paso a paso
   - 🎯 19 tests manuales documentados
   - 📸 Indica dónde tomar capturas
   - ⏱️ Indica dónde medir tiempos

2. **`Plantas_Medicinales_API.postman_collection.json`**
   - 🔌 Colección de Postman lista para importar
   - 🌐 8 tests de API REST
   - 📝 Descripciones completas de cada endpoint

3. **`RESULTADOS_TESTING.csv`**
   - 📊 Plantilla para documentar resultados
   - ✏️ Abre con Excel/Google Sheets
   - 📈 Lista para copiar/pegar en tu tesis

---

## 🚀 PASO 1: TESTS EN LA APLICACIÓN (11 tests)

### ¿Qué vas a hacer?

Usar la aplicación directamente en tu celular/emulador, probar funciones y tomar capturas.

### Instrucciones:

1. **Abre el archivo:** `TESTING_MANUAL_TESIS.md`

2. **Instala la app en tu dispositivo:**
   - Conecta tu celular o abre el emulador
   - Ejecuta desde Android Studio: Run > Run 'app'

3. **Sigue cada caso de prueba:**
   - **CP1:** Login y validaciones (4 tests)
   - **CP2:** Clasificación con IA (4 tests)
   - **CP3:** Gestión de sesión (2 tests)
   - **CP4:** Funcionalidades principales (5 tests)

4. **Para cada test:**
   - ✅ Lee el procedimiento
   - 🎬 Ejecuta los pasos
   - ⏱️ Mide el tiempo donde se indica
   - 📸 Toma captura de pantalla
   - ✏️ Anota el resultado en `RESULTADOS_TESTING.csv`

### Ejemplo de Test Manual:

```
Test 1.1: Username vacío
1. Abre la app
2. Deja el campo Usuario VACÍO
3. Escribe password: "123456"
4. Presiona "Iniciar Sesión"
5. RESULTADO ESPERADO: Error "Usuario vacío"
6. 📸 CAPTURA: Pantalla con el error
7. ✏️ ANOTA: En Excel, columna "Resultado Obtenido"
```

---

## 🌐 PASO 2: TESTS CON POSTMAN (8 tests de API)

### ¿Qué vas a hacer?

Probar las APIs REST del backend usando Postman, como se hace en desarrollo web profesional.

### Instrucciones:

#### 2.1 Instalar Postman

1. Ve a: https://www.postman.com/downloads/
2. Descarga e instala Postman
3. Abre Postman

#### 2.2 Importar la Colección

1. En Postman, click en **"Import"** (esquina superior izquierda)
2. Arrastra el archivo: `Plantas_Medicinales_API.postman_collection.json`
3. Click **"Import"**
4. ✅ Verás la colección "Plantas Medicinales API - Testing Tesis"

#### 2.3 Configurar la URL del Servidor

1. En Postman, click en **"Environments"** (lado izquierdo)
2. Click **"+"** para crear nuevo Environment
3. Nombre: `Plantas Medicinales`
4. Agrega variable:
   - Variable: `API_URL`
   - Initial Value: `http://tu-servidor.com/api` (⚠️ REEMPLAZA con tu URL real)
   - Current Value: (lo mismo)
5. Click **"Save"**
6. Selecciona el Environment en el dropdown (esquina superior derecha)

#### 2.4 Ejecutar los Tests

Para cada test en la colección:

1. Click en el test (ej: "CP5.1 - GET Listar Todas las Plantas")
2. Lee la **descripción** en la pestaña "Description"
3. Click **"Send"**
4. Observa el resultado:
   - ✅ Status Code (debe ser 200 o 201)
   - 📄 Response Body (JSON con los datos)
5. 📸 **CAPTURA:** Pantalla completa de Postman mostrando:
   - Request enviado
   - Status Code
   - Response JSON
6. ✏️ **ANOTA:** En `RESULTADOS_TESTING.csv`

### Ejemplo de Test en Postman:

```
CP5.1 - GET Listar Todas las Plantas
1. Click en el test en Postman
2. Verifica que la URL sea: {{API_URL}}/plants.php
3. Click "Send"
4. RESULTADO ESPERADO:
   - Status: 200 OK
   - Body: JSON con array de plantas
5. 📸 CAPTURA: Postman mostrando el response
```

---

## 📊 PASO 3: DOCUMENTAR RESULTADOS

### Opción A: Usar Excel

1. Abre el archivo: `RESULTADOS_TESTING.csv` con Excel
2. Para cada test que ejecutaste:
   - Columna **"Resultado Obtenido":** Escribe lo que pasó
   - Columna **"Tiempo (seg)":** Anota el tiempo medido
   - Columna **"Estado":** Escribe ✅ (exitoso) o ❌ (falló)
   - Columna **"Observaciones":** Notas adicionales
   - Columna **"Ruta Captura":** Nombre del archivo de captura (ej: CP1_Test1.1.png)
3. Guarda el archivo

### Opción B: Copiar a Word

1. Abre Excel con el CSV completado
2. Selecciona la tabla
3. Copia (Ctrl + C)
4. Pega en tu documento de tesis Word
5. Formatea la tabla como gustes

---

## 📸 ORGANIZAR LAS CAPTURAS

### Estructura de Carpetas Recomendada:

```
📁 TestingTesis/
  📁 CP1_Validacion/
     📷 CP1_Test1.1_Username_Vacio.png
     📷 CP1_Test1.2_Username_Corto.png
     📷 CP1_Test1.3_Login_Exitoso.png
     📷 CP1_Test1.4_Password_Corto.png
  📁 CP2_IA/
     📷 CP2_Test2.1_Clasificacion_1.png
     📷 CP2_Test2.2a_Clasificacion_2.png
     ...
  📁 CP5_API_Postman/
     📷 CP5_Test5.1_GET_Plantas.png
     📷 CP5_Test5.2_GET_Buscar.png
     ...
```

---

## ⏱️ TIEMPOS ESTIMADOS

| Actividad | Tiempo |
|-----------|--------|
| Configurar Postman | 10 min |
| Tests CP1 (Validación) | 15 min |
| Tests CP2 (IA) | 20 min |
| Tests CP3 (Sesión) | 35 min* |
| Tests CP4 (Funcionalidades) | 15 min |
| Tests CP5 (API Postman) | 20 min |
| Documentar resultados | 20 min |
| **TOTAL** | **~2 horas** |

*El Test 3.2 requiere esperar 30 minutos de inactividad

---

## ✅ CHECKLIST DE EJECUCIÓN

### Antes de Empezar:
- [ ] Aplicación instalada en dispositivo/emulador
- [ ] Postman instalado
- [ ] Servidor API funcionando (si aplica)
- [ ] Capturas organizadas en carpetas

### Durante la Ejecución:
- [ ] CP1: 4 tests de validación ✅
- [ ] CP2: 4 tests de clasificación IA ✅
- [ ] CP3: 2 tests de sesión ✅
- [ ] CP4: 5 tests de funcionalidades ✅
- [ ] CP5: 8 tests de API en Postman ✅

### Después de Ejecutar:
- [ ] Todas las capturas guardadas
- [ ] CSV completado con resultados
- [ ] Tablas copiadas a documento de tesis
- [ ] Observaciones documentadas

---

## 📈 MÉTRICAS CLAVE PARA TU TESIS

Al finalizar los tests, podrás reportar:

✅ **Total de tests ejecutados:** 19 (app) + 8 (API) = **27 tests**
✅ **Tests exitosos:** ___ / 27
✅ **Porcentaje de éxito:** ___%
✅ **Tiempo promedio de login:** ___s
✅ **Tiempo promedio de clasificación IA:** ___s
✅ **Tiempo de respuesta API:** ___ms
✅ **Tasa de error:** ___%

---

## 🆘 SOLUCIÓN DE PROBLEMAS

### Problema: La app no se instala
**Solución:**
- Conecta el dispositivo via USB
- Habilita "Depuración USB" en Opciones de Desarrollador
- O usa el emulador de Android Studio

### Problema: Postman no puede conectar con la API
**Solución:**
- Verifica que la URL en `API_URL` sea correcta
- Verifica que el servidor esté corriendo
- Si es localhost, usa la IP de tu PC, no "localhost"
- Para emulador Android: usa `http://10.0.2.2:puerto`

### Problema: No puedo tomar capturas en Android
**Solución:**
- Presiona: Botón Power + Volumen Abajo simultáneamente
- Las capturas se guardan en Galería > Screenshots

---

## 📝 RESUMEN EJECUTIVO

Has recibido:

1. ✅ **Guía completa** de 19 tests manuales en la app
2. ✅ **Colección de Postman** con 8 tests de API listos
3. ✅ **Plantilla CSV** para documentar resultados
4. ✅ **Instrucciones detalladas** paso a paso

**Todo está listo para que ejecutes y documentes los tests visualmente en tu tesis.**

---

## 🎓 PRÓXIMOS PASOS

1. **Lee** `TESTING_MANUAL_TESIS.md` completo
2. **Instala** Postman
3. **Importa** la colección JSON
4. **Ejecuta** los tests uno por uno
5. **Captura** pantallas de cada resultado
6. **Documenta** en el CSV
7. **Incluye** en tu tesis

---

**¿Dudas?** Consulta `TESTING_MANUAL_TESIS.md` para detalles de cada test.

**Creado: 2025-11-13**
**Listo para usar ✅**
