import dependencies.Libs
import dependencies.Versions
import org.jetbrains.compose.compose

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "soup.material.compose.sample"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = 1
        versionName = "1.0"
    }
    signingConfigs {
        // We use a bundled debug keystore, to allow debug builds from CI to be upgradable
        getByName("debug") {
            storeFile = rootProject.file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }
    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Libs.Compose.version
    }
    packagingOptions {
        // Multiple dependencies bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        exclude("/META-INF/AL2.0")
        exclude("/META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(project(":library"))
    implementation(project(":navigation"))

    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.core_ktx)
    implementation(Libs.AndroidX.lifecycle_ktx)
    implementation(Libs.material)

    implementation(compose.ui)
    implementation(compose.uiTooling)
    implementation(compose.material)
    implementation(Libs.Compose.activity)
    implementation(Libs.Compose.constraintlayout)
}