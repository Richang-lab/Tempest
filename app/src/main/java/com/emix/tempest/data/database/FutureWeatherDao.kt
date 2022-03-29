package com.emix.tempest.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emix.tempest.data.database.entity.FutureWeatherEntry
import com.emix.tempest.data.database.unitlocalized.future.detail.ImperialDetailFutureWeatherEntry
import com.emix.tempest.data.database.unitlocalized.future.detail.MetricDetailFutureWeatherEntry
import com.emix.tempest.data.database.unitlocalized.future.list.ImperialSimpleFutureWeatherEntry
import com.emix.tempest.data.database.unitlocalized.future.list.MetricSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntry>)

    @Query("Select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastMetric(startDate: LocalDate): LiveData<List<MetricSimpleFutureWeatherEntry>>

    @Query("Select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastImperial(startDate: LocalDate): LiveData<List<ImperialSimpleFutureWeatherEntry>>

    @Query("Select * from future_weather where date(date) = date(:date)")
    fun getDetailWeatherForecastMetric(date: LocalDate): LiveData<MetricDetailFutureWeatherEntry>

    @Query("Select * from future_weather where date(date) = date(:date)")
    fun getDetailWeatherForecastImperial(date: LocalDate): LiveData<ImperialDetailFutureWeatherEntry>

    @Query("select count(id) from future_weather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("delete from future_weather where date(date) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate)

}