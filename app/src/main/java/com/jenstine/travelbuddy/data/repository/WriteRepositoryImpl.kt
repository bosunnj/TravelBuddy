package com.jenstine.travelKing.data.repository

import com.jenstine.travelKing.data.local.dao.JournalDao
import com.jenstine.travelKing.data.local.entity.JournalEntryEntity
import com.jenstine.travelKing.domain.model.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WriteRepositoryImpl @Inject constructor(
    private val dao: JournalDao
) : WriteRepository {

    override fun observeEntries(): Flow<List<JournalEntry>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun getEntry(id: String): JournalEntry? =
        dao.getById(id)?.toDomain()

    override suspend fun saveEntry(entry: JournalEntry) =
        dao.upsert(entry.toEntity())

    override suspend fun deleteEntry(id: String) =
        dao.deleteById(id)
}

private fun JournalEntryEntity.toDomain() =
    JournalEntry(id, title, body, destination, createdAt, updatedAt)

private fun JournalEntry.toEntity() =
    JournalEntryEntity(id, title, body, destination, createdAt, updatedAt)
