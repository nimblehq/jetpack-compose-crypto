plugins {
    id("com.android.library")
    id("kotlin-android")

    id("plugins.jacoco-report")
}

android {
    compileSdk = Versions.ANDROID_COMPILE_SDK_VERSION
    defaultConfig {
        minSdk = Versions.ANDROID_MIN_SDK_VERSION
        targetSdk = Versions.ANDROID_TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            )
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = false
            /**
             * From AGP 4.2.0, Jacoco generates the report incorrectly, and the report is missing
             * some code coverage from module. On the new version of Gradle, they introduce a new
             * flag [testCoverageEnabled], we must enable this flag if using Jacoco to capture
             * coverage and creates a report in the build directory.
             * Reference: https://developer.android.com/reference/tools/gradle-api/7.1/com/android/build/api/dsl/BuildType#istestcoverageenabled
             */
            isTestCoverageEnabled = true
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    lintOptions {
        isCheckDependencies = true
        xmlReport = true
        xmlOutput = file("build/reports/lint/lint-result.xml")
    }
}

dependencies {
    implementation(project(Module.CORE))
    implementation(project(Module.DOMAIN))

    implementation("com.google.dagger:hilt-android:${Versions.HILT_VERSION}")
    implementation("com.squareup.moshi:moshi:${Versions.MOSHI_VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}")
    implementation("javax.inject:javax.inject:${Versions.JAVAX_INJECT_VERSION}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES_VERSION}")

    // Testing
    testImplementation("junit:junit:${Versions.TEST_JUNIT_VERSION}")
    testImplementation("io.mockk:mockk:${Versions.TEST_MOCKK_VERSION}")
    testImplementation("io.kotest:kotest-assertions-core:${Versions.TEST_KOTEST_VERSION}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLINX_COROUTINES_VERSION}")
}
