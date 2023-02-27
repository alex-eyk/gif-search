package com.alex.eyk.gifsearch.ui.search

import androidx.fragment.app.viewModels
import com.alex.eyk.gifsearch.R
import com.alex.eyk.gifsearch.databinding.FragmentGifInfoBinding
import com.alex.eyk.gifsearch.ui.AbstractFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : AbstractFragment<FragmentGifInfoBinding>(
    layoutRes = R.layout.fragment_gif_search
) {

    private val viewModel by viewModels<SearchViewModel>()
}
