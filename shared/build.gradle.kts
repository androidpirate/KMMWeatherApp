plugins {
    // Kotlin multiplatform Gradle plugin
    kotlin("multiplatform")
    // Android Gradle plugin
    id("com.android.library")
    // Kotlin serialization plugin - also requires runtime
    id("kotlinx-serialization")
}

// kotlin is the top-level block for multiplatform project configuration in the Gradle build script
kotlin {
    // Declares a particular target of a project
    // Android applications and libraries
    // You can only create one Android target per Gradle subproject.
    android()
    
    listOf(
        // Apple iOS simulator on x86_64 platforms
        iosX64(),
        // Apple iOS on ARM64 platforms (Apple iPhone 5s and newer)
        iosArm64(),
        //iosSimulatorArm64() sure all ios dependencies support this target
    ).forEach {
        it.binaries.framework {
            // Custom base name for the output file.
            // The final file name will be formed by
            // adding system-dependent prefix and postfix to this base name.
            baseName = "shared"
        }
    }

    // The sourceSets block describes source sets of the project.
    // A source set contains Kotlin source files that participate in compilations together,
    // along with their resources, dependencies, and language settings.
    sourceSets {
        val ktorVersion = "1.6.7"
        val coroutinesVersion = "1.6.0-native-mt"

        // Code and resources shared between all platforms.
        // Available in all multiplatform projects.
        // Used in all main compilations of a project.

        // With Kotlin Gradle DSL, the sections of predefined source sets should be marked by getting.
        val commonMain by getting {
            // Libraries used below will be available for android and iOS
            dependencies {
                // KTOR Core library dependency
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                // KTOR HTTP request logger dependency
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                // KTOR JSON serialization/deserialization dependency
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                // KTOR Json engine dependency
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                // Coroutines dependency - also requires platform specific runtime library
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion") {
                    // Required to get the -native-mt version of the library
                    version { strictly(coroutinesVersion)}
                }
            }
        }
        // Test code and resources shared between all platforms.
        // Available in all multiplatform projects.
        // Used in all test compilations of a project.
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                // Android engine dependency
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                // JSON engine dependency
                implementation("io.ktor:ktor-client-json-jvm:$ktorVersion")
                // OKHttp dependency
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }
        //val iosSimulatorArm64Main by getting

        // Custom source set
        // This is created to defined all dependencies for iOS platform under one common source set
        val iosMain by creating {
            // Defined common dependencies for iosMain
            dependsOn(commonMain)
            // Define iosMain for iosX64Main and iosArm64Main
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            //iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                // iOS engine dependency
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        //val iosSimulatorArm64Test by getting
        // Custom source set
        // This is created to defined all dependencies for iOS platform under one common source set
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            //iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}