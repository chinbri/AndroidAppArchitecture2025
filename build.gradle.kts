buildscript {

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }


    dependencies {
    }
}

plugins {
    alias (libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt.gradle) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.navigation.safe.args.kotlin) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.crashlytics.gradle) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
}

// Root build.gradle.kts
