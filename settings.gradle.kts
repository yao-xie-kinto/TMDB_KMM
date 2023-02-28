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
//Android App
include(":android")
//iOS App
include(":ios")
//Shared modules
include(
    //Data
    ":kmm:data",
    //Domain
    ":kmm:domain",
    //UI Wrapper
    ":kmm:shared-ui"
)