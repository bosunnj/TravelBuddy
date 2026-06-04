package com.jenstine.travelKing.data.repository

import com.jenstine.travelKing.domain.model.TravelArticle

/** Thrown only for 401/403 — the user must fix their API key. */
class ApiAuthException(message: String) : Exception(message)

interface ReadRepository {
    fun getFallbackArticles(): List<TravelArticle>
    suspend fun getArticles(): List<TravelArticle>      // uses cache; errors propagate
    suspend fun refreshArticles(): List<TravelArticle>  // bypasses cache; errors propagate
}
