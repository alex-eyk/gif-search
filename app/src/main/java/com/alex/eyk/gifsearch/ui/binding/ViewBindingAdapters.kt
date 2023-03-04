package com.alex.eyk.gifsearch.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter
import java.util.Date

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

    @BindingAdapter("visibleOnCorrectDate")
    @JvmStatic
    fun setVisibeOnDateCorrect(
        view: View,
        date: Date
    ) {
        view.visibility = if (date.time > 0) View.VISIBLE else View.GONE
    }
}
