package com.emix.tempest.data.database.unitlocalized.current

/**
 * Structre for unit system metric and imperial to retrive data that is needed and made code more optimized
 * To call data with more readable name
 * */
interface UnitSpecificCurrentWeatherEntry {
    val temperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
    val humidity: Int
    val uv: Double
    val conditionCode: Int
}