import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.isKaptVerbose

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.yesitlab.compro"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yesitlab.compro"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }

}
kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.circleimageview)
    implementation(libs.imagepicker)
    implementation(libs.androidx.viewpager2)

//    implementation(libs.showmoretext)
    implementation(libs.glide)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
  //

    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("com.google.dagger:hilt-android:2.33-beta")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation("com.hbb20:android-country-picker:0.0.7")

    implementation("com.airbnb.android:lottie:3.4.0")
    implementation("com.github.taimoorsultani:android-sweetalert2:2.0.2")
    implementation(project(":network"))

}