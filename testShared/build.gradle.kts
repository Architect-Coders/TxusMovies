plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":domain"))
    Libs.kotlinLibs.forEach {
        implementation(it)
    }
    Libs.testLibs.forEach {
        testImplementation(it)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}
