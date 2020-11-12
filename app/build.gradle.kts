plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.3"

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode(1)
        versionName("1.0.0")
        multiDexEnabled = true
        applicationId("com.txusmslabs.templateapp")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug") {
            debuggable(true)
            isMinifyEnabled = true
        }
        getByName("release") {
            minifyEnabled(false)
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":usecases"))
    testImplementation(project(":testShared"))

    Libs.androidLibs.forEach {
        implementation(it)
    }
    Libs.kotlinLibs.forEach {
        implementation(it)
    }
    Libs.libs.forEach {
        implementation(it)
    }
    Libs.androidKaptLibs.forEach {
        kapt(it)
    }

    Libs.testLibs.forEach {
        testImplementation(it)
    }
    Libs.androidxTestLibs.forEach {
        testImplementation(it)
    }
    Libs.androidTestLibs.forEach {
        androidTestImplementation(it)
    }

    // Testing code should not be included in the main code.
    // Once https://issuetracker.google.com/128612536 is fixed this can be fixed.
    debugImplementation("androidx.fragment:fragment-testing:1.2.5")
    implementation("androidx.test:core:1.3.0")
}