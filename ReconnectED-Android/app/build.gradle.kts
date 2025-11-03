plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.getreconnected.reconnected"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.getreconnected.reconnected"
        minSdk = 31
        targetSdk = 36
        versionCode = 10
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }
    splits {
        abi {
            // https://developer.android.com/build/configure-apk-splits#kts
            isEnable = true // enable multiple APKs per ABI
            reset() // reset included ABIs
            include("armeabi-v7a", "arm64-v8a", "x86_64")
            isUniversalApk = true
        }
    }
}

dependencies {
    implementation(libs.firebase.ai)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.datastore)
    implementation(libs.gson)
    implementation(libs.androidx.compose.foundation)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.ui)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.ui.auth)
    implementation(libs.firebase.ui)
    implementation(libs.googleid)
    implementation(libs.material)
    implementation(libs.play.services.auth)
    implementation(libs.vico.compose.m3)
    implementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.firebase.bom))
    implementation(libs.accompanist.drawablepainter)
    implementation("com.kizitonwose.calendar:compose:2.9.0")
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.junit)
}
