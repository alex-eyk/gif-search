package com.alex.eyk.gifsearch.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindingAdapters {

    @BindingAdapter("android:onLongClick")
    @JvmStatic
    fun setOnLongClickListener(
        view: View,
        onLongClick: () -> Unit
    ) {
        view.setOnLongClickListener {
            onLongClick()
            true
        }
    }
}
