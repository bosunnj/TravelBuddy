package com.jenstine.travelKing.data.repository

import com.jenstine.travelKing.domain.model.JournalEntry
import kotlinx.coroutines.flow.Flow

interface WriteRepository {
    fun observeEntries(): Flow<List<JournalEntry>>
    suspend fun getEntry(id: String): JournalEntry?
    suspend fun saveEntry(entry: JournalEntry)
    suspend fun deleteEntry(id: String)
}
