package com.emix.tempest.ui.weather.future.list

import com.emix.tempest.data.provider.UnitProvider
import com.emix.tempest.data.repository.ForecastRepository
import com.emix.tempest.internal.lazyDeferred
import com.emix.tempest.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weatherEntries by lazyDeferred {
        forecastRepository.getFutureWeatherList(LocalDate.now(),super.isMetricUnit)
    }
}