// App-level build.gradle.kts
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") // Correct Kotlin plugin ID
}

android {
    namespace = "com.example.pkfarm"
    compileSdk = 34
    buildToolsVersion = "28.0.0"

    defaultConfig {
        applicationId = "com.example.pkfarm"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    signingConfigs {
        create("release") {
            storeFile = file("release-key.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "your-keystore-password"
            keyAlias = "my_key"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "your-key-password"
        }
    }
}

kotlin {
    jvmToolchain(21) // Ensure Kotlin uses JDK 21
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
}
