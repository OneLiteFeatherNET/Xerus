import org.sonarqube.gradle.SonarQubeTask

plugins {
    java
    alias(libs.plugins.sonarqube)
    jacoco
}

group = "net.theevilreaper.xerus"
val baseVersion = "1.2.0-SNAPSHOT"
val sonarKey = "dungeon_projects_xerus_AYKjiRt9dAa6ziWsmMZw"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

val strigiVersion = "e89dd8352c"

dependencies {
    implementation("com.github.PatrickZdarsky:Strigiformes:$strigiVersion")

    compileOnly("de.icevizion.lib:Aves:1.2.0-SNAPSHOT")
    compileOnly(libs.minestom)

    testImplementation(libs.minestom)
    testImplementation(libs.junitApi)
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.6.1")
    testImplementation("de.icevizion.lib:Aves:1.2.0-SNAPSHOT")
    testRuntimeOnly(libs.junitEngine)
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    jacocoTestReport {
        dependsOn(rootProject.tasks.test)
        reports {
            xml.required.set(true)
        }
    }

    test {
        finalizedBy(rootProject.tasks.jacocoTestReport)
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    getByName<SonarQubeTask>("sonarqube") {
        dependsOn(rootProject.tasks.test)
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", sonarKey)
    }
}

if (System.getenv().containsKey("CI")) {
    version = "${baseVersion}+${System.getenv("CI_COMMIT_SHORT_SHA")}"
} else {
    version = baseVersion
}
