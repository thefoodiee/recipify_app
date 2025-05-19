import com.android.build.api.dsl.Packaging
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
    alias(libs.plugins.google.gms.google.services)
}
val apiKey: String = project.findProperty("RECIPE_API_KEY") as? String ?: ""
android {
    namespace = "com.recipify"
    compileSdk = 35//load the values from .properties file

    // Exclude this file from being packaged, since it's duplicated
    packaging{
        // Exclude this file from being packaged, since it's duplicated
        resources.excludes.add("META-INF/gradle/incremental.annotation.processors")
    }

        defaultConfig {
            applicationId = "com.recipify"
            minSdk = 26
            targetSdk = 35
            versionCode = 1
            versionName = "1.0"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            //load the values from .properties file
            val keystoreFile = project.rootProject.file("app/keys.properties")
            val properties = Properties()
            properties.load(keystoreFile.inputStream())
            val apiKey = properties.getProperty("RECIPE_API_KEY") ?: ""

            buildConfigField("String", "RECIPE_API_KEY", value = apiKey)
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
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        kotlinOptions {
            jvmTarget = "11"
        }
        buildFeatures {
            compose = true
            buildConfig = true
        }
    }


    dependencies {
    implementation(libs.androidx.contentpager)
    implementation(libs.androidx.room.compiler)
        implementation(libs.firebase.auth)
        implementation(libs.androidx.credentials)
        implementation(libs.androidx.credentials.play.services.auth)
        implementation(libs.googleid)
        implementation(libs.androidx.runtime.livedata)
        implementation(libs.androidx.espresso.core)
        implementation(libs.androidx.animation.core.lint)
        val nav_version = "2.8.9"
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("io.coil-kt.coil3:coil-svg:3.1.0")
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("androidx.core:core-splashscreen:1.0.1")
        implementation ("io.coil-kt:coil-compose:2.4.0")
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

// Hilt with Navigation Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    val lifecycle_version = "2.9.0"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")

    // Annotation processor
    ksp("com.google.dagger:dagger-compiler:2.51.1")

    val room_version = "2.7.1"

    implementation("androidx.room:room-runtime:$room_version")
        implementation("androidx.compose.material:material-icons-extended:1.6.1")

        // Import the BoM for the Firebase platform
//        implementation (platform("com.google.firebase:firebase-bom:31.0.0"))

        // Declare the dependency for the Firebase Authentication library
//        implementation("com.google.firebase:firebase-auth-ktx")
}

configurations.all {
    exclude(group = "com.intellij", module = "annotations")
}
