package com.jenstine.travelKing.domain.model

data class AppSettings(
    val theme: AppTheme = AppTheme.SYSTEM,
    val aiApiKey: String = "",
    val currency: String = "USD",
    val distanceUnit: DistanceUnit = DistanceUnit.KM
)

enum class AppTheme(val label: String) {
    SYSTEM("System"),
    LIGHT("Light"),
    DARK("Dark")
}

enum class DistanceUnit(val label: String) {
    KM("km"),
    MILES("mi")
}
