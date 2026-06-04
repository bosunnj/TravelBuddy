package com.jenstine.travelKing.ui.screens.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jenstine.travelKing.data.repository.WriteRepository
import com.jenstine.travelKing.domain.model.JournalEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val repository: WriteRepository
) : ViewModel() {

    val entries: StateFlow<List<JournalEntry>> = repository.observeEntries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun deleteEntry(id: String) {
        viewModelScope.launch { repository.deleteEntry(id) }
    }
}
