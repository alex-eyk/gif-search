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

@AndroidEntryPoint
class SearchFragment : AbstractFragment<SearchViewModel, FragmentGifSearchBinding>(
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
        binding.viewModel = viewModel

        binding.apply {
            searchView.editText.addTextChangedListener {
                viewModel?.updateSuggestions(it.toString())
            }
            prepareSuggestionsRecyclerViews()
            prepareGifsRecyclerView()
            searchView.editText.setOnActionListener {
                viewModel?.search(
                    searchView.text.toString()
                )
                binding.searchView.hide()
                binding.searchBar.text = searchView.text.toString()
            }
        }
        suggestionsAdapter.onItemClick = {
            binding.searchView.hide()
            binding.searchBar.text = it.name
            viewModel.search(it.name)
        }
        gifsAdapter.onItemClick = {
            val action = SearchFragmentDirections
                .actionGifSearchToGifInfo(it)
            findNavController().navigate(action)
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
