package com.jenstine.travelbuddy.data.repository

import com.jenstine.travelbuddy.domain.model.TravelArticle

interface ReadRepository {
    suspend fun getArticles(): List<TravelArticle>
}
