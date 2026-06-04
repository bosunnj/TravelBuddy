package com.jenstine.travelKing.ui.screens.read

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jenstine.travelKing.data.repository.ApiAuthException
import com.jenstine.travelKing.data.repository.ReadRepository
import com.jenstine.travelKing.data.repository.SettingsRepository
import com.jenstine.travelKing.domain.model.TravelArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ReadUiState {
    object Loading : ReadUiState()
    data class Success(
        val articles: List<TravelArticle>,
        val notice: String? = null   // non-null when showing fallback content
    ) : ReadUiState()
    data class Error(val message: String) : ReadUiState()
}

@HiltViewModel
class ReadViewModel @Inject constructor(
    private val repository: ReadRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReadUiState>(ReadUiState.Loading)
    private val _searchQuery = MutableStateFlow("")
    private val _isRefreshing = MutableStateFlow(false)

    val searchQuery: StateFlow<String> = _searchQuery
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    val hasApiKey: StateFlow<Boolean> = settingsRepository.settings
        .map { it.aiApiKey.isNotBlank() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    val uiState: StateFlow<ReadUiState> = combine(_uiState, _searchQuery) { state, query ->
        if (state is ReadUiState.Success && query.isNotBlank()) {
            val terms = query.trim().split("\\s+".toRegex())
            ReadUiState.Success(
                articles = state.articles.filter { article ->
                    val haystack = "${article.title} ${article.destination} ${article.category}"
                    terms.all { term -> haystack.contains(term, ignoreCase = true) }
                },
                notice = state.notice
            )
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

    fun refresh() {
        _isRefreshing.value = true
        viewModelScope.launch {
            try {
                _uiState.value = ReadUiState.Success(repository.refreshArticles())
            } catch (e: ApiAuthException) {
                _uiState.value = ReadUiState.Error(e.message ?: "API key error")
            } catch (e: Exception) {
                val current = (_uiState.value as? ReadUiState.Success)?.articles
                    ?: repository.getFallbackArticles()
                _uiState.value = ReadUiState.Success(
                    articles = current,
                    notice = e.message
                )
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun retry() = loadArticles()

    private fun loadArticles() {
        viewModelScope.launch {
            _uiState.value = ReadUiState.Loading
            try {
                _uiState.value = ReadUiState.Success(repository.getArticles())
            } catch (e: ApiAuthException) {
                _uiState.value = ReadUiState.Error(e.message ?: "API key error")
            } catch (e: Exception) {
                _uiState.value = ReadUiState.Success(
                    articles = repository.getFallbackArticles(),
                    notice = e.message
                )
            }
        }
    }
}
