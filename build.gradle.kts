plugins {
    java
    `java-library`
    `maven-publish`
    jacoco
    alias(libs.plugins.sonarqube)
}

group = "net.theevilreaper.xerus"
val baseVersion = "1.2.0-SNAPSHOT"
val sonarKey = "dungeon_projects_xerus_AYKjiRt9dAa6ziWsmMZw"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation(libs.strigiformes)

    compileOnly(libs.aves)
    compileOnly(libs.minestom)

    testImplementation(libs.minestom)
    testImplementation(libs.minestom.test)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit)
    testImplementation(libs.aves)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(21)
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
        jvmArgs("-Dminestom.inside-test=true")
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    getByName("sonar") {
        dependsOn(rootProject.tasks.test)
    }
}

version = if (System.getenv().containsKey("CI")) {
    "${baseVersion}+${System.getenv("CI_COMMIT_SHORT_SHA")}"
} else {
    baseVersion
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    if (System.getenv().containsKey("CI")) {
        repositories {
            maven {
                name = "GitLab"
                val ciApiv4Url = System.getenv("CI_API_V4_URL")
                val projectId = System.getenv("CI_PROJECT_ID")
                url = uri("$ciApiv4Url/projects/$projectId/packages/maven")
                credentials(HttpHeaderCredentials::class.java) {
                    name = "Job-Token"
                    value = System.getenv("CI_JOB_TOKEN")
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
        }
    }
}
