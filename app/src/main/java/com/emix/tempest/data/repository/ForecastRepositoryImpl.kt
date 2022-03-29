package com.emix.tempest.data.repository

import androidx.lifecycle.LiveData
import com.emix.tempest.data.database.CurrentWeatherDao
import com.emix.tempest.data.database.FutureWeatherDao
import com.emix.tempest.data.database.WeatherLocationDao
import com.emix.tempest.data.database.entity.WeatherLocation
import com.emix.tempest.data.database.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.emix.tempest.data.database.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.emix.tempest.data.database.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.emix.tempest.data.network.FORECAST_DAYS_COUNT
import com.emix.tempest.data.network.WeatherNetworkDataSource
import com.emix.tempest.data.network.response.CurrentWeatherResponse
import com.emix.tempest.data.network.response.FutureWeatherResponse
import com.emix.tempest.data.provider.LocationProvider
import kotlinx.coroutines.*
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime


class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val locationProvider: LocationProvider,
    private val weatherLocationDao: WeatherLocationDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever{ newCurrentWeather->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever{ newFutureWeather ->
                persistFetchedFutureWeather(newFutureWeather)
            }
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if(metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }

    override suspend fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if(metric) futureWeatherDao.getSimpleWeatherForecastMetric(startDate)
            else futureWeatherDao.getSimpleWeatherForecastImperial(startDate)
        }
    }

    override suspend fun getFutureWeatherByDate(
        date: LocalDate,
        metric: Boolean
    ): LiveData<out UnitSpecificDetailFutureWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if(metric) futureWeatherDao.getDetailWeatherForecastMetric(date)
            else futureWeatherDao.getDetailWeatherForecastImperial(date)
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    //store api data to local database when we receive api response

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }


    private fun persistFetchedFutureWeather(fetchedWeather:FutureWeatherResponse){
        fun deleteOldForecastData(){
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.futureWeatherEntries.entries
            futureWeatherDao.insert(futureWeatherList)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData(){
        val lastWeatherLocation =  weatherLocationDao.getLocationNonLive()
        if(lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }
        if(isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime)){
            fetchCurrentWeather()
        }

        if(isFetchFutureNeeded()){
            fetchFutureWeather()
        }
    }

    private suspend fun fetchFutureWeather() {
        weatherNetworkDataSource.fetchFutureWeather(
            locationProvider.getPreferredLocationString())
    }

    private fun isFetchFutureNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString())
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime):Boolean{
        val thirtyMinuteAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinuteAgo)
    }
}