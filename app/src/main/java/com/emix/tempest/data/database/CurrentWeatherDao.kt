package com.emix.tempest.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emix.tempest.data.database.entity.CURRENT_WEATHER_ID
import com.emix.tempest.data.database.entity.CurrentWeatherEntry
import com.emix.tempest.data.database.unitlocalized.current.ImperialCurrentWeatherEntry
import com.emix.tempest.data.database.unitlocalized.current.MetricCurrentWeatherEntry

//Data Access Object it access data to perform some function
@Dao
interface CurrentWeatherDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    //Livedata is used to observe data as we are using mvvm we can't change data directly with component we need view model
    @Query("Select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("Select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>

}