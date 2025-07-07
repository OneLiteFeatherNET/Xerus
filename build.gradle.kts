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
    addBuildData()
    useGitlabReposForProject("89", "https://gitlab.onelitefeather.dev/")
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
            credentials(HttpHeaderCredentials::class) {
                name = "Job-Token"
                value = System.getenv("CI_JOB_TOKEN")
            }
            authentication {
                create("header", HttpHeaderAuthentication::class)
            }

            name = "Gitlab"
            // Get the detected repository from the publish data
            url = uri(publishData.getRepository())
        }
    }
}
