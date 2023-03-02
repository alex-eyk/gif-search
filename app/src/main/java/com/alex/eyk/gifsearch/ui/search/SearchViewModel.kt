package com.alex.eyk.gifsearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.eyk.gifsearch.data.entity.Gif
import com.alex.eyk.gifsearch.data.entity.Suggestion
import com.alex.eyk.gifsearch.data.repo.GifRepository
import com.alex.eyk.gifsearch.data.repo.SuggestionsRepository
import com.alex.eyk.gifsearch.ui.UiState
import com.alex.eyk.gifsearch.ui.UiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val gifRepository: GifRepository,
    private val suggestionsRepository: SuggestionsRepository
) : ViewModel() {

    companion object {

        private const val GIFS_PER_REQUEST = 25
    }

    private val _searchResults = MutableStateFlow<UiState<List<Gif>>>(UiState.None)
    val searchResults: StateFlow<UiState<List<Gif>>> = _searchResults

    private val _nextResults = MutableStateFlow<UiState<List<Gif>>>(UiState.None)
    val nextResults: StateFlow<UiState<List<Gif>>> = _nextResults

    private val _suggestions = MutableStateFlow<UiState<List<Suggestion>>>(UiState.None)
    val suggestions: StateFlow<UiState<List<Suggestion>>> = _suggestions

    val query = MutableStateFlow("")

    private var offset = 0

    fun onSuggestionSelected(
        suggestion: Suggestion
    ) {
        query.value = suggestion.name
        searchGifs()
    }

    private fun searchGifs() {
        if (query.value.isBlank()) {
            if (_searchResults.value is Success) {
                _searchResults.value = UiState.None
            }
        }
        _searchResults.value = UiState.Loading
        viewModelScope.launch {
            _searchResults.value = UiState.by {
                gifRepository.findAll(
                    query.value,
                    limit = GIFS_PER_REQUEST,
                    offset = 0
                )
            }
            offset = GIFS_PER_REQUEST
        }
    }

    fun searchNextGifs() {
        if (query.value.isBlank()) {
            return
        }
        _nextResults.value = UiState.Loading
        viewModelScope.launch {
            _nextResults.value = UiState.by {
                gifRepository.findAll(
                    query.value,
                    limit = GIFS_PER_REQUEST,
                    offset = offset
                )
            }
            offset += GIFS_PER_REQUEST
        }
    }

    fun updateSuggestions() {
        if (query.value.isBlank()) {
            _suggestions.value = UiState.None
        }
        viewModelScope.launch {
            _suggestions.value = UiState.by {
                suggestionsRepository.findAll(query.value)
            }
        }
    }
}
