rootProject.name = "Xerus"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven {
            val groupdId = "dungeon" // Gitlab Group
            url = if (System.getenv().containsKey("CI")) {
                val ciApiv4Url = System.getenv("CI_API_V4_URL")
                uri("$ciApiv4Url/groups/$groupdId/-/packages/maven")
            } else {
                uri("https://gitlab.themeinerlp.dev/api/v4/groups/$groupdId/-/packages/maven")
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

    versionCatalogs {
        create("libs") {
            version("minestom", "1.3.1")
            version("junit", "5.10.2")
            version("mockito", "5.11.0")
            library("minestom", "net.onelitefeather.microtus", "Minestom").versionRef("minestom")
            library("minestom-test", "net.onelitefeather.microtus.testing", "testing").versionRef("minestom")
            library("junit-jupiter", "org.junit.jupiter", "junit-jupiter").versionRef("junit")
            library("junit-jupiter-engine", "org.junit.jupiter", "junit-jupiter-engine").versionRef("junit")
            library("mockito-core", "org.mockito", "mockito-core").versionRef("mockito")
            library("mockito-junit", "org.mockito", "mockito-junit-jupiter").versionRef("mockito")
            library("aves", "de.icevizion.lib", "Aves").version("1.3.1+dc3a1da8")
            library("strigiformes", "com.github.PatrickZdarsky", "Strigiformes").version("e89dd8352c")

            plugin("sonarqube", "org.sonarqube").version("4.0.0.2929")
        }
    }
}
