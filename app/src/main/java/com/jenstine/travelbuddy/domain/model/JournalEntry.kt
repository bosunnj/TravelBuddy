package com.jenstine.travelKing.domain.model

data class JournalEntry(
    val id: String,
    val title: String,
    val body: String,
    val destination: String,
    val createdAt: Long,
    val updatedAt: Long
)
