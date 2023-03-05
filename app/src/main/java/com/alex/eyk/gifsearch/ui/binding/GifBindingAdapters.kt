package com.alex.eyk.gifsearch.ui.binding

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.alex.eyk.gifsearch.GlideApp
import com.alex.eyk.gifsearch.R
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object GifBindingAdapters {

    @Suppress("NAME_SHADOWING")
    @BindingAdapter(
        value = [
            "gifUrl",
            "centering",
            "loading"
        ],
        requireAll = false
    )
    @JvmStatic
    fun setGif(
        imageView: ImageView,
        url: String,
        centering: Boolean? /* = false */,
        loading: Boolean? /* = false */
    ) {
        // binding adapter does not support default argument values
        val centering = centering ?: false
        val loading = loading ?: false

        GlideApp.with(imageView)
            .load(url)
            .let {
                if (centering) {
                    it.centerCrop()
                } else {
                    it.fitCenter()
                }
            }
            .let {
                if (loading) {
                    it.placeholder(
                        getProgressDrawable(imageView.context)
                    )
                } else {
                    it
                }
            }
            .transition(
                DrawableTransitionOptions.withCrossFade()
            )
            .into(imageView)
    }

    private fun getProgressDrawable(
        context: Context
    ): Drawable {
        return CircularProgressDrawable(context).apply {
            strokeWidth = context.resources
                .getDimension(R.dimen.progress_bar_stroke_width)
            centerRadius = context.resources
                .getDimension(R.dimen.progress_bar_radius)
            start()
        }
    }
}
