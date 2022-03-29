package com.emix.tempest.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emix.tempest.data.provider.UnitProvider
import com.emix.tempest.data.repository.ForecastRepository

/**
 * This class will help preserving data from getting destroyed because of lifecycle
 * factory creates object while viewModelProvider preserve data
 */
class CurrentWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository,unitProvider) as T
    }
}