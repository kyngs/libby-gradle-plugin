/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Gradle plugin project to get you started.
 * For more details on writing Custom Plugins, please refer to https://docs.gradle.org/8.2.1/userguide/custom_plugins.html in the Gradle documentation.
 */

plugins {
    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    `java-gradle-plugin`
    `maven-publish`
    id("org.cadixdev.licenser").version("0.6.1")
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.grack:nanojson:1.7")

    compileOnly("com.github.johnrengelman:shadow:8.1.1")

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

gradlePlugin {
    plugins {
        create("xyz.kyngs.libby.plugin") {
            id = "xyz.kyngs.libby.plugin"
            implementationClass = "xyz.kyngs.libby.plugin.LibbyGradlePlugin"
            version = "1.0.0"
        }
    }
}

group = "xyz.kyngs.libby"

tasks.named<Test>("test") {
    // Use JUnit Jupiter for unit tests.
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
    withJavadocJar()
}

license {
    header(rootProject.file("HEADER.txt"))
    include("**/*.java")
    newLine(true)
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

publishing {
    repositories {
        maven {
            name = "kyngsRepo"
            url = uri(
                    "https://repo.kyngs.xyz/" + (if (project.version.toString()
                                    .contains("SNAPSHOT")
                    ) "snapshots" else "releases") + "/"
            )
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}

version = "1.0.0"
