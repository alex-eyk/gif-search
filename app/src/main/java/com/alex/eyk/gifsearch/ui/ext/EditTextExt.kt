package com.alex.eyk.gifsearch.ui.ext

import android.widget.EditText

fun EditText.setOnActionListener(
    block: () -> Unit
) {
    this.setOnEditorActionListener { _, _, _ ->
        block()
        false
    }
}
