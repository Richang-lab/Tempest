package com.emix.tempest.ui.weather.current

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.emix.tempest.R
import com.emix.tempest.data.network.ConnectivityInterceptorImpl
import com.emix.tempest.data.network.WeatherApiService
import com.emix.tempest.data.network.WeatherNetworkDataSourceImpl
import com.emix.tempest.databinding.CurrentWeatherFragmentBinding
import com.emix.tempest.internal.glide.GlideApp
import com.emix.tempest.ui.base.ScopedFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {
    private lateinit var fragmentComponent: CurrentWeatherFragmentBinding

    override val kodein: Kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentComponent = CurrentWeatherFragmentBinding.inflate(inflater, container, false)
        return fragmentComponent.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[CurrentWeatherViewModel::class.java]
        bindUI()

    }

    private fun bindUI() = launch{
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer

            fragmentComponent.groupLoading.visibility = View.GONE
            updateLocation("Los Angeles")
            updateDateToToday()
            updateTemperature(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection,it.windSpeed)
            updateVisibility(it.visibilityDistance)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("https:${it.conditionIconUrl}")
                .into(fragmentComponent.imageViewConditionIcon)
            Log.d("icon", "bindUI: http:${it.conditionIconUrl}")
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric:String, imperial: String):String{
        return if(viewModel.isMetric) metric else imperial
    }

    private fun updateLocation(location: String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature: Double,feelsLike: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "°C", "°F")
        fragmentComponent.textViewTemperature.text = "$temperature$unitAbbreviation"
        fragmentComponent.textViewFeelsLikeTemperature.text = "$feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition:String){
        fragmentComponent.textViewCondition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "mm", "in")
        fragmentComponent.textViewPrecipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "kph", "mph")
        fragmentComponent.textViewWind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "km", "mi")
        fragmentComponent.textViewVisibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }


}