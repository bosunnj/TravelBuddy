package com.jenstine.travelKing.domain.model

data class TravelDestination(
    val id: String,
    val name: String,
    val country: String,
    val description: String,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double
)

