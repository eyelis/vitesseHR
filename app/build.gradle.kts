plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("com.google.dagger.hilt.android")
    //id("com.google.devtools.ksp")
   // kotlin("kapt")
    id("kotlin-kapt")
}

android {
    namespace = "com.vitesse.hr"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vitesse.hr"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


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
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    hilt {
        enableAggregatingTask = true
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material)
    //testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.kotlin.mockito.kotlin)

    //Room
    implementation(libs.androidx.room.ktx)
    //ksp(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)

    //dagger hilt
    implementation(libs.hilt.android)
    //implementation(libs.androidx.hilt.lifecycle.viewmodel)
    //ksp(libs.hilt.android.compiler)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)


    //nav
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.fragment)

    implementation(libs.kotlin.reflect)

    implementation(libs.androidx.material.icons.extended)

    implementation(libs.kotlinx.datetime)

    implementation(libs.coil.compose)

    implementation(libs.accompanist.permissions)

    implementation(libs.glideCompose)

 //   implementation(libs.datetime)

    // Retrofit
    implementation(libs.retrofit)
    //FIXME tech radar HOLD library
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    testImplementation(libs.mockwebserver)

}

kapt {
    correctErrorTypes = true
}