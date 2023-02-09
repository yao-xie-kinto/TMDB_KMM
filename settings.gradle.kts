pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
}

rootProject.name = "TMDB_KMM"
include(":android")
include(":ios")
include(":kmm:shared-ui")
include(":kmm:data")
include(":kmm:domain")
include(":kmm:feature")