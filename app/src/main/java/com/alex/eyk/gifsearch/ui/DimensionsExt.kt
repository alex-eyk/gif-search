package com.alex.eyk.gifsearch.ui

import android.content.res.Resources
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP

val Number.toPx
    get() = TypedValue.applyDimension(
        COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
