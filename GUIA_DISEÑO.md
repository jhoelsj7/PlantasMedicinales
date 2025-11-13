# 🎨 GUÍA COMPLETA DE DISEÑO - PLANTAS MEDICINALES

## ✅ **LO QUE YA ESTÁ LISTO**

He creado estos archivos para ti:

### **1. Paleta de Colores (`res/values/colors.xml`)**
```xml
@color/green_primary         - #4CAF50 (Verde principal para botones)
@color/green_primary_dark    - #2E7D32 (Verde oscuro para toolbar)
@color/green_light_bg        - #F1F8E9 (Fondo verde claro)
@color/green_soft            - #A5D6A7 (Cards y acentos)
@color/brown_earth           - #795548 (Texto secundario)
@color/cream                 - #FFF8E1 (Fondos alternos)
```

### **2. Fondos/Drawables Creados (`res/drawable/`)**
```
✅ bg_button_primary.xml      - Botón verde con gradiente
✅ bg_button_secondary.xml    - Botón blanco con borde verde
✅ bg_edittext.xml            - Campo de texto redondeado
✅ bg_card.xml                - Card con sombra
✅ bg_gradient_green.xml      - Fondo gradiente verde
```

---

## 📥 **PASO 4: DESCARGAR IMÁGENES**

### **A) ICONOS MATERIAL (Ya vienen en Android - No descargar)**

Para agregar iconos Material Design sin descargar:

1. **En Android Studio:**
   - Click derecho en `res/drawable`
   - `New` → `Vector Asset`
   - Click en el icono de Android
   - Busca estos nombres:

```
camera_alt       → Para "Identificar Planta"
format_list_bulleted → Para "Lista de Plantas"
search           → Para "Buscar"
sync             → Para "Sincronizar Datos"
eco              → Para logo de la app
local_florist    → Icono alternativo bonito
spa              → Para decoración
nature_people    → Alternativa
```

5. Dale nombre: `ic_camera`, `ic_list`, etc.
6. Click `Finish`

### **B) LOGO DE LA APP (Opcional - Si quieres uno custom)**

**Opción 1 - Usar Material Icon (Recomendado - Gratis):**
- Usa el icono `eco` o `local_florist` que viene con Android
- Ya está disponible como `@drawable/ic_eco` después de agregarlo

**Opción 2 - Descargar de Flaticon:**
1. Ve a: https://www.flaticon.com/free-icons/leaf
2. Busca "medical leaf" o "herb"
3. Descarga GRATIS en PNG 512x512
4. Arrastra a `res/drawable/`
5. Renombra a: `logo_plantas.png`

### **C) FONDO PARA LOGIN (Opcional pero bonito)**

**Opción 1 - Usar el gradiente que ya creé:**
- Ya está listo en `@drawable/bg_gradient_green`
- No necesitas descargar nada

**Opción 2 - Imagen de hojas (Si quieres algo más elaborado):**
1. Ve a: https://unsplash.com/s/photos/green-leaves-texture
2. Busca una imagen que te guste
3. Descarga en tamaño mediano (1920x1080)
4. Arrastra a `res/drawable/`
5. Renombra a: `bg_leaves.jpg`

---

## 🎨 **PASO 5: APLICAR EL DISEÑO**

### **EJEMPLO 1: LoginActivity con diseño mejorado**

Copia este código en `activity_login.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fillViewport="true"
    android:background="@drawable/bg_gradient_green">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="32dp"
        android:gravity="center_vertical">

        <Space
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"/>

        <!-- Logo de la app -->
        <ImageView
            android:layout_width="120dp"
            android:layout_height="120dp"
            android:src="@drawable/ic_eco"
            android:layout_gravity="center"
            android:tint="@color/green_primary_dark"
            android:layout_marginBottom="16dp"
            android:contentDescription="Logo Plantas Medicinales"/>

        <!-- Título -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="Plantas Medicinales"
            android:textSize="26sp"
            android:textStyle="bold"
            android:textColor="@color/green_primary_dark"
            android:layout_marginBottom="8dp"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="Identifica plantas con IA"
            android:textSize="14sp"
            android:textColor="@color/brown_earth"
            android:layout_marginBottom="48dp"/>

        <!-- Campo Usuario -->
        <EditText
            android:id="@+id/etUsername"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Usuario"
            android:inputType="text"
            android:maxLines="1"
            android:background="@drawable/bg_edittext"
            android:textColor="@color/grey_dark"
            android:textColorHint="@color/grey_medium"
            android:layout_marginBottom="16dp"/>

        <!-- Campo Contraseña -->
        <EditText
            android:id="@+id/etPassword"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Contraseña"
            android:inputType="textPassword"
            android:maxLines="1"
            android:background="@drawable/bg_edittext"
            android:textColor="@color/grey_dark"
            android:textColorHint="@color/grey_medium"
            android:layout_marginBottom="32dp"/>

        <!-- Botón Login -->
        <Button
            android:id="@+id/btnLogin"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Iniciar Sesión"
            android:textColor="@color/white"
            android:textStyle="bold"
            android:textSize="16sp"
            android:background="@drawable/bg_button_primary"
            android:elevation="4dp"/>

        <Space
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"/>

    </LinearLayout>
</ScrollView>
```

