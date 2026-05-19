// settings.gradle.kts — Root settings file for the Kreeda-Ankana project
// Defines the project name and included modules

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Kreeda-Ankana"
include(":app")
