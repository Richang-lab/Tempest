package com.emix.tempest.ui.weather.future.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.emix.tempest.data.database.LocalDateConverter
import com.emix.tempest.databinding.FutureDetailWeatherFragmentBinding
import com.emix.tempest.internal.DateNotFoundException
import com.emix.tempest.internal.glide.GlideApp
import com.emix.tempest.ui.base.ImageSelector
import com.emix.tempest.ui.base.ScopedFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureDetailWeatherFragment : ScopedFragment(), KodeinAware {

    lateinit var component: FutureDetailWeatherFragmentBinding

    override val kodein by closestKodein()

    private val viewModelFactoryInstanceFactory :((LocalDate) -> FutureDetailWeatherViewModelFactory) by factory()

    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component = FutureDetailWeatherFragmentBinding.inflate(inflater, container, false)
        return component.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        hideActionBar()
        val safeArgs = arguments?.let {
            FutureDetailWeatherFragmentArgs.fromBundle(it) }
        val date = LocalDateConverter.stringToDate(safeArgs?.dateString) ?: throw DateNotFoundException()

        viewModel = ViewModelProvider(this, viewModelFactoryInstanceFactory(date))[FutureDetailWeatherViewModel::class.java]
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(viewLifecycleOwner, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        futureWeather.observe(viewLifecycleOwner, Observer { weatherEntry ->
            if (weatherEntry == null) return@Observer

            updateDate(weatherEntry.date)
            updateTemperatures(weatherEntry.avgTemperature,
                weatherEntry.minTemperature, weatherEntry.maxTemperature)
            updateCondition(weatherEntry.conditionText)
            updatePrecipitation(weatherEntry.totalPrecipitation)
            updateWindSpeed(weatherEntry.maxWindSpeed)
            updateVisibility(weatherEntry.avgVisibilityDistance)
            updateUv(weatherEntry.uv)
            updateBackground(weatherEntry.conditionCode)
            updateHumidity(weatherEntry.avgHumidity)

            GlideApp.with(this@FutureDetailWeatherFragment)
                .load("https:" + weatherEntry.conditionIconUrl)
                .into(component.imageViewConditionIcon)
        })
    }

    private fun updateHumidity(humidity: Double) {
        component.humidity.text = "${humidity.toInt()}"
    }
    private fun updateBackground(code:Int){
        component.backgroundImage.setImageResource(ImageSelector.backgroundImage(code))
    }

    private fun hideActionBar(){
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetricUnit) metric else imperial
    }

    private fun updateLocation(location: String) {
        component.textViewLocation.text = location
    }

    private fun updateDate(date: LocalDate) {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }

    private fun updateTemperatures(temperature: Double, min: Double, max: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        component.textViewTemperature.text = "$temperature$unitAbbreviation"
        component.minTemperature.text = "$min$unitAbbreviation"
        component.maxTemperature.text = "$max$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        component.textViewCondition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        component.precipitation.text = "$precipitationVolume $unitAbbreviation"
    }

    private fun updateWindSpeed(windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        component.speed.text = "${windSpeed.toInt()} $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.")
        component.visibility.text = "$visibilityDistance $unitAbbreviation"
    }

    private fun updateUv(uv: Double) {
        component.uv.text = "$uv"
    }
}