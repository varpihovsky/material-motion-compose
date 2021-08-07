import dependencies.Versions
import dependencies.Libs
import org.jetbrains.compose.compose
import org.jetbrains.kotlin.psi.psiUtil.checkReservedPrefixWord

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
}

kotlin {
    android()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Migrated to kts because I have no idea how to get compose object in groovy
                implementation(compose.ui)
                implementation(compose.foundation)
            }

        }
        val androidTest by getting {
            dependencies {
                implementation(Libs.Compose.ui_test_junit4)
                implementation(Libs.Compose.ui_test_manifest)
                implementation(Libs.AndroidX.Test.rules)
                implementation(Libs.AndroidX.Test.runner)
            }
        }
    }
}

android {
    compileSdkVersion(Versions.compileSdk)
    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)

        // testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        buildConfig = false
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "${Libs.Compose.version}"
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

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.fornewid"
                artifactId = "material-motion-compose"
                version = "0.6.2"
            }
        }
    }
}