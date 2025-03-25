plugins {
    java
    `java-library`
    `maven-publish`
    jacoco
    alias(libs.plugins.publishdata)
}

group = "net.theevilreaper.xerus"
version = "1.3.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation(platform(libs.bom.base))
    compileOnly(libs.minestom)
    compileOnly(libs.aves)
    testImplementation(platform(libs.bom.base))
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

publishData {
    addMainRepo("https://repo.onelitefeather.dev/onelitefeather-releases")
    addMasterRepo("https://repo.onelitefeather.dev/onelitefeather-releases")
    addSnapshotRepo("https://repo.onelitefeather.dev/onelitefeather-snapshots")
    publishTask("jar")
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            // configure the publication as defined previously.
            publishData.configurePublication(this)
            version = publishData.getVersion(false)
        }
    }
    repositories {
        maven {
            authentication {
                credentials(PasswordCredentials::class) {
                    // Those credentials need to be set under "Settings -> Secrets -> Actions" in your repository
                    username = System.getenv("ONELITEFEATHER_MAVEN_USERNAME")
                    password = System.getenv("ONELITEFEATHER_MAVEN_PASSWORD")
                }
            }

            name = "OneLiteFeatherRepository"
            // Get the detected repository from the publish data
            url = uri(publishData.getRepository())
        }
    }
}
