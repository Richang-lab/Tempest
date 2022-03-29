package com.emix.tempest.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emix.tempest.data.network.response.CurrentWeatherResponse
import com.emix.tempest.data.network.response.FutureWeatherResponse
import com.emix.tempest.internal.NoConnectivityException

/**
 * To avoid direct call to api
 * To handle exceptions
*/

const val FORECAST_DAYS_COUNT = 7

class WeatherNetworkDataSourceImpl(
    private val weatherApiService: WeatherApiService
) : WeatherNetworkDataSource {
    //Since LiveData can't be changed we will need a mutable live data
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    //when we need downloadedCurrentWeather we will send _downloadedCurrentWeather which will be casted as LiveData making it unchangeable
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val fetchedCurrentWeather = weatherApiService
                .getCurrentWeather(location)
                .await()
            //updating downloadedCurrentWeather
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity","No Internet Connection",e)
        }
    }

    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchFutureWeather(location: String) {
        try {
            val fetchedFutureWeather = weatherApiService
                .getFutureWeather(location, FORECAST_DAYS_COUNT)
                .await()
            //updating downloadedCurrentWeather
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity","No Internet Connection",e)
        }
    }
}