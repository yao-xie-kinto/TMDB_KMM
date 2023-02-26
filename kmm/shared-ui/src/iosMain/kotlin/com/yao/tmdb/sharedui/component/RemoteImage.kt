package com.yao.tmdb.sharedui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.component.setupCommonComponents
import com.seiko.imageloader.rememberAsyncImagePainter

@Composable
internal actual fun __RemoteImage(imageUrl: String, modifier: Modifier, contentDescription: String?) {
    CompositionLocalProvider(
        LocalImageLoader provides generateImageLoader(),
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

fun generateImageLoader(): ImageLoader {
    return ImageLoader(/* requestCoroutineContext = Dispatchers.IO */) {
        components {
            setupCommonComponents()
            // or
            // setupKtorComponents(httpClient)
            // setupBase64Components()
            // setupCommonComponents()
            // setupJvmComponents()
            // setupAndroidComponents(context, maxImageSize)
            // or
            // add(KtorUrlMapper())
            // add(KtorUrlKeyer())
            // add(KtorUrlFetcher.Factory(httpClient))
            // ....
        }
    }
}
