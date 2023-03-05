@file:OptIn(ExperimentalCoroutinesApi::class)

package com.alex.eyk.gifsearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alex.eyk.gifsearch.data.entity.Gif
import com.alex.eyk.gifsearch.data.entity.Suggestion
import com.alex.eyk.gifsearch.data.net.pages.GifsPagingSource
import com.alex.eyk.gifsearch.data.repo.SuggestionsRepository
import com.alex.eyk.gifsearch.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private typealias SuggestionsState = UiState<List<Suggestion>>

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val pagingSourceFactory: GifsPagingSource.Factory,
    private val suggestionsRepository: SuggestionsRepository
) : ViewModel() {

    companion object {

        private const val GIFS_PER_PAGE = 25
    }

    private val gifPagingConfig = PagingConfig(
        pageSize = GIFS_PER_PAGE,
        enablePlaceholders = false
    )

    private val query = MutableStateFlow("")

    val gifs: StateFlow<PagingData<Gif>> = query
        .map {
            Pager(gifPagingConfig) {
                pagingSourceFactory.create(it)
            }
        }
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    private val _suggestions = MutableStateFlow<SuggestionsState>(UiState.None)
    val suggestions: StateFlow<SuggestionsState> = _suggestions

    fun search(query: String) {
        this.query.tryEmit(query)
    }

    fun updateSuggestions(
        query: String
    ) {
        if (query.isBlank()) {
            _suggestions.value = UiState.None
        }
        viewModelScope.launch {
            _suggestions.value = UiState.by {
                suggestionsRepository.findAll(query)
            }
        }
    }
}
