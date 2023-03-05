package com.alex.eyk.gifsearch.domain.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class CopyUseCaseImpl(
    appContext: Context
) : CopyUseCase {

    private val clipboardManager: ClipboardManager

    init {
        this.clipboardManager = appContext.getSystemService(Context.CLIPBOARD_SERVICE)
            as ClipboardManager
    }

    override fun copy(
        label: String,
        data: String
    ) {
        val clip = ClipData.newPlainText(label, data)
        clipboardManager.setPrimaryClip(clip)
    }
}
