object Versions {
    const val appCompat = "1.3.0"
    const val coroutines = "1.5.0"
    const val fragment = "1.3.4"
    const val koin = "3.0.2"
    const val kotlin = "1.5.10"
    const val lifecycle = "2.3.1"
    const val material = "1.3.0"
    const val mockito = "3.11.0"
    const val navigation = "2.3.5"
    const val okhttp = "4.9.1"
    const val retrofit = "2.9.0"
    const val room = "2.3.0"
}

object Libs {
    val androidLibs = listOf(
        "androidx.appcompat:appcompat:${Versions.appCompat}",
        "androidx.cardview:cardview:1.0.0",
        "androidx.constraintlayout:constraintlayout:2.0.4",
//        "androidx.core:core-ktx:1.3.2",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}",
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}",
        "androidx.navigation:navigation-ui-ktx:${Versions.navigation}",
        "androidx.fragment:fragment-ktx:${Versions.fragment}",
        "androidx.recyclerview:recyclerview-selection:1.1.0",
        "androidx.room:room-runtime:${Versions.room}",
        "com.google.android.gms:play-services-location:18.0.0",
        "com.google.android.material:material:${Versions.material}",
        "com.karumi:dexter:6.2.2",
        "com.squareup.picasso:picasso:2.71828"
    )

    val androidKaptLibs = listOf(
        "androidx.room:room-compiler:${Versions.room}"
    )

    val kotlinLibs = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    )

    val libs = listOf(
        "com.squareup.okhttp3:okhttp:${Versions.okhttp}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}",
        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}",
        "io.insert-koin:koin-android:${Versions.koin}"
    )

    val testLibs = listOf(
        "junit:junit:4.13.2",
        "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0",
        "org.mockito:mockito-inline:${Versions.mockito}",
        "io.insert-koin:koin-test:${Versions.koin}"
//        project(":testShared")
    )

    val androidxTestLibs = listOf(
//        "android.arch.core:core-testing:1.1.1",
        "androidx.arch.core:core-testing:2.1.0"
    )

    val androidTestLibs = listOf(
        "androidx.arch.core:core-testing:2.1.0",
        "androidx.navigation:navigation-testing:${Versions.navigation}",
        "androidx.test.espresso:espresso-contrib:3.3.0",
        "androidx.test.ext:junit-ktx:1.1.2",
        "androidx.test:rules:1.3.0",
        "androidx.test:runner:1.3.0",
        "com.jakewharton.espresso:okhttp3-idling-resource:1.0.0",
        "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}",
        "io.insert-koin:koin-test:${Versions.koin}",
        "org.mockito:mockito-android:${Versions.mockito}"
//        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
//                fragmentTest :"androidx.fragment:fragment-testing:1.2.2"
    )

    val androidxDebugTestLibs = listOf(
        "androidx.fragment:fragment-testing:${Versions.fragment}"
    )
}