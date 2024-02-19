rootProject.name = "Xerus"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("minestom", "net.onelitefeather.microtus", "Minestom").version("1.3.1")
            library("minestom-test", "net.onelitefeather.microtus.testing", "testing").version("1.1.1")
            library("junit-jupiter", "org.junit.jupiter", "junit-jupiter").version("5.10.0")
            library("junit-jupiter-engine", "org.junit.jupiter", "junit-jupiter-engine").version("5.10.0")
            library("mockito-core", "org.mockito", "mockito-core").version("5.7.0")
            library("mockito-junit", "org.mockito", "mockito-junit-jupiter").version("5.7.0")
            library("aves", "de.icevizion.lib", "Aves").version("1.3.1+dc3a1da8")
            library("strigiformes", "com.github.PatrickZdarsky", "Strigiformes").version("e89dd8352c")

            plugin("sonarqube", "org.sonarqube").version("4.0.0.2929")
        }
    }
}
