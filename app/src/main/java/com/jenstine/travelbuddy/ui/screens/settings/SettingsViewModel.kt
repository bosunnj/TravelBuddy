package com.jenstine.travelKing.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jenstine.travelKing.data.repository.SettingsRepository
import com.jenstine.travelKing.domain.model.AppSettings
import com.jenstine.travelKing.domain.model.AppTheme
import com.jenstine.travelKing.domain.model.DistanceUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    val settings: StateFlow<AppSettings> = repository.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppSettings())

    fun setTheme(theme: AppTheme) =
        viewModelScope.launch { repository.setTheme(theme) }

    fun setAiApiKey(key: String) =
        viewModelScope.launch { repository.setAiApiKey(key) }

    fun setCurrency(currency: String) =
        viewModelScope.launch { repository.setCurrency(currency) }

    fun setDistanceUnit(unit: DistanceUnit) =
        viewModelScope.launch { repository.setDistanceUnit(unit) }
}
