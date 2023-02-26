@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    alias(libs.plugins.compse.jb)
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":kmm:data"))
                implementation(project(":kmm:domain"))
                implementation(project(":kmm:feature:base"))
                implementation(project(":kmm:feature:home"))
                implementation(project(":kmm:feature:search"))
                implementation(project(":kmm:feature:favourite"))
                implementation(project(":kmm:feature:settings"))
                implementation(libs.bundles.common)
                api(compose.ui)
                api(compose.foundation)
                api(compose.material)
                api(compose.runtime)
                api(compose.animation)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.coil)
                implementation(libs.coil.compose)
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                implementation(libs.koin.core)
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.yao.tmdb.sharedui"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}