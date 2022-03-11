package com.emix.tempest.data.repository

import androidx.lifecycle.LiveData
import com.emix.tempest.data.database.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric:Boolean) : LiveData<out UnitSpecificCurrentWeatherEntry>
}