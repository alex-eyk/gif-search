package com.alex.eyk.gifsearch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.eyk.gifsearch.data.entity.Gif
import com.alex.eyk.gifsearch.data.entity.Suggestion
import com.alex.eyk.gifsearch.data.repo.GifRepository
import com.alex.eyk.gifsearch.data.repo.SuggestionsRepository
import com.alex.eyk.gifsearch.ui.UiState
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

    private val _searchResults = MutableStateFlow<UiState<List<Gif>>>(UiState.None)
    private val _suggestions = MutableStateFlow<UiState<List<Suggestion>>>(UiState.None)

    val query = MutableStateFlow("")

    val searchResults: StateFlow<UiState<List<Gif>>> = _searchResults
    val suggestions: StateFlow<UiState<List<Suggestion>>> = _suggestions

    fun search() {
        if (query.value.isBlank()) {
            return
        }
        viewModelScope.launch {
            _searchResults.value = UiState.by {
                gifRepository.findAll(
                    query.value,
                    offset = 0
                )
            }
        }
    }

    fun onSuggestionSelected(
        suggestion: Suggestion
    ) {
        query.value = suggestion.name
        search()
    }

    fun updateSuggestions() {
        if (query.value.isBlank()) {
            return
        }
        viewModelScope.launch {
            _suggestions.value = UiState.by {
                suggestionsRepository.findAll(query.value)
            }
        }
    }
}
