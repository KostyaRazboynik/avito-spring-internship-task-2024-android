plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.kostyarazboynik.moviedata"
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
    implementation(project(":domain"))
    implementation(project(":movie-api"))
    implementation(project(":movie-database"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.retrofit)

    // dagger dependency injection pattern
    implementation(libs.dagger)
}