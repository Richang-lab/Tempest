package com.emix.tempest.ui.weather.current

import androidx.lifecycle.ViewModel
import com.emix.tempest.data.repository.ForecastRepository
import com.emix.tempest.internal.UnitSystem
import com.emix.tempest.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC
    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}