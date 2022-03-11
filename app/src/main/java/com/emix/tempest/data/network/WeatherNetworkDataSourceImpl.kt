package com.emix.tempest.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emix.tempest.data.network.response.CurrentWeatherResponse
import com.emix.tempest.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val weatherApiService: WeatherApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val fetchCurrentWeather = weatherApiService
                .getCurrentWeather(location)
                .await()
            _downloadedCurrentWeather.postValue(fetchCurrentWeather)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity","No Internet Connection",e)
        }
    }
}