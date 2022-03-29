package com.emix.tempest.ui.weather.current

import androidx.lifecycle.ViewModel
import com.emix.tempest.data.provider.UnitProvider
import com.emix.tempest.data.repository.ForecastRepository
import com.emix.tempest.internal.UnitSystem
import com.emix.tempest.internal.lazyDeferred
import com.emix.tempest.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    //To make it initialized when it is called we use lazy property of coroutine
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }
}