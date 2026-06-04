package com.jenstine.travelKing.data.repository

import com.jenstine.travelKing.domain.model.TravelArticle

interface ReadRepository {
    suspend fun getArticles(): List<TravelArticle>
}

