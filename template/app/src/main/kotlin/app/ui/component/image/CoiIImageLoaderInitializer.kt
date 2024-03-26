package app.ui.component.image

import android.content.Context
import android.os.Build
import app.AppDependencyInitializer
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import core.data.datasource.http.okhttp.OkHttpSource
import dagger.Lazy
import javax.inject.Inject

/**
 * A dependency initializer for configuring the Coil image loader library.
 * Initializes the image loader factory with specified configurations.
 */
class CoiIImageLoaderInitializer : AppDependencyInitializer<ImageLoaderFactory>() {

    @Inject
    lateinit var httpSource: Lazy<OkHttpSource>

    override fun initialize(context: Context): ImageLoaderFactory {
        val factory = ImageLoaderFactory {
            ImageLoader.Builder(context)
                .components {
                    add(SvgDecoder.Factory())
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .okHttpClient(httpSource.get().okhttp)
                .respectCacheHeaders(false)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .networkCachePolicy(CachePolicy.ENABLED)
                .build()
        }
        Coil.setImageLoader(factory)
        return factory
    }

}