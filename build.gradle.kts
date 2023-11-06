import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.ktorfit)
}

group = "com.lionzxy"
version = "1.0-SNAPSHOT"

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)

    implementation(libs.compose.kamel)
    implementation(libs.compose.paging)
    implementation(libs.compose.decompose)
    implementation(libs.compose.decompose.jetbrains)

    implementation(libs.ktor.client)
    implementation(libs.ktor.negotiation)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.serialization)

    implementation(libs.kotlin.immutable.collections)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.datetime)


    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    implementation(libs.ktorfit.lib)
    ksp(libs.ktorfit.ksp)

    implementation(libs.protobuf.kotlinlite)
    implementation(libs.protobuf.protoc)
    implementation(libs.flipper.protobuf)

    implementation(libs.jserial)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "FlipperMultiplatform"
            packageVersion = "1.0.0"
        }
    }
}
