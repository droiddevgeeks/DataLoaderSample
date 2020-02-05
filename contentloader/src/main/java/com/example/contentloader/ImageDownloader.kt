package com.example.contentloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.contentloader.cache.MemoryCache
import com.example.contentloader.network.Downloader
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

class ImageDownloader<T : View> private constructor(
    private val url: String,
    private val imageView: WeakReference<T>,
    private val errorPlaceholder: Int,
    private val width: Int,
    private val height: Int
) : Downloader(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    fun fetchImage() {
        val byteArray = MemoryCache.getData(url)

        if (byteArray != null)
            networkResponse(result = byteArray, error = null)
        else
            startDownload(url)
    }

    /**
     * This launch is on Main thread as we set this to MAIN dispatcher
     */
    override fun networkResponse(result: ByteArray?, error: Throwable?) {
        launch {
            val view: View = imageView.get() as View
            result?.let {
                MemoryCache.putData(url,result)
                try {
                    if (view is ImageView) {
                        val bitmap = Bitmap.createScaledBitmap(
                            BitmapFactory.decodeByteArray(
                                result,
                                0,
                                result.size
                            ), width, height, false
                        )
                        if (bitmap != null) {
                            view.setImageBitmap(bitmap)
                        } else {
                            setImage(view, errorPlaceholder)
                        }
                    }
                } catch (e: OutOfMemoryError) {
                    setImage(view, errorPlaceholder)
                }
            }
            error?.let { setImage(view, errorPlaceholder) }
        }
    }

    private fun setImage(view: View, resourceId: Int) {
        if (view is ImageView) {
            view.setImageResource(resourceId)
        }
    }

    class Builder<T : View> {

        private lateinit var imageView: WeakReference<T>
        private var url: String = ""
        private var width: Int = 200
        private var height: Int = 200
        private var errorPlaceholder: Int = R.drawable.error_placeholder

        fun load(url: String): Builder<T> {
            this.url = url
            return this
        }

        fun scale(width: Int, height: Int): Builder<T> {
            this.width = width
            this.height = height
            return this
        }


        fun into(view: T): Builder<T> {
            this.imageView = WeakReference(view)
            return this
        }

        fun errorPlaceholder(@DrawableRes resourceId: Int): Builder<T> {
            this.errorPlaceholder = resourceId
            return this
        }

        fun build(): ImageDownloader<T> {
            require(url.isNotEmpty()) { "Url should not be empty." }
            require(this::imageView.isInitialized) { "View should not be empty." }
            return ImageDownloader(
                this.url,
                this.imageView,
                this.errorPlaceholder,
                this.width,
                this.height
            )
        }
    }
}