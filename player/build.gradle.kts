plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.kotlincompose)
    alias(libs.plugins.detekt)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.sample.videoplayer.player"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation(project(":designsystem"))
    implementation(project(":commonmodule"))
    implementation(project(":datastoragemodule"))

    // kotlin core
    implementation(libs.androidx.core.ktx)
    // compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.ui.ui.graphics)
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.androidx.lifecycle.runtime.compose.v270)
    // coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlin.stdlib.v1924)
    // immutable collections
    implementation(libs.immutable.collections)
    // image loading
    implementation(libs.coil)
    implementation(libs.coil.compose)
    // Compose Animation
    implementation(libs.androidx.animation)

    // DataStore Preferences
    implementation(libs.androidx.datastore.preferences)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)

    // hilt
    implementation(libs.google.dagger.hilt)
    ksp(libs.google.dagger.hilt.compiler)

    // Navigation
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose.v280alpha08)

    // timber
    implementation(libs.timber)

    // Detekt compose rules
    detektPlugins(libs.detekt)

    // Exoplayer
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.common)

    // Work manager
    implementation(libs.androidx.work.runtime.ktx)

    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.androidx.core.testing)
}
