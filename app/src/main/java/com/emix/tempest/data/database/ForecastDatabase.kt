package com.emix.tempest.data.database

import android.content.Context
import androidx.room.*
import com.emix.tempest.data.database.entity.CurrentWeatherEntry
import com.emix.tempest.data.database.entity.FutureWeatherEntry
import com.emix.tempest.data.database.entity.WeatherLocation

//Database class
@Database(
    entities = [CurrentWeatherEntry::class, FutureWeatherEntry::class, WeatherLocation::class],
    version = 1
)
@TypeConverters(LocalDateConverter::class)
abstract class ForecastDatabase : RoomDatabase(){
    abstract fun futureWeatherDao():FutureWeatherDao
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object{
        //To get immediate access to this instance
        @Volatile private var instance: ForecastDatabase? = null
        //we need lock object because of thread to lock a process
        private val LOCK = Any()

        operator fun invoke(context:Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{ instance = it}
        }

        //To initialize database
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java, "futureWeatherEntries.db")
                .build()
    }
}