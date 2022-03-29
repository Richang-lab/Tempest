package com.emix.tempest.data.database.unitlocalized.current

import androidx.room.ColumnInfo

//we need to give reference to these property from our table/CurrentWeatherClass (same name from entity class)
data class ImperialCurrentWeatherEntry(
    @ColumnInfo(name = "tempF")
    override val temperature: Double,

    @ColumnInfo(name = "condition_text")
    override val conditionText: String,

    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,

    @ColumnInfo(name = "windMph")
    override val windSpeed: Double,

    @ColumnInfo(name = "windDir")
    override val windDirection: String,

    @ColumnInfo(name = "precipIn")
    override val precipitationVolume: Double,

    @ColumnInfo(name = "feelslikeF")
    override val feelsLikeTemperature: Double,

    @ColumnInfo(name = "visMiles")
    override val visibilityDistance: Double,

    @ColumnInfo(name = "humidity")
    override val humidity: Int,

    @ColumnInfo(name = "uv")
    override val uv: Double
): UnitSpecificCurrentWeatherEntry
