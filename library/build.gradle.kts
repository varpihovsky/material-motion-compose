import dependencies.Versions
import dependencies.Libs
import org.jetbrains.compose.compose
import org.jetbrains.kotlin.psi.psiUtil.checkReservedPrefixWord

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
    id("org.jetbrains.compose")
}

group = "soup.compose.material"
version = "0.6.4"

kotlin {
    android{
        publishLibraryVariants("release")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Migrated to kts because I have no idea how to get compose object in groovy
                implementation(compose.ui)
                implementation(compose.foundation)
            }

        }
    }
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.github.fornewid"
                artifactId = "material-motion-compose"
                version = "0.6.4"
            }
        }
    }
}

android {
    compileSdk = Versions.compileSdk
    sourceSets["main"].manifest.srcFile("src/commonMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    packagingOptions {
        // Multiple dependencies bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        exclude("/META-INF/AL2.0")
        exclude("/META-INF/LGPL2.1")
    }
}