package com.alex.eyk.gifsearch.ui.binding

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.alex.eyk.gifsearch.ui.toPx
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object GifBindingAdapters {

    private val PROGRESS_BAR_STROKE_WIDTH = 4f.toPx.toFloat()
    private val PROGRESS_BAR_RADIUS = 16.toPx.toFloat()

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
        val imageCentering = centering ?: false
        val showLoading = loading ?: false

        Glide.with(imageView)
            .load(url)
            .let {
                if (imageCentering) {
                    it.centerCrop()
                } else {
                    it.fitCenter()
                }
            }
            .let {
                if (showLoading) {
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
            strokeWidth = PROGRESS_BAR_STROKE_WIDTH
            centerRadius = PROGRESS_BAR_RADIUS
            start()
        }
    }
}
