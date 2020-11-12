// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.google.gms:google-services:4.3.4")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.35.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

apply(plugin = "com.github.ben-manes.versions")

allprojects {
    repositories {
        google()
        jcenter()
    }

//    ext {
//        androidLibs = [
//                appcompat            : "androidx.appcompat:appcompat:1.2.0",
//                recyclerview         : "androidx.recyclerview:recyclerview-selection:1.1.0-rc03",
//                constraintlayout     : "androidx.constraintlayout:constraintlayout:2.0.4",
//                androidxCore         : "androidx.core:core-ktx:1.3.2",
//                lifecycle            : "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0",
//                lifecycleExtensions : "android.arch.lifecycle:extensions:1.1.1",
//                playServicesLocation: "com.google.android.gms:play-services-location:17.1.0",
//                material             : "com.google.android.material:material:1.2.1",
//                cardview             : "androidx.cardview:cardview:1.0.0",
//                roomRuntime          : "androidx.room:room-runtime:2.2.5",
//                picasso              : "com.squareup.picasso:picasso:2.71828",
//                dexter               : "com.karumi:dexter:6.2.1",
//                fragment             : "androidx.fragment:fragment-ktx:1.2.5",
//                navigationFragment   : "androidx.navigation:navigation-fragment-ktx:2.3.1",
//                navigationUi         : "androidx.navigation:navigation-ui-ktx:2.3.1",
//                idlingResource       : "androidx.test.espresso:espresso-idling-resource:3.3.0"
//        ]
//
//        androidKaptLibs = [
//                roomCompiler : "androidx.room:room-compiler:2.2.5"
//        ]
//
//        kotlinLibs = [
//                stdlib    : "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version",
//                coroutines: "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.0"
//        ]
//
//        libs = [
//                okhttp                    : "com.squareup.okhttp3:okhttp:4.9.0",
//                okhttpLoginInterceptor   : "com.squareup.okhttp3:logging-interceptor:4.9.0",
//                retrofit                  : "com.squareup.retrofit2:retrofit:2.9.0",
//                retrofitGsonConverter    : "com.squareup.retrofit2:converter-gson:2.9.0",
//                retrofitScalarsConverter : "com.squareup.retrofit2:converter-scalars:2.9.0",
//                koin                      : "org.koin:koin-android-viewmodel:2.1.6"
//        ]
//
//        testLibs = [
//                junit         : "junit:junit:4.13.1",
//                mockitoKotlin : "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0",
//                mockitoInline : "org.mockito:mockito-inline:3.6.0",
//                koinTest      : "org.koin:koin-test:2.1.6",
//                testShared    : project(":testShared")
//        ]
//
//        androidxTestLibs = [
//                archCoreTesting: "android.arch.core:core-testing:1.1.1"
//        ]
//
//        androidTestLibs = [
//                testRunner   : "androidx.test:runner:1.3.0",
//                espresso     : "androidx.test.espresso:espresso-contrib:3.3.0",
//                coreTesting  : "androidx.arch.core:core-testing:2.1.0",
//                rules        : "androidx.test:rules:1.3.0",
//                extJunit     : "androidx.test.ext:junit-ktx:1.1.2",
//                koinTest     : "org.koin:koin-test:2.1.6",
//                mockitoAndroid: "org.mockito:mockito-android:3.6.0",
//                mockWebServer: "com.squareup.okhttp3:mockwebserver:4.9.0",
//                okhttpIdling : "com.jakewharton.espresso:okhttp3-idling-resource:1.0.0",
//                navigation   : "androidx.navigation:navigation-testing:2.3.1"
////                fragmentTest :"androidx.fragment:fragment-testing:1.2.2"
//        ]
//
//    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                    reject("Release candidate")
                }
            }
        }
    }

    // optional parameters
    checkForGradleUpdate = false
    outputFormatter = "json"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}