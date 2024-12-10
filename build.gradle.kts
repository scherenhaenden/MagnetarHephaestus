import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.21" // Or your latest version
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "com.magnetar.hephaestus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    gradlePluginPortal()  // Add this if needed
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1") // For JSON parsing
    implementation("org.yaml:snakeyaml:2.0") // For YAML parsing
    implementation("androidx.compose.material:material-icons-extended-desktop:1.7.5") // Material Icons
    runtimeOnly("guru.nidi:graphviz-kotlin:0.18.1")
    implementation("guru.nidi:graphviz-kotlin:0.18.1")
    implementation("guru.nidi:graphviz-java:0.18.1")
    implementation("com.beust:klaxon:5.6") // Or the latest version
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")

    //implementation("androidx.compose.ui:ui:1.7.5") // Compose UI library
    //implementation("androidx.compose.foundation:foundation:1.7.5") // Foundation for gestures
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MagnetarHephaestus"
            packageVersion = "1.0.0"
        }
    }
}
