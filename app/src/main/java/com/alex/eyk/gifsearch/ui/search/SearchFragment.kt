package com.alex.eyk.gifsearch.ui.search

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : AbstractFragment<FragmentGifSearchBinding>(
    layoutRes = R.layout.fragment_gif_search
) {

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
        }
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
            layoutManager = LinearLayoutManager(context)
            adapter = gifsAdapter
        }
    }
}
