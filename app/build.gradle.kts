plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.google.secrets.gradle)
}

android {
    namespace = "com.kostyarazboynik.kinopoisksearch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kostyarazboynik.kinopoisksearch"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
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
    implementation(project(":core:dagger"))
    implementation(project(":core:utils"))
    implementation(project(":domain"))
    implementation(project(":features:feature-movie-list"))
    implementation(project(":movie-api"))
    implementation(project(":movie-data"))
    implementation(project(":movie-database"))

//    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment.ktx)

    // view binding
    implementation(libs.viewbinding.property.delegate.full)

    // dagger dependency injection pattern
    kapt(libs.dagger.compiler)
    annotationProcessor(libs.dagger.processor)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    debugImplementation(libs.okhttp.logging.interceptor)
}