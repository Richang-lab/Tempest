package com.emix.tempest.data.database.unitlocalized.current

import androidx.room.ColumnInfo

//we need to give reference to these property from our table/CurrentWeatherClass
data class MetricCurrentWeatherEntry (
    @ColumnInfo(name = "tempC")
    override val temperature: Double,

    @ColumnInfo(name = "condition_text")
    override val conditionText: String,

    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,

    @ColumnInfo(name = "windKph")
    override val windSpeed: Double,

    @ColumnInfo(name = "windDir")
    override val windDirection: String,

    @ColumnInfo(name = "precipMm")
    override val precipitationVolume: Double,

    @ColumnInfo(name = "feelslikeC")
    override val feelsLikeTemperature: Double,

    @ColumnInfo(name = "visKm")
    override val visibilityDistance: Double,

    @ColumnInfo(name = "humidity")
    override val humidity: Int,

    @ColumnInfo(name = "uv")
    override val uv: Double
): UnitSpecificCurrentWeatherEntry