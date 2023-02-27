package com.alex.eyk.gifsearch.ui.info

import androidx.fragment.app.viewModels
import com.alex.eyk.gifsearch.R
import com.alex.eyk.gifsearch.databinding.FragmentGifInfoBinding
import com.alex.eyk.gifsearch.ui.AbstractFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifInfoFragment : AbstractFragment<FragmentGifInfoBinding>(
    layoutRes = R.layout.fragment_gif_info
) {

    private val viewModel by viewModels<GifInfoViewModel>()
}
