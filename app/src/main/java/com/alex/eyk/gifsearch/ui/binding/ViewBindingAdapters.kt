package com.alex.eyk.gifsearch.ui.binding

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.databinding.BindingAdapter
import com.alex.eyk.gifsearch.ui.AnimationUtils
import com.alex.eyk.gifsearch.ui.AnimationUtils.ALPHA_SHORT_DURATION
import com.alex.eyk.gifsearch.ui.UiState
import com.alex.eyk.gifsearch.ui.UiState.None
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
    fun setVisibleOnDateCorrect(
        view: View,
        date: Date
    ) {
        view.visibility = if (date.time > 0) View.VISIBLE else View.GONE
    }

    @BindingAdapter("visibleOnNoneStateWithAnimation")
    @JvmStatic
    fun setVisibleOnNoneState(
        view: View,
        state: UiState<*>
    ) {
        if (state is None) {
            setVisibilityWithAnimation(
                view,
                visible = true,
                notVisibleState = View.GONE
            )
        } else {
            setVisibilityWithAnimation(
                view,
                visible = false,
                notVisibleState = View.GONE
            )
        }
    }

    @Suppress("NAME_SHADOWING")
    @BindingAdapter(
        value = [
            "animatedVisibility",
            "notVisibleState",
        ],
        requireAll = false
    )
    @JvmStatic
    fun setVisibilityWithAnimation(
        view: View,
        visible: Boolean,
        notVisibleState: Int /* = View.GONE */
    ) {
        // Parameter default values do not yet work with data binding
        val notVisibleState = if (notVisibleState != 0) notVisibleState else View.GONE

        if (visible) {
            if (view.visibility == View.VISIBLE) {
                return
            }
            view.visibility = View.VISIBLE
            AnimationUtils.appearance(view)
        } else {
            if (view.visibility == notVisibleState) {
                return
            }
            AnimationUtils.disappearance(view)
            Handler(Looper.myLooper()!!).postDelayed(
                {
                    view.visibility = notVisibleState
                },
                ALPHA_SHORT_DURATION
            )
        }
    }
}
