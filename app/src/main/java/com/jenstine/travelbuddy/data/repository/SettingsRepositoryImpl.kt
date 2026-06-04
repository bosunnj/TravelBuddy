package com.jenstine.travelKing.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jenstine.travelKing.domain.model.AppSettings
import com.jenstine.travelKing.domain.model.AppTheme
import com.jenstine.travelKing.domain.model.DistanceUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private object Keys {
        val THEME         = stringPreferencesKey("theme")
        val AI_API_KEY    = stringPreferencesKey("ai_api_key")
        val CURRENCY      = stringPreferencesKey("currency")
        val DISTANCE_UNIT = stringPreferencesKey("distance_unit")
    }

    override val settings: Flow<AppSettings> = dataStore.data
        .catch { e -> if (e is IOException) emit(emptyPreferences()) else throw e }
        .map { prefs ->
            AppSettings(
                theme = prefs[Keys.THEME]
                    ?.let { runCatching { AppTheme.valueOf(it) }.getOrNull() }
                    ?: AppTheme.SYSTEM,
                aiApiKey = prefs[Keys.AI_API_KEY] ?: "",
                currency = prefs[Keys.CURRENCY] ?: "USD",
                distanceUnit = prefs[Keys.DISTANCE_UNIT]
                    ?.let { runCatching { DistanceUnit.valueOf(it) }.getOrNull() }
                    ?: DistanceUnit.KM
            )
        }

    override suspend fun setTheme(theme: AppTheme) {
        dataStore.edit { it[Keys.THEME] = theme.name }
    }

    override suspend fun setAiApiKey(key: String) {
        dataStore.edit { it[Keys.AI_API_KEY] = key }
    }

    override suspend fun setCurrency(currency: String) {
        dataStore.edit { it[Keys.CURRENCY] = currency }
    }

    override suspend fun setDistanceUnit(unit: DistanceUnit) {
        dataStore.edit { it[Keys.DISTANCE_UNIT] = unit.name }
    }
}
