package com.emix.tempest.data.network

import androidx.lifecycle.LiveData
import com.emix.tempest.data.network.response.CurrentWeatherResponse
import com.emix.tempest.data.network.response.FutureWeatherResponse

/**
 *  The function will not return result from api
 *  Rather it will update downloadCurrentWeather
 *  So that it can be observed in repository class
 */

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String
    )

    suspend fun fetchFutureWeather(
        location: String
    )
}