package com.alex.eyk.gifsearch.ui.search

import androidx.fragment.app.viewModels
import com.alex.eyk.gifsearch.R
import com.alex.eyk.gifsearch.databinding.FragmentGifSearchBinding
import com.alex.eyk.gifsearch.ui.AbstractFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : AbstractFragment<FragmentGifSearchBinding>(
    layoutRes = R.layout.fragment_gif_search
) {

    private val viewModel by viewModels<SearchViewModel>()
}
