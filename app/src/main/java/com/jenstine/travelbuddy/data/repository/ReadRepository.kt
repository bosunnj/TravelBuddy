package com.jenstine.travelKing.data.repository

import com.jenstine.travelKing.domain.model.TravelArticle

interface ReadRepository {
    suspend fun getArticles(): List<TravelArticle>    // uses cache if available
    suspend fun refreshArticles(): List<TravelArticle> // always calls API; falls back to cache on rate-limit
}

