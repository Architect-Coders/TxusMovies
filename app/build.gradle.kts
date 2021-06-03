plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode(1)
        versionName("1.0.0")
        multiDexEnabled = true
        applicationId("com.txusmslabs.templateapp")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("debug") {
            debuggable(true)
            isMinifyEnabled = false
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
    Libs.androidxDebugTestLibs.forEach {
        debugImplementation(it)
    }
}