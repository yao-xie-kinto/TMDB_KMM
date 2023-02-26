package com.yao.tmdb.sharedui.component // ktlint-disable filename

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.rememberAsyncImagePainter
import com.yao.tmdb.sharedui.ContextUtil
import com.yao.tmdb.sharedui.util.commonConfig
import okio.Path.Companion.toOkioPath

@Composable
internal actual fun __RemoteImage(
    imageUrl: String,
    modifier: Modifier,
    contentDescription: String?
) {
    val painter = rememberAsyncImagePainter(
        url = imageUrl,
        imageLoader = generateImageLoader()
    )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

private fun generateImageLoader(): ImageLoader {
    return ImageLoader {
        commonConfig()
        components {
            setupDefaultComponents(ContextUtil.getContext()!!)
        }
        interceptor {
            memoryCacheConfig {
                // Set the max size to 25% of the app's available memory.
                maxSizePercent(ContextUtil.getContext()!!, 0.25)
            }
            diskCacheConfig {
                directory(ContextUtil.getContext()!!.cacheDir.resolve("image_cache").toOkioPath())
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}

