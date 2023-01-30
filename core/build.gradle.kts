plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")

    id("dagger.hilt.android.plugin")
}

android {
    namespace = "co.nimblehq.compose.crypto.core"
    compileSdk = Versions.ANDROID_COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = Versions.ANDROID_MIN_SDK_VERSION
        targetSdk = Versions.ANDROID_TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion = Versions.KOTLIN_VERSION
        kotlinCompilerExtensionVersion = Versions.COMPOSE_VERSION
    }

    buildFeatures {
        compose = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    api("androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX_VERSION}")
    api("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE_VERSION}")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.ANDROIDX_LIFECYCLE_VERSION}")
    api("androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE_VERSION}")

    api("androidx.constraintlayout:constraintlayout-compose:${Versions.COMPOSE_CONSTRAINT_LAYOUT_VERSION}")
    api("androidx.compose.ui:ui:${Versions.COMPOSE_VERSION}")
    api("androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE_VERSION}")
    api("androidx.compose.foundation:foundation:${Versions.COMPOSE_VERSION}")
    api("androidx.compose.material:material:${Versions.COMPOSE_VERSION}")

    api("androidx.navigation:navigation-compose:${Versions.ANDROIDX_NAVIGATION_COMPOSE_VERSION}")

    api("com.google.dagger:hilt-android:${Versions.HILT_VERSION}")
    api("androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAVIGATION_COMPOSE_VERSION}")

    api("com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM_VERSION}")

    api("io.coil-kt:coil-compose:${Versions.COIL_VERSION}")

    api("com.jakewharton.timber:timber:${Versions.TIMBER_LOG_VERSION}")

    api("com.github.nimblehq:android-common-ktx:${Versions.ANDROID_COMMON_KTX_VERSION}")

    api("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES_VERSION}")

    api("io.github.bytebeats:compose-charts:${Versions.COMPOSE_CHART_VERSION}")

    api("com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT_VERSION}")
    api("com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}")

    api("com.squareup.moshi:moshi-adapters:${Versions.MOSHI_VERSION}")
    api("com.squareup.moshi:moshi-kotlin:${Versions.MOSHI_VERSION}")

    api("com.squareup.okhttp3:okhttp:${Versions.OKHTTP_VERSION}")
    api("com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_VERSION}")

    implementation("androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX_VERSION}")
    implementation("androidx.security:security-crypto:${Versions.ANDROID_CRYPTO_VERSION}")

    kapt("com.google.dagger:hilt-compiler:${Versions.HILT_VERSION}")
}
