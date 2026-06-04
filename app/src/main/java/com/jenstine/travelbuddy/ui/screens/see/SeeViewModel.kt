package com.jenstine.travelKing.ui.screens.see

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jenstine.travelKing.data.repository.SeeRepository
import com.jenstine.travelKing.domain.model.TravelDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SeeViewMode { GALLERY, MAP }

sealed class SeeUiState {
    object Loading : SeeUiState()
    data class Success(val destinations: List<TravelDestination>) : SeeUiState()
    data class Error(val message: String) : SeeUiState()
}

@HiltViewModel
class SeeViewModel @Inject constructor(
    private val repository: SeeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SeeUiState>(SeeUiState.Loading)
    val uiState: StateFlow<SeeUiState> = _uiState.asStateFlow()

    private val _viewMode = MutableStateFlow(SeeViewMode.GALLERY)
    val viewMode: StateFlow<SeeViewMode> = _viewMode.asStateFlow()

    init {
        loadDestinations()
    }

    fun setViewMode(mode: SeeViewMode) {
        _viewMode.value = mode
    }

    fun retry() {
        loadDestinations()
    }

    private fun loadDestinations() {
        viewModelScope.launch {
            _uiState.value = SeeUiState.Loading
            try {
                _uiState.value = SeeUiState.Success(repository.getDestinations())
            } catch (e: Exception) {
                _uiState.value = SeeUiState.Error(e.message ?: "Failed to load destinations")
            }
        }
    }
}

