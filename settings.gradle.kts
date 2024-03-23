rootProject.name = "Xerus"

dependencyResolutionManagement {
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
