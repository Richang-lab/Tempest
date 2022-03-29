package com.emix.tempest.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.emix.tempest.data.provider.UnitProvider
import com.emix.tempest.data.repository.ForecastRepository
import com.emix.tempest.internal.lazyDeferred
import com.emix.tempest.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeferred {
        forecastRepository.getFutureWeatherByDate(detailDate,super.isMetricUnit)
    }
}