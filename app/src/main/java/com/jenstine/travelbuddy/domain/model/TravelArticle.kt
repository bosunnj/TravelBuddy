package com.jenstine.travelbuddy.domain.model

data class TravelArticle(
    val id: String,
    val title: String,
    val destination: String,
    val summary: String,
    val category: String,
    val readTimeMinutes: Int
)