### **EJEMPLO 2: MainActivity con iconos Material**

```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fillViewport="true"
    android:background="@color/green_light_bg">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="24dp"
        android:gravity="center_vertical">

        <Space
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"/>

        <!-- Título -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="Plantas Medicinales"
            android:textSize="28sp"
            android:textStyle="bold"
            android:textColor="@color/green_primary_dark"
            android:layout_marginBottom="40dp"/>

        <!-- Botón Identificar con Icono -->
        <LinearLayout
            android:id="@+id/btnCamera"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center"
            android:background="@drawable/bg_button_primary"
            android:padding="16dp"
            android:layout_marginBottom="16dp"
            android:clickable="true"
            android:focusable="true">

            <ImageView
                android:layout_width="24dp"
                android:layout_height="24dp"
                android:src="@drawable/ic_camera_alt"
                android:tint="@color/white"
                android:layout_marginEnd="12dp"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Identificar Planta"
                android:textColor="@color/white"
                android:textSize="16sp"
                android:textStyle="bold"/>
        </LinearLayout>

        <!-- Botón Lista con Icono -->
        <LinearLayout
            android:id="@+id/btnPlantList"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center"
            android:background="@drawable/bg_button_secondary"
            android:padding="16dp"
            android:layout_marginBottom="16dp"
            android:clickable="true"
            android:focusable="true">

            <ImageView
                android:layout_width="24dp"
                android:layout_height="24dp"
                android:src="@drawable/ic_format_list_bulleted"
                android:tint="@color/green_primary"
                android:layout_marginEnd="12dp"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Lista de Plantas"
                android:textColor="@color/green_primary"
                android:textSize="16sp"
                android:textStyle="bold"/>
        </LinearLayout>

        <!-- Similar para los demás botones... -->

        <Space
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"/>

    </LinearLayout>
</ScrollView>
```

---

## 🔧 **PASO 6: CÓMO USAR LOS RECURSOS**

### **En cualquier Layout XML:**

```xml
<!-- Aplicar fondo gradiente verde -->
android:background="@drawable/bg_gradient_green"

<!-- Aplicar color verde primario -->
android:textColor="@color/green_primary"

<!-- Botón con estilo primario -->
android:background="@drawable/bg_button_primary"

<!-- Campo de texto redondeado -->
android:background="@drawable/bg_edittext"

<!-- Card con sombra -->
android:background="@drawable/bg_card"

<!-- Usar icono Material -->
android:src="@drawable/ic_eco"
```

### **Cambiar color de icono (tint):**

```xml
<ImageView
    android:src="@drawable/ic_camera_alt"
    android:tint="@color/green_primary"/>
```

---

## 📱 **PASO 7: AGREGAR ICONOS MATERIAL DESIGN**

### **Para cada icono que necesites:**

1. Click derecho en `res/drawable`
2. `New` → `Vector Asset`
3. Click en el icono de Android (junto a "Clip Art:")
4. Busca el icono (ej: "camera")
5. Cambia el nombre a `ic_camera_alt`
6. Click `Next` → `Finish`

**Lista de iconos recomendados:**
```
✅ camera_alt           → Cámara
✅ photo_library        → Galería
✅ format_list_bulleted → Lista
✅ search               → Buscar
✅ sync                 → Sincronizar
✅ eco                  → Eco/Planta (para logo)
✅ local_florist        → Flor
✅ check_circle         → Check verde
✅ warning              → Advertencia
✅ info                 → Información
```

---

## 🖼️ **URLS DIRECTAS PARA DESCARGAR (Opcionales)**

### **Logos Gratuitos:**
1. https://www.flaticon.com/free-icon/leaf_2913133
2. https://www.flaticon.com/free-icon/herbal_3081648
3. https://www.flaticon.com/free-icon/medicine_3774299

### **Fondos de Hojas (Unsplash - Gratis):**
1. https://unsplash.com/photos/green-leafed-plant-wpOa2i3MUrY
2. https://unsplash.com/photos/green-leafed-plants-during-daytime-ZVprbBmT8QA
3. https://unsplash.com/photos/green-leaf-plant-decors-xcMlnvFfI8A

*(Click en "Download free" en cada imagen)*

---

## 📋 **RESUMEN RÁPIDO**

### **Ya tienes listos (sin descargar nada):**
✅ Paleta de colores completa
✅ Fondos con gradientes
✅ Botones redondeados
✅ Cards con sombra
✅ Campos de texto estilizados

### **Puedes agregar (usando Material Icons - gratis):**
✅ Todos los iconos que necesites
✅ Sin descargar archivos externos
✅ Ya incluidos en Android Studio

### **Opcional (si quieres más personalización):**
📥 Logo custom de Flaticon
📥 Foto de fondo de Unsplash

---

## 💡 **SIGUIENTE PASO**

¿Quieres que:
1. **Te aplique el diseño automáticamente** a todas las pantallas?
2. **Te muestre cómo queda visualmente** cada pantalla?
3. **Te guíe pantalla por pantalla** para que lo hagas tú?

¡Dime y continuamos! 🌿✨
