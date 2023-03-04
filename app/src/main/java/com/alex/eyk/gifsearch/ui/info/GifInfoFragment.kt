package com.alex.eyk.gifsearch.ui.info

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.DisplayMetrics
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.alex.eyk.gifsearch.R
import com.alex.eyk.gifsearch.databinding.FragmentGifInfoBinding
import com.alex.eyk.gifsearch.ui.AbstractFragment
import com.alex.eyk.gifsearch.ui.info.GifInfoViewModel.Event
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope


@AndroidEntryPoint
class GifInfoFragment : AbstractFragment<FragmentGifInfoBinding>(
    layoutRes = R.layout.fragment_gif_info
) {

    private val viewModel by viewModels<GifInfoViewModel>()

    private val navArgs by navArgs<GifInfoFragmentArgs>()

    override fun onBindingCreated() {
        super.onBindingCreated()
        viewModel.gif = navArgs.gif
        binding.viewModel = viewModel
        binding.executePendingBindings()
        binding.gifImage.apply {
            layoutParams = getGifImageLayoutParams()
        }
    }

    override fun onCollectStates(): suspend CoroutineScope.() -> Unit = {
        viewModel.eventsFlow.collect {
            when (it) {
                is Event.OpenUrl -> {
                    openUrl(it.url)
                }
                is Event.NotifyCopied -> {
                    notifyCopied()
                }
            }
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

    private fun notifyCopied() {
        Snackbar.make(
            requireView(),
            R.string.link_copied,
            LENGTH_SHORT
        ).show()
    }

    private fun getGifImageLayoutParams(): LinearLayout.LayoutParams {
        val image = navArgs.gif.images.original
        return LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            calculateImageHeight(image.width, image.height)
        )
    }

    private fun calculateImageHeight(
        imageWidth: Int,
        imageHeight: Int
    ): Int {
        val multiplier: Float = getScreenWidth() / imageWidth.toFloat()
        return (imageHeight * multiplier).toInt()
    }

    private fun getScreenWidth(): Int {
        val windowManager = requireActivity().windowManager
        return if (VERSION.SDK_INT >= VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.width()
        } else {
            val metrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(metrics)
            metrics.widthPixels
        }
    }
}
