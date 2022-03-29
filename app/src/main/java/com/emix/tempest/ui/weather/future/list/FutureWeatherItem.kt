package com.emix.tempest.ui.weather.future.list

import android.widget.ImageView
import android.widget.TextView
import com.emix.tempest.R
import com.emix.tempest.data.database.unitlocalized.future.list.MetricSimpleFutureWeatherEntry
import com.emix.tempest.data.database.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import com.emix.tempest.internal.glide.GlideApp
import com.xwray.groupie.Item
import com.xwray.groupie.GroupieViewHolder
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureWeatherItem(
    val weatherEntry: UnitSpecificSimpleFutureWeatherEntry
): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            val textViewCondition = this.root.findViewById<TextView>(R.id.textView_condition)
            textViewCondition.text = weatherEntry.conditionText
            updateDate()
            updateTemperature()
            updateConditionImage()
        }
    }

    override fun getLayout() = R.layout.item_future_weather

    private fun GroupieViewHolder.updateDate(){
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val textViewDate = this.root.findViewById<TextView>(R.id.textView_date)
        textViewDate.text = weatherEntry.date.format(dtFormatter)
    }

    private fun GroupieViewHolder.updateTemperature(){
        val unitAbbrivation = if(weatherEntry is MetricSimpleFutureWeatherEntry) "°C"
        else "°F"
        val textViewTemperature = this.root.findViewById<TextView>(R.id.textView_temperature)
        textViewTemperature.text = "${weatherEntry.avgTemperature}$unitAbbrivation"
    }

    private fun GroupieViewHolder.updateConditionImage(){
        val imageViewConditionIcon = this.root.findViewById<ImageView>(R.id.imageView_condition_icon)
        GlideApp.with(this.itemView)
            .load("https:" + weatherEntry.conditionIconUrl)
            .into(imageViewConditionIcon)
    }

}