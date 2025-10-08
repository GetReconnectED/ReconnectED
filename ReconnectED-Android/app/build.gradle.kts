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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.room.runtime)
     val roomVersion = "2.8.1"

    implementation(libs.vico.compose.m3)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.appcompat)
    //implementation(libs.androidx.constraintlayout)
    //implementation(libs.androidx.lifecycle.livedata.ktx)
    //implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //implementation(libs.androidx.navigation.fragment.ktx)
    //implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    implementation(libs.androidx.compose.material.icons.extended)
    //implementation(libs.androidx.room.common.jvm)
    //implementation(libs.androidx.room.ktx)
    //implementation(libs.androidx.work.runtime.ktx)~
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    //androidTestImplementation(libs.androidx.ui.test.junit4)
    //debugImplementation(libs.androidx.ui.tooling)
    //debugImplementation(libs.androidx.ui.test.manifest)

    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
//    ksp("androidx.room:room-compiler:$roomVersion")

    // If this project only uses Java source, use the Java annotationProcessor
    // No additional plugins are necessary
    //annotationProcessor("androidx.room:room-compiler:$roomVersion")

    // optional - Kotlin Extensions and Coroutines support for Room
    //implementation("androidx.room:room-ktx:$roomVersion")

    // optional - RxJava2 support for Room
    //implementation("androidx.room:room-rxjava2:$roomVersion")

    // optional - RxJava3 support for Room
    //implementation("androidx.room:room-rxjava3:$roomVersion")

    // optional - Guava support for Room, including Optional and ListenableFuture
    //implementation("androidx.room:room-guava:$roomVersion")

    // optional - Test helpers
    //testImplementation("androidx.room:room-testing:$roomVersion")

    // optional - Paging 3 Integration
    //implementation("androidx.room:room-paging:$roomVersion")

    // Firebase dependencies
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.splashscreen)
}
