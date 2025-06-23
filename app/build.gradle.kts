plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.app_ban_hang"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.app_ban_hang"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
//    implementation(libs.car.ui.lib)
    implementation(libs.legacy.support.v4)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("com.google.android.material:material:1.6.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0") //thư viện sử dụng cho API
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")//thư viện sử dụng cho API
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")

    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")// thư viện test api
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")//  thư viện test api


}