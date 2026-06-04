package com.jenstine.travelKing.ui.screens.write.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jenstine.travelKing.data.repository.WriteRepository
import com.jenstine.travelKing.domain.model.JournalEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EntryEditorViewModel @Inject constructor(
    private val repository: WriteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val entryId: String? =
        savedStateHandle.get<String>("entryId")?.takeIf { it != "new" }

    var title by mutableStateOf("")
        private set
    var body by mutableStateOf("")
        private set
    var destination by mutableStateOf("")
        private set

    private var originalCreatedAt: Long = 0L

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved.asStateFlow()

    init {
        entryId?.let { id ->
            viewModelScope.launch {
                repository.getEntry(id)?.let { entry ->
                    title = entry.title
                    body = entry.body
                    destination = entry.destination
                    originalCreatedAt = entry.createdAt
                }
            }
        }
    }

    fun onTitleChange(value: String) { title = value }
    fun onBodyChange(value: String) { body = value }
    fun onDestinationChange(value: String) { destination = value }

    fun save() {
        if (title.isBlank() && body.isBlank()) return
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            repository.saveEntry(
                JournalEntry(
                    id = entryId ?: UUID.randomUUID().toString(),
                    title = title.ifBlank { "Untitled" },
                    body = body,
                    destination = destination,
                    createdAt = if (entryId == null) now else originalCreatedAt,
                    updatedAt = now
                )
            )
            _saved.value = true
        }
    }
}
