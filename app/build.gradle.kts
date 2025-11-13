plugins {
    alias(libs.plugins.android.application)
    id("jacoco")
}

android {
    namespace = "com.tuapp.plantasmedicinales"
    compileSdk = 36

    aaptOptions {
        noCompress("tflite")
    }

    // Configuración para tests con salida detallada en terminal
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
            all {
                it.testLogging {
                    events("passed", "skipped", "failed", "standardOut", "standardError")
                    showExceptions = true
                    showCauses = true
                    showStackTraces = true
                    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
                }
            }
        }
    }

    defaultConfig {
        applicationId = "com.tuapp.plantasmedicinales"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.activity)

    // Testing - Unitarias (test/)
    testImplementation(libs.junit)
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.3.1")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("com.google.truth:truth:1.1.5")
    testImplementation("org.robolectric:robolectric:4.11.1")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // Testing - Instrumentadas (androidTest/)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")
    androidTestImplementation("com.google.truth:truth:1.1.5")
    androidTestImplementation("org.mockito:mockito-android:5.3.1")

    // Room Testing
    testImplementation("androidx.room:room-testing:2.5.2")
    androidTestImplementation("androidx.room:room-testing:2.5.2")

    // Retrofit Testing
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    implementation("org.tensorflow:tensorflow-lite:2.17.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4") {
        exclude(group = "org.tensorflow", module = "tensorflow-lite")
        exclude(group = "org.tensorflow", module = "tensorflow-lite-api")
    }
    implementation("org.tensorflow:tensorflow-lite-gpu:2.17.0") {
        exclude(group = "org.tensorflow", module = "tensorflow-lite-api")
    }

    // Retrofit para API REST
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // RecyclerView y CardView para listas
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.cardview:cardview:1.0.0")


    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.karumi:dexter:6.2.3")
}

// Configuración de JaCoCo para cobertura de código
tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(false)
        html.required.set(false)
        csv.required.set(true) // Solo CSV para ver en terminal
    }

    // Mostrar resumen en consola
    doLast {
        println("\n===========================================")
        println("REPORTE DE COBERTURA DE CÓDIGO")
        println("===========================================")
        println("Archivo generado: ${reports.csv.outputLocation.get()}")
        println("Ver con: cat ${reports.csv.outputLocation.get()}")
        println("===========================================\n")
    }
}