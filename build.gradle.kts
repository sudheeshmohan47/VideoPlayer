// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.google.dagger.hilt) apply false
    alias(libs.plugins.roomPlugin) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.detekt.gradle.plugin)
        classpath(libs.org.jacoco.core)
    }
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
