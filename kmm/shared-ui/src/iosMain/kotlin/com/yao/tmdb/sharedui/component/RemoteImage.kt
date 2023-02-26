package com.yao.tmdb.sharedui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.rememberAsyncImagePainter
import com.yao.tmdb.sharedui.util.commonConfig
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@Composable
internal actual fun __RemoteImage(imageUrl: String, modifier: Modifier, contentDescription: String?) {
    CompositionLocalProvider(
        LocalImageLoader provides generateImageLoader()
    ) {
        val resource = rememberAsyncImagePainter(
            url = imageUrl,
            imageLoader = LocalImageLoader.current,
        )

        Image(
            painter = resource,
            contentDescription = contentDescription,
            modifier = modifier,
        )
    }
}

private fun generateImageLoader(): ImageLoader {
    return ImageLoader {
        commonConfig()
        components {
            setupDefaultComponents(imageScope)
        }
        interceptor {
            memoryCacheConfig {
                // Set the max size to 25% of the app's available memory.
                maxSizePercent(0.25)
            }
            diskCacheConfig {
                directory(getCacheDir().toPath().resolve("image_cache"))
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}

private fun getCacheDir(): String {
    return NSFileManager.defaultManager.URLForDirectory(
        NSCachesDirectory,
        NSUserDomainMask,
        null,
        true,
        null,
    )!!.path.orEmpty()
}