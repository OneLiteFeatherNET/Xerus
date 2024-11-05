rootProject.name = "Xerus"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://eldonexus.de/repository/maven-public/")
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        maven("https://jitpack.io")
        maven {
            val groupdId = "dungeon" // Gitlab Group
            url = if (System.getenv().containsKey("CI")) {
                val ciApiv4Url = System.getenv("CI_API_V4_URL")
                uri("$ciApiv4Url/groups/$groupdId/-/packages/maven")
            } else {
                uri("https://gitlab.onelitefeather.dev/api/v4/groups/$groupdId/-/packages/maven")
            }
            name = "GitLab"
            credentials(HttpHeaderCredentials::class.java) {
                name = if (System.getenv().containsKey("CI")) {
                    "Job-Token"
                } else {
                    "Private-Token"
                }
                value = if (System.getenv().containsKey("CI")) {
                    System.getenv("CI_JOB_TOKEN")
                } else {
                    val gitLabPrivateToken: String? by settings
                    gitLabPrivateToken
                }
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
    if (System.getenv("CI") != null) {
        repositoriesMode = RepositoriesMode.PREFER_SETTINGS
        repositories {
            maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            maven("https://repo.htl-md.schule/repository/Gitlab-Runner/")
            maven {
                val groupdId = 28 // Gitlab Group
                val ciApiv4Url = System.getenv("CI_API_V4_URL")
                url = uri("$ciApiv4Url/groups/$groupdId/-/packages/maven")
                name = "GitLab"
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
    versionCatalogs {
        create("libs") {
            version("bom", "1.1.1")
            version("aves", "1.5.3")
            version("publishdata", "1.4.0")

            library("bom.base", "net.theevilreaper.dungeon.bom", "base").versionRef("bom")
            library("minestom", "net.onelitefeather.microtus", "Microtus").withoutVersion()
            library("minestom-test", "net.onelitefeather.microtus.testing", "testing").withoutVersion()
            library("aves", "de.icevizion.lib", "aves").versionRef("aves")
            library("junit.api", "org.junit.jupiter", "junit-jupiter-api").withoutVersion()
            library("junit.engine", "org.junit.jupiter", "junit-jupiter-engine").withoutVersion()
            library("mockito.core", "org.mockito", "mockito-core").withoutVersion()
            library("mockito.junit", "org.mockito", "mockito-junit-jupiter").withoutVersion()
            plugin("publishdata", "de.chojo.publishdata").versionRef("publishdata")
        }
    }
}
