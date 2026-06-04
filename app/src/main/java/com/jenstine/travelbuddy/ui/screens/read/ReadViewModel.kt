package com.jenstine.travelKing.ui.screens.read

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jenstine.travelKing.data.repository.ReadRepository
import com.jenstine.travelKing.domain.model.TravelArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ReadUiState {
    object Loading : ReadUiState()
    data class Success(val articles: List<TravelArticle>) : ReadUiState()
    data class Error(val message: String) : ReadUiState()
}

@HiltViewModel
class ReadViewModel @Inject constructor(
    private val repository: ReadRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReadUiState>(ReadUiState.Loading)
    private val _searchQuery = MutableStateFlow("")

    val searchQuery: StateFlow<String> = _searchQuery

    val uiState: StateFlow<ReadUiState> = combine(_uiState, _searchQuery) { state, query ->
        if (state is ReadUiState.Success && query.isNotBlank()) {
            val filtered = state.articles.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.destination.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true)
            }
            ReadUiState.Success(filtered)
        } else {
            state
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ReadUiState.Loading)

    init {
        loadArticles()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun retry() {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            _uiState.value = ReadUiState.Loading
            try {
                _uiState.value = ReadUiState.Success(repository.getArticles())
            } catch (e: Exception) {
                _uiState.value = ReadUiState.Error(e.message ?: "Failed to load articles")
            }
        }
    }
}

