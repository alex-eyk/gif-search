package com.alex.eyk.gifsearch.ui

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator

object AnimationUtils {

    const val ALPHA_SHORT_DURATION = 150L

    fun appearance(
        view: View,
        animDuration: Long = ALPHA_SHORT_DURATION
    ) {
        ObjectAnimator.ofFloat(
            view,
            View.ALPHA,
            0f,
            1f
        ).apply {
            interpolator = DecelerateInterpolator()
            duration = animDuration
            start()
        }
    }

    fun disappearance(
        view: View,
        animDuration: Long = ALPHA_SHORT_DURATION
    ) {
        ObjectAnimator.ofFloat(
            view,
            View.ALPHA,
            1f,
            0f
        ).apply {
            interpolator = AccelerateInterpolator()
            duration = animDuration
            start()
        }
    }
}
