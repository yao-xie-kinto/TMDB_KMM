pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TMDB_KMM"
include(":android")
include(":ios")
include(":kmm:shared-ui")
include(":kmm:data")
include(":kmm:domain")
include(":kmm:feature")