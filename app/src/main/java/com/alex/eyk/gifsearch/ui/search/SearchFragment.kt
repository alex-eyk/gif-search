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
import com.alex.eyk.gifsearch.ui.ext.addOnScrolledToBottomListener
import com.alex.eyk.gifsearch.ui.search.adapter.GifAdapter
import com.alex.eyk.gifsearch.ui.search.adapter.SuggestionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : AbstractFragment<FragmentGifSearchBinding>(
    layoutRes = R.layout.fragment_gif_search
) {

    companion object {

        private const val GIFS_SPAN_COUNT = 3
    }

    private val viewModel by viewModels<SearchViewModel>()

    private val gifsAdapter = GifAdapter()
    private val suggestionsAdapter = SuggestionAdapter()

    private var scrollComplete: Boolean = false

    override fun onBindingCreated() {
        super.onBindingCreated()
        binding.viewModel = viewModel
        binding.apply {
            executePendingBindings()
            searchView.editText.addTextChangedListener {
                viewModel?.updateSuggestions()
            }
            prepareSuggestionsRecyclerViews()
            prepareGifsRecyclerView()
            searchView.editText
                .setOnEditorActionListener { _, _, _ ->
                    onSuggestionSelected(
                        Suggestion(searchView.text.toString())
                    )
                    return@setOnEditorActionListener false
                }
            gifsRecyclerView.addOnScrolledToBottomListener {
                if (scrollComplete) {
                    return@addOnScrolledToBottomListener
                }
                viewModel?.searchNextGifs()
                scrollComplete = true
            }
        }
        suggestionsAdapter.onItemClick = ::onSuggestionSelected
        gifsAdapter.onItemClick = ::onGifSelected
    }

    override fun onCollectStates(): suspend CoroutineScope.() -> Unit = {
        with(viewModel) {
            lifecycleScope.launch {
                suggestions.collect(::collectSuggestionsState)
            }
            lifecycleScope.launch {
                gifs.collect {
                    gifsAdapter.submitData(it)
                }
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
            is UiState.None -> {
            }
            is UiState.Success -> {
                suggestionsAdapter.submitList(state.value)
            }
            is UiState.Loading -> {
            }
            else -> {}
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

    private fun FragmentGifSearchBinding.prepareGifsRecyclerView() {
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
}
