package app

import android.app.Application
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import core.essentials.http.HttpSource // {httpSource-import}
import dagger.Lazy // {httpSource-import}
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject // {httpSource-import}

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {

    // start {httpSource-inject}
    @Inject
    lateinit var httpSource: Lazy<HttpSource>
    // end {httpSource-inject}

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(SvgDecoder.Factory())
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            // start {httpSource-client}
            .okHttpClient(httpSource.get().okhttp)
            // end {httpSource-client}
            .respectCacheHeaders(false)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .build()
    }
}
