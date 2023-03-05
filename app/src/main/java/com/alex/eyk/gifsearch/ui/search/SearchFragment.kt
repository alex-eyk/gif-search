package com.alex.eyk.gifsearch.ui.search

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.alex.eyk.gifsearch.R
import com.alex.eyk.gifsearch.data.entity.Gif
import com.alex.eyk.gifsearch.data.entity.Suggestion
import com.alex.eyk.gifsearch.databinding.FragmentGifSearchBinding
import com.alex.eyk.gifsearch.ui.AbstractFragment
import com.alex.eyk.gifsearch.ui.UiState
import com.alex.eyk.gifsearch.ui.ext.setOnActionListener
import com.alex.eyk.gifsearch.ui.search.adapter.GifAdapter
import com.alex.eyk.gifsearch.ui.search.adapter.SuggestionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private typealias GifSearchBinding = FragmentGifSearchBinding

@AndroidEntryPoint
class SearchFragment : AbstractFragment<SearchViewModel, GifSearchBinding>(
    layoutRes = R.layout.fragment_gif_search
) {

    companion object {

        private const val GIFS_SPAN_COUNT = 3
    }

    override val viewModel by viewModels<SearchViewModel>()

    private val gifsAdapter = GifAdapter()
    private val suggestionsAdapter = SuggestionAdapter()

    override fun onBindingCreated() {
        super.onBindingCreated()
        with(binding) {
            prepareSuggestionsRecyclerViews()
            prepareGifsRecyclerView()

            searchView.editText.addTextChangedListener {
                viewModel?.updateSuggestions(it.toString())
            }
            searchView.editText.setOnActionListener {
                viewModel?.search(searchView.text.toString())
                onQueryCompleted(searchView.text.toString())
            }
        }

        suggestionsAdapter.onItemClick = {
            binding.onQueryCompleted(it.name)
            viewModel.search(it.name)
        }
        gifsAdapter.onItemClick = ::showGifInfo
        gifsAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Error ||
                it.append is LoadState.Error ||
                it.prepend is LoadState.Error
            ) {
                quickSnackbar(R.string.unable_to_load_gifs)
            }
        }
    }

    override fun onCollectStates(): suspend CoroutineScope.() -> Unit = {
        with(viewModel) {
            lifecycleScope.launch {
                suggestions.collect(::collectSuggestionsState)
            }
            lifecycleScope.launch {
                gifs.collectLatest {
                    gifsAdapter.submitData(it)
                }
            }
        }
    }

    private fun collectSuggestionsState(
        state: UiState<List<Suggestion>>
    ) {
        when (state) {
            is UiState.Success -> {
                suggestionsAdapter.submitList(state.value)
            }
            else -> {}
        }
    }

    private fun showGifInfo(gif: Gif) {
        val action = SearchFragmentDirections
            .actionGifSearchToGifInfo(gif)
        findNavController().navigate(action)
    }

    private fun GifSearchBinding.prepareSuggestionsRecyclerViews() {
        suggestionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = suggestionsAdapter
            val dividerItemDecoration = DividerItemDecoration(
                context,
                HORIZONTAL
            )
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun GifSearchBinding.prepareGifsRecyclerView() {
        gifsRecyclerView.apply {
            layoutManager = GridLayoutManager(context, GIFS_SPAN_COUNT)
            adapter = gifsAdapter
            addItemDecoration(
                SpacesItemDecoration(
                    spaceInPx = resources.getDimension(R.dimen.gif_spacing),
                    spanCount = GIFS_SPAN_COUNT
                )
            )
        }
    }

    private fun GifSearchBinding.onQueryCompleted(
        query: String
    ) {
        searchView.hide()
        searchBar.text = query
    }
}
