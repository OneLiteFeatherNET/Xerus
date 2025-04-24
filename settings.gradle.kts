rootProject.name = "xerus"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://eldonexus.de/repository/maven-public/")
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        maven {
            name = "OneLiteFeatherRepository"
            url = uri("https://repo.onelitefeather.dev/onelitefeather")
            if (System.getenv("CI") != null) {
                credentials {
                    username = System.getenv("ONELITEFEATHER_MAVEN_USERNAME")
                    password = System.getenv("ONELITEFEATHER_MAVEN_PASSWORD")
                }
            } else {
                credentials(PasswordCredentials::class)
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            version("bom", "1.2.0")
            version("aves", "1.6.1")
            version("junit.platform", "1.12.2")
            version("publishdata", "1.4.0")

            library("bom.base", "net.onelitefeather.mycelium.bom", "mycelium-bom").versionRef("bom")
            library("adventure", "net.kyori", "adventure-text-minimessage").withoutVersion()
            library("minestom", "net.minestom", "minestom-snapshots").withoutVersion()
            library("cyano", "net.onelitefeather.cyano", "cyano").withoutVersion()
            library("aves", "de.icevizion.lib", "aves").versionRef("aves")
            library("junit.api", "org.junit.jupiter", "junit-jupiter-api").withoutVersion()
            library("junit.engine", "org.junit.jupiter", "junit-jupiter-engine").withoutVersion()
            library("mockito.core", "org.mockito", "mockito-core").withoutVersion()
            library("mockito.junit", "org.mockito", "mockito-junit-jupiter").withoutVersion()
            library("junit.platform.launcher", "org.junit.platform", "junit-platform-launcher").versionRef("junit.platform")

            plugin("publishdata", "de.chojo.publishdata").versionRef("publishdata")
        }
    }
}
