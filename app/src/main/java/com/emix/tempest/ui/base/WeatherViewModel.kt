package com.emix.tempest.ui.base

import androidx.lifecycle.ViewModel
import com.emix.tempest.data.provider.UnitProvider
import com.emix.tempest.data.repository.ForecastRepository
import com.emix.tempest.internal.UnitSystem
import com.emix.tempest.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
): ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}