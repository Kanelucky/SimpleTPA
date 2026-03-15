plugins {
    id("java-library")
    id("org.allaymc.gradle.plugin") version "0.2.1"
    id("com.gradleup.shadow") version "9.3.0"
}

group = "org.kanelucky.simpletpa"
description = "A simple tpa plugin. With customizable Messages."
version = "0.1.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

allay {
    api = "0.26.0"

    plugin {
        entrance = "org.kanelucky.simpletpa.SimpleTPA"
        authors += "Kanelucky"
        website = "https://github.com/Kanelucky/SimpleTPA"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.34")
    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.34")
}

tasks.jar {
    archiveBaseName = "SimpleTPA"
    archiveVersion = "$version"
    archiveClassifier.set("")
}

tasks.shadowJar {
    archiveBaseName = "SimpleTPA"
    archiveVersion = "$version"
    archiveClassifier.set("")
}