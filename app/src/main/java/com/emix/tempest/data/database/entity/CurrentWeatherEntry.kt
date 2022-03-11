package com.emix.tempest.data.database.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//api representation in kotlin class which was turned into a database entity(table) where each property is column
const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    /** embedded is used to use a another class's property for database as sql can hold only premitive data
        serialized name is used to name Gson property different than property name in class
    **/
    @Embedded(prefix = "condition_")
    val condition: Condition,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("feelslike_c")
    val feelslikeC: Double,
    @SerializedName("feelslike_f")
    val feelslikeF: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("precip_in")
    val precipIn: Double,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("vis_km")
    val visKm: Double,
    @SerializedName("vis_miles")
    val visMiles: Double
){
    //since we need one entry in our table we will make it static primary key
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}

/**
 * properties for future update
val humidity: Int,
@SerializedName("last_updated")
val lastUpdated: String,
@SerializedName("last_updated_epoch")
val lastUpdatedEpoch: Int,
val cloud: Int,
@SerializedName("pressure_in")
val pressureIn: Double,
@SerializedName("pressure_mb")
val pressureMb: Double,
val uv: Double,
@SerializedName("wind_degree")
val windDegree: Int,
@SerializedName("gust_kph")
val gustKph: Double,
@SerializedName("gust_mph")
val gustMph: Double*/