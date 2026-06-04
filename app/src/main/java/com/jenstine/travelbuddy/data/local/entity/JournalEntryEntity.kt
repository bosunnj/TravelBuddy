package com.jenstine.travelKing.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntryEntity(
    @PrimaryKey val id: String,
    val title: String,
    val body: String,
    val destination: String,
    val createdAt: Long,
    val updatedAt: Long
)
