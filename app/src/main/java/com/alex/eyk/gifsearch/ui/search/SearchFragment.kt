package com.alex.eyk.gifsearch.ui.search

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
import com.alex.eyk.gifsearch.ui.search.adapter.GifAdapter
import com.alex.eyk.gifsearch.ui.search.adapter.SuggestionAdapter
import com.alex.eyk.gifsearch.ui.toPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : AbstractFragment<FragmentGifSearchBinding>(
    layoutRes = R.layout.fragment_gif_search
) {

    companion object {

        private const val GIFS_SPACING_DP = 8
        private const val GIFS_SPAN_COUNT = 3
    }

    private val viewModel by viewModels<SearchViewModel>()

    private val gifsAdapter = GifAdapter()
    private val suggestionsAdapter = SuggestionAdapter()

    override fun onBindingCreated() {
        super.onBindingCreated()
        binding.viewModel = viewModel
        binding.apply {
            executePendingBindings()
            searchView.editText.addTextChangedListener {
                viewModel?.updateSuggestions()
            }
            prepareSuggestionsRecyclerViews()
            prepareSearchRecyclerView()
            searchView.editText
                .setOnEditorActionListener { _, _, _ ->
                    onSuggestionSelected(
                        Suggestion(searchView.text.toString())
                    )
                    return@setOnEditorActionListener false
                }
        }
        suggestionsAdapter.onItemClick = ::onSuggestionSelected
        gifsAdapter.onItemClick = ::onGifSelected
    }

    override fun onCollectStates(): suspend CoroutineScope.() -> Unit = {
        viewModel.apply {
            lifecycleScope.launch {
                suggestions.collect(::collectSuggestionsState)
            }
            lifecycleScope.launch {
                searchResults.collect(::collectSearchResultsState)
            }
        }
    }

    private fun onSuggestionSelected(
        suggestion: Suggestion
    ) {
        binding.searchView.hide()
        viewModel.onSuggestionSelected(suggestion)
    }

    private fun onGifSelected(gif: Gif) {
        val action = SearchFragmentDirections
            .actionGifSearchToGifInfo(gif)
        findNavController().navigate(action)
    }

    private fun collectSuggestionsState(
        state: UiState<List<Suggestion>>
    ) {
        when (state) {
            is UiState.None -> {}
            is UiState.Success -> {
                suggestionsAdapter.submitList(state.value)
            }
            else -> {}
        }
    }

    private fun collectSearchResultsState(
        state: UiState<List<Gif>>
    ) {
        collectState(
            state,
            onSuccess = {
                gifsAdapter.submitList(it)
            },
            onLoading = {
            },
            onClear = {
                gifsAdapter.submitList(emptyList())
            }
        )
    }

    private inline fun <T : Any> collectState(
        state: UiState<T>,
        onSuccess: (result: T) -> Unit,
        onLoading: () -> Unit,
        onClear: () -> Unit
    ) {
        when (state) {
            is UiState.None -> {
                onClear()
            }
            is UiState.Loading -> {
                onLoading()
            }
            is UiState.Success -> {
                onSuccess(state.value)
            }
            is UiState.Failure -> {
            }
        }
    }

    private fun FragmentGifSearchBinding.prepareSuggestionsRecyclerViews() {
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

    private fun FragmentGifSearchBinding.prepareSearchRecyclerView() {
        searchResultsRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = gifsAdapter
            addItemDecoration(
                SpacesItemDecoration(
                    spaceInPx = GIFS_SPACING_DP.toPx,
                    spanCount = GIFS_SPAN_COUNT
                )
            )
        }
    }
}
