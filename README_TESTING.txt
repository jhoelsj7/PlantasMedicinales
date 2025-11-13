═══════════════════════════════════════════════════════════
   GUÍA RÁPIDA: TESTING DESDE TERMINAL PARA TESIS
═══════════════════════════════════════════════════════════

🎯 OBJETIVO: Ejecutar tests profesionales desde TERMINAL y tomar
   capturas de pantalla de la salida en consola (no HTML).

═══════════════════════════════════════════════════════════
📦 HERRAMIENTAS QUE USARÁS
═══════════════════════════════════════════════════════════

✅ YA CONFIGURADAS (no instalar nada):
   1. JUnit + Robolectric → Tests unitarios
   2. Espresso → Tests de UI Android
   3. JaCoCo → Cobertura de código

❗ NECESITAS INSTALAR:
   1. Node.js → https://nodejs.org/ (versión LTS)
   2. Newman → Ejecuta: npm install -g newman

═══════════════════════════════════════════════════════════
🚀 COMANDOS PRINCIPALES (copia y pega)
═══════════════════════════════════════════════════════════

▶ 1. TESTS UNITARIOS - Validación (CP1)
   Comando:
   ./gradlew test --tests ValidationUtilsTest

   📸 Captura: Toda la terminal mostrando "19 tests passed"

---

▶ 2. TESTS UNITARIOS - Sesión (CP7)
   Comando:
   ./gradlew test --tests SessionManagerTest

   📸 Captura: Mostrando "11 tests passed"

---

▶ 3. TODOS LOS TESTS UNITARIOS
   Comando:
   ./gradlew test --console=verbose

   📸 Captura: Resumen con "31 tests completed"

---

▶ 4. TESTS INSTRUMENTADOS - IA (CP2)
   ⚠️ PRIMERO: Conecta dispositivo o inicia emulador

   Comando:
   ./gradlew connectedAndroidTest --tests PlantClassifierTest

   📸 Captura: Con métricas "Tiempo de inferencia: XXXms"

---

▶ 5. TESTS INSTRUMENTADOS - Smoke (CP10)
   Comando:
   ./gradlew connectedAndroidTest --tests SmokeTestSuite

   📸 Captura: Con "Tiempo de login: XXXms"

---

▶ 6. COBERTURA DE CÓDIGO
   Comando:
   ./gradlew testDebugUnitTest jacocoTestReport

   Ver resultado:
   cat app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.csv

   📸 Captura: Tabla CSV con porcentajes de cobertura

---

▶ 7. TESTS DE API (Newman)
   Comando:
   newman run Plantas_Medicinales_API.postman_collection.json --reporters cli

   📸 Captura: Tabla de resultados de 8 requests

═══════════════════════════════════════════════════════════
📸 CHECKLIST DE CAPTURAS (8 total)
═══════════════════════════════════════════════════════════

[ ] Captura 1: ValidationUtilsTest (19 tests)
[ ] Captura 2: SessionManagerTest (11 tests)
[ ] Captura 3: Todos tests unitarios (31 tests)
[ ] Captura 4: adb devices (dispositivo conectado)
[ ] Captura 5: PlantClassifierTest con tiempos
[ ] Captura 6: SmokeTestSuite con métricas
[ ] Captura 7: Cobertura CSV de JaCoCo
[ ] Captura 8: Newman API tests (8 requests)

═══════════════════════════════════════════════════════════
⏱️ TIEMPO ESTIMADO
═══════════════════════════════════════════════════════════

Instalar Newman: 5 minutos
Ejecutar todos los tests: 50 minutos
Tomar capturas: 10 minutos
TOTAL: ~65 minutos

═══════════════════════════════════════════════════════════
📋 ORDEN DE EJECUCIÓN RECOMENDADO
═══════════════════════════════════════════════════════════

1. Instala Newman (si no lo tienes)
2. Abre terminal en el proyecto
3. Ejecuta comandos 1, 2, 3 (tests unitarios)
4. Conecta dispositivo Android
5. Ejecuta comandos 4, 5 (tests instrumentados)
6. Ejecuta comando 6 (cobertura)
7. Ejecuta comando 7 (API con Newman)
8. Revisa que tengas las 8 capturas

═══════════════════════════════════════════════════════════
💡 TIPS PARA CAPTURAS PROFESIONALES
═══════════════════════════════════════════════════════════

✓ Maximiza la terminal a pantalla completa
✓ Aumenta tamaño de fuente (Ctrl + rueda mouse)
✓ Limpia pantalla antes: cls (Windows) o clear (Linux)
✓ Usa Win + Shift + S para capturar
✓ Nombra archivos: CP1_ValidationUtils.png

═══════════════════════════════════════════════════════════
📁 ARCHIVOS IMPORTANTES
═══════════════════════════════════════════════════════════

→ COMANDOS_TESTING_TERMINAL.md
  Guía detallada con explicaciones completas

→ Plantas_Medicinales_API.postman_collection.json
  Colección de Postman para tests de API

→ RESULTADOS_TESTING.csv
  Plantilla para documentar resultados en Excel

═══════════════════════════════════════════════════════════
🆘 SOLUCIÓN DE PROBLEMAS
═══════════════════════════════════════════════════════════

Problema: "./gradlew" no funciona
Solución: Usa "gradlew.bat" en Windows
   gradlew.bat test --tests ValidationUtilsTest

Problema: No encuentra dispositivo Android
Solución:
   adb devices          (ver dispositivos)
   adb kill-server      (reiniciar adb)
   adb start-server

Problema: Newman no instalado
Solución:
   npm install -g newman
   Si falla, ejecuta como administrador

═══════════════════════════════════════════════════════════

✅ TODO ESTÁ CONFIGURADO Y LISTO PARA EJECUTAR

Lee COMANDOS_TESTING_TERMINAL.md para detalles completos.

═══════════════════════════════════════════════════════════
