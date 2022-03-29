package com.emix.tempest.data.repository

import androidx.lifecycle.LiveData
import com.emix.tempest.data.database.entity.WeatherLocation
import com.emix.tempest.data.database.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.emix.tempest.data.database.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.emix.tempest.data.database.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

//It holds all data local and api
interface ForecastRepository {
    suspend fun getCurrentWeather(metric:Boolean) : LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>
    suspend fun getFutureWeatherByDate(date: LocalDate, metric: Boolean): LiveData<out UnitSpecificDetailFutureWeatherEntry>
}