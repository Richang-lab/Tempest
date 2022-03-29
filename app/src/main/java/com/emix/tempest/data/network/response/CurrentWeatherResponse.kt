package com.emix.tempest.data.network.response

import com.emix.tempest.data.database.entity.CurrentWeatherEntry
import com.emix.tempest.data.database.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

//Api Response in kotlin
data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation
)