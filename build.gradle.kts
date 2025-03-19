plugins {
    kotlin("jvm") version "2.1.10"
    id("com.google.devtools.ksp") version "2.1.10-1.0.31"
    id("de.jensklingenberg.ktorfit") version "2.4.1"
}

group = "com.github.theapache64"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Ktorfit
    implementation("de.jensklingenberg.ktorfit:ktorfit-lib:2.4.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}