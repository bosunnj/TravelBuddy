package com.jenstine.travelKing.data.repository

import com.jenstine.travelKing.domain.model.TravelDestination

interface SeeRepository {
    suspend fun getDestinations(): List<TravelDestination>
}

