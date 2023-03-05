package com.alex.eyk.gifsearch.ui.binding

import android.text.Spanned
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.search.SearchView

@SuppressWarnings("ReturnCount")
object SearchViewBindingAdapters {

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(
        searchView: SearchView,
        text: CharSequence?
    ) {
        val oldText = searchView.text ?: ""
        if (text === oldText || text == null && oldText.isEmpty()) {
            return
        }
        if (text is Spanned) {
            if (text == oldText) {
                return
            }
        } else if (!oldText.isContentDifferentFrom(text)) {
            return
        }
        searchView.setText(text)
    }

    @InverseBindingAdapter(
        attribute = "android:text",
        event = "android:textAttrChanged"
    )
    @JvmStatic
    fun getTextString(
        searchView: SearchView
    ): String {
        return searchView.text.toString()
    }

    @BindingAdapter("android:textAttrChanged")
    @JvmStatic
    fun setTextWatcher(
        searchBar: SearchView,
        textAttrChanged: InverseBindingListener
    ) {
        searchBar.editText.addTextChangedListener {
            textAttrChanged.onChange()
        }
    }

    private fun CharSequence.isContentDifferentFrom(
        content: CharSequence?
    ): Boolean {
        if (content == null) {
            return true
        }
        val length = this.length
        if (length != content.length) {
            return true
        }
        for (i in 0 until length) {
            if (this[i] != content[i]) {
                return true
            }
        }
        return false
    }
}
