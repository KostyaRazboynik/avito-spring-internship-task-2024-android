plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.kostyarazboynik.common"
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
    implementation(libs.androidx.appcompat)
}
