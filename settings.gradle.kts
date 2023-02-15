pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "TMDB_KMM"
include(":android")
include(":ios")
include(":kmm:data")
include(":kmm:domain")
include(":kmm:feature")
include(":kmm:feature:base")
include(":kmm:feature:home")
include(":kmm:feature:search")
include(":kmm:feature:favourite")
include(":kmm:feature:settings")
include(":kmm:shared-ui")
