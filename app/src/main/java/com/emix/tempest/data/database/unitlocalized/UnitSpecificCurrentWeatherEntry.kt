package com.emix.tempest.data.database.unitlocalized

interface UnitSpecificCurrentWeatherEntry {
    val temperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
}