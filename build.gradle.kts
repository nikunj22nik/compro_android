// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.5.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.42")


    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    alias(libs.plugins.android.library) apply false
}