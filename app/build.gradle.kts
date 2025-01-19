plugins {
//    alias(libs.plugins.androidLibrary)
//    alias(libs.plugins.kotlinAndroid)
//    alias(libs.plugins.ksp)
//    alias(libs.plugins.aboutLibraries)
//    alias(libs.plugins.kapt)
//    alias(libs.plugins.hilt)
    id("com.android.application") version "8.2.2"
//    id("com.android.library") version "8.2.2"
    id("org.jetbrains.kotlin.android") version "1.9.0"
    id("com.google.devtools.ksp") version "1.9.0-1.0.12"
    id("com.mikepenz.aboutlibraries.plugin") version "10.8.3"
    id("org.jetbrains.kotlin.kapt") version "1.9.0"
    id("com.google.dagger.hilt.android") version "2.46"
}

android {
    namespace = "com.mckimquyen.libresudoku"
    compileSdk = 34

    defaultConfig {
//        applicationId = "com.mckimquyen.libresudoku"
        minSdk = 26
        targetSdk = 34
        versionCode = 20240722
        versionName = "2024.07.22"

        vectorDrawables {
            useSupportLibrary = true
        }

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
//            isShrinkResources = true
//            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    buildTypes.all { isCrunchPngs = false }

//    flavorDimensions.add("type")
//    productFlavors {
//        create("dev") {
//            dimension = "type"
//            //            buildConfigField("String", "FLAVOR_buildEnv", "dev")
//
//            resValue ("string", "app_name", "Sudoku in the Universe DEV")
//
//            resValue("string", "SDK_KEY", "e75FnQfS9XTTqM1Kne69U7PW_MBgAnGQTFvtwVVui6kRPKs5L7ws9twr5IQWwVfzPKZ5pF2IfDa7lguMgGlCyt")
//            resValue("string", "BANNER", "")
//            resValue("string", "INTER", "")
//
//            resValue("string", "EnableAdInter", "true")
//            resValue("string", "EnableAdBanner", "true")
//        }
//        create("production") {
//            dimension = "type"
//            //            buildConfigField("String", "FLAVOR_buildEnv", "prod")
//
//            resValue ("string", "app_name", "Sudoku in the Universe")
//
//            resValue("string", "SDK_KEY", "e75FnQfS9XTTqM1Kne69U7PW_MBgAnGQTFvtwVVui6kRPKs5L7ws9twr5IQWwVfzPKZ5pF2IfDa7lguMgGlCyt")
//            resValue("string", "BANNER", "")
//            resValue("string", "INTER", "")
//
//            resValue("string", "EnableAdInter", "true")
//            resValue("string", "EnableAdBanner", "true")
//        }
//    }
//    android.buildFeatures.dataBinding = true
}

aboutLibraries {
    excludeFields = arrayOf("generated")
}

dependencies {
//    implementation(libs.core.ktx)
//    implementation(libs.lifecycle.runtime.ktx)
//    implementation(libs.lifecycle.runtime.compose)
//    implementation(libs.activity.compose)
//    implementation(libs.ui)
//    implementation(libs.ui.util)
//    implementation(libs.ui.graphics)
//    implementation(libs.ui.tooling.preview)
//    implementation(libs.material3)
//    implementation(libs.material.icons.extended)
//    debugImplementation(libs.ui.tooling)
//    debugImplementation(libs.ui.test.manifest)
//    implementation(libs.navigation.compose)
//    implementation(libs.accompanist.systemuicontroller)
//    implementation(libs.accompanist.pager.indicators)
//    implementation(libs.hilt)
//    implementation(libs.hilt.navigation)
//    kapt(libs.hilt.compiler)
//    implementation(libs.room.runtime)
//    implementation(libs.room.ktx)
//    ksp(libs.room.compiler)
//    implementation(libs.datastore.preferences)
//    implementation(libs.appcompat)
//    implementation(libs.acra.dialog)
//    implementation(libs.acra.mail)
//    implementation(libs.aboutLibraries)
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.3")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.ui:ui-util:1.6.8")
    implementation("androidx.compose.ui:ui-graphics:1.6.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material-icons-extended:1.6.8")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.8")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")
    implementation("com.google.dagger:hilt-android:2.46")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    kapt("com.google.dagger:hilt-compiler:2.46")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("ch.acra:acra-dialog:5.11.3")
    implementation("ch.acra:acra-mail:5.11.3")
    implementation("com.mikepenz:aboutlibraries-compose:10.8.3")
}
