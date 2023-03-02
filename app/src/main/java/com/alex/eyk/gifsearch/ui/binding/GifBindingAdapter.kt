package com.alex.eyk.gifsearch.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object GifBindingAdapter {

    @BindingAdapter("url")
    @JvmStatic
    fun setGif(
        imageView: ImageView,
        url: String
    ) {
        Glide.with(imageView)
            .load(url)
            .centerCrop()
            .into(imageView)
    }
}
