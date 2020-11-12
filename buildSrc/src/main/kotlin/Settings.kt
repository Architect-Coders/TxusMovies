object Versions {
    const val kotlin = "1.4.20-RC"
    const val navigation = "2.3.1"
    const val retrofit = "2.9.0"
    const val okhttp = "4.9.0"
    const val koin = "2.2.0"
    const val mockito = "3.6.0"
}

object Libs {
    val androidLibs = listOf(
        "androidx.appcompat:appcompat:1.2.0",
        "androidx.recyclerview:recyclerview-selection:1.1.0-rc03",
        "androidx.constraintlayout:constraintlayout:2.0.4",
        "androidx.core:core-ktx:1.3.2",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0",
        "android.arch.lifecycle:extensions:1.1.1",
        "com.google.android.gms:play-services-location:17.1.0",
        "com.google.android.material:material:1.2.1",
        "androidx.cardview:cardview:1.0.0",
        "androidx.room:room-runtime:2.2.5",
        "com.squareup.picasso:picasso:2.71828",
        "com.karumi:dexter:6.2.1",
        "androidx.fragment:fragment-ktx:1.2.5",
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}",
        "androidx.navigation:navigation-ui-ktx:${Versions.navigation}",
        "androidx.test.espresso:espresso-idling-resource:3.3.0"
    )

    val androidKaptLibs = listOf(
        "androidx.room:room-compiler:2.2.5"
    )

    val kotlinLibs = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1"
    )

    val libs = listOf(
        "com.squareup.okhttp3:okhttp:${Versions.okhttp}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}",
        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}",
        "org.koin:koin-androidx-viewmodel:${Versions.koin}",
        "org.koin:koin-androidx-scope:${Versions.koin}"
    )

    val testLibs = listOf(
        "junit:junit:4.13.1",
        "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0",
        "org.mockito:mockito-inline:${Versions.mockito}",
        "org.koin:koin-test:${Versions.koin}"
//        project(":testShared")
    )

    val androidxTestLibs = listOf(
        "android.arch.core:core-testing:1.1.1"
    )

    val androidTestLibs = listOf(
        "androidx.test:runner:1.3.0",
        "androidx.test.espresso:espresso-contrib:3.3.0",
        "androidx.arch.core:core-testing:2.1.0",
        "androidx.test:rules:1.3.0",
        "androidx.test.ext:junit-ktx:1.1.2",
        "org.koin:koin-test:${Versions.koin}",
        "org.mockito:mockito-android:${Versions.mockito}",
        "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}",
        "com.jakewharton.espresso:okhttp3-idling-resource:1.0.0",
        "androidx.navigation:navigation-testing:2.3.1"
//                fragmentTest :"androidx.fragment:fragment-testing:1.2.2"
    )
}