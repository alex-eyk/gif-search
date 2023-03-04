package com.alex.eyk.gifsearch.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object GifBindingAdapters {

    @BindingAdapter(
        value = [
            "gifUrl",
            "centering"
        ],
        requireAll = false
    )
    @JvmStatic
    fun setGif(
        imageView: ImageView,
        url: String,
        centering: Boolean? /* = false */
    ) {
        // binding adapter does not support default argument values
        val imageCentering = centering ?: false

        Glide.with(imageView)
            .load(url)
            .let {
                if (imageCentering) {
                    it.centerCrop()
                } else {
                    it.fitCenter()
                }
            }
            .into(imageView)
    }
}
