pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Kinopoisk Quick Search"
include(":app")
include(":core:dagger")
include(":core:utils")
include(":domain")
include(":features:feature-movie-details")
include(":features:feature-movie-list")
include(":movie-api")
include(":movie-data")
include(":movie-database")
