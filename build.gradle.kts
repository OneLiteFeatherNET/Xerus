plugins {
    java
    `java-library`
    `maven-publish`
    jacoco
}

group = "net.theevilreaper.xerus"
val baseVersion = "1.2.0-SNAPSHOT"
val sonarKey = "dungeon_projects_xerus_AYKjiRt9dAa6ziWsmMZw"

java {
   toolchain {
         languageVersion.set(JavaLanguageVersion.of(21))
   }
}

dependencies {
    implementation(platform(libs.dungeon.base.bom))

    compileOnly(libs.aves)
    compileOnly(libs.minestom)
    testImplementation(platform(libs.dungeon.base.bom))
    testImplementation(libs.minestom)
    testImplementation(libs.minestom.test)
    testImplementation(libs.junit.api)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit)
    testImplementation(libs.aves)
    testRuntimeOnly(libs.junit.engine)
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
