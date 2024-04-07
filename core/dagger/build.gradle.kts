plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

java {
    sourceCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
    targetCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
}

dependencies {
    api(libs.dagger)
}