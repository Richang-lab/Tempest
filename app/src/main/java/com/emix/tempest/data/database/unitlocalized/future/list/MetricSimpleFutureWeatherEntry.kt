package com.emix.tempest.data.database.unitlocalized.future.list

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

data class MetricSimpleFutureWeatherEntry(
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "avgtempC")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "condition_code")
    override val conditionCode: Int
): UnitSpecificSimpleFutureWeatherEntry
