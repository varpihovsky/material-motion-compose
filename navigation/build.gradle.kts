import dependencies.Libs
import dependencies.Versions
import org.jetbrains.compose.compose

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.compose")
}

android {
    compileSdkVersion(Versions.compileSdk)
    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Libs.Compose.version
    }
    lintOptions {
        textReport = true
        textOutput = file("stdout")
        isCheckReleaseBuilds = false
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    packagingOptions {
        // Multiple dependencies bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        excludes += "/META-INF/AL2.0"
        excludes += "/META-INF/LGPL2.1"
    }
}

dependencies {
    implementation(project(":library"))

    api(Libs.Compose.navigation)
    implementation(Libs.Compose.activity)

    implementation(compose.animation)
    implementation(compose.foundation)
    implementation(compose.material)

    // ======================
    // Test dependencies
    // ======================

    androidTestImplementation(Libs.Compose.ui_test_junit4)
    androidTestImplementation(Libs.Compose.ui_test_manifest)
    androidTestImplementation(Libs.AndroidX.Test.rules)
    androidTestImplementation(Libs.AndroidX.Test.runner)
    androidTestImplementation(Libs.AndroidX.Test.truth)
}
