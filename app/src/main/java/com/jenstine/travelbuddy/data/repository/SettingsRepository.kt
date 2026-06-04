package com.jenstine.travelKing.data.repository

import com.jenstine.travelKing.domain.model.AppSettings
import com.jenstine.travelKing.domain.model.AppTheme
import com.jenstine.travelKing.domain.model.DistanceUnit
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val settings: Flow<AppSettings>
    suspend fun setTheme(theme: AppTheme)
    suspend fun setAiApiKey(key: String)
    suspend fun setCurrency(currency: String)
    suspend fun setDistanceUnit(unit: DistanceUnit)
}
