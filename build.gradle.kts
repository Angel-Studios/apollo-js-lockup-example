plugins {
    kotlin("multiplatform") version libs.versions.kotlin.get()
    id("com.apollographql.apollo3") version libs.versions.apollo.get()
}

group = "com.example.apolloJsLockup"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    maven("file://$rootDir/repository")
}

kotlin {
    js {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.apollo.api)
                implementation(libs.apollo.runtime)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.html)
            }
        }
    }
}

apollo {
    service("rocketreserver") {
        generateKotlinModels.set(true)
        schemaFile.set(file("./src/commonMain/graphql/rocketreserver/schema.graphqls"))
        generateApolloMetadata.set(true)
        generateAsInternal.set(true)
        alwaysGenerateTypesMatching.set(listOf(".*"))
        sourceFolder.set("rocketreserver")
        packageName.set("com.example.apolloJsLockup.graphql.rocketreserver")
        useSemanticNaming.set(true)
    }
}
