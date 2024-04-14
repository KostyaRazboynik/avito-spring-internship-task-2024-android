plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    id("kotlin-parcelize")
}

android {
    namespace = "com.kostyarazboynik.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    implementation(project(":core:utils"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.dagger)
}
