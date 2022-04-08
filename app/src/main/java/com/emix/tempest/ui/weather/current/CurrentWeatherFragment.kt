package com.emix.tempest.ui.weather.current


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.emix.tempest.databinding.CurrentWeatherFragmentBinding
import com.emix.tempest.internal.glide.GlideApp
import com.emix.tempest.ui.base.ImageSelector
import com.emix.tempest.ui.base.ScopedFragment
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
        hideActionBar()
        viewModel = ViewModelProvider(this,viewModelFactory)[CurrentWeatherViewModel::class.java]
        bindUI()
        /*fragmentComponent.swipe.setOnRefreshListener {
            //bindUI()
            refresh()
            fragmentComponent.swipe.isRefreshing = false
        }*/
    }


    private fun blur(){

    }

    private fun refresh(){
        val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
        ft.detach(this)
        ft.attach(this)
        ft.commit()
    }

    private fun bindUI() = launch{
        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(viewLifecycleOwner, Observer {location->
            if(location == null) return@Observer
            updateLocation(location.name)
        })

        currentWeather.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer

            fragmentComponent.groupLoading.visibility = View.GONE
            updateDateToToday()
            updateBackground(it.conditionCode)
            updateTemperature(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection,it.windSpeed)
            updateVisibility(it.visibilityDistance)
            updateUv(it.uv)
            updateHumidity(it.humidity)
            GlideApp.with(this@CurrentWeatherFragment)
                .load("https:${it.conditionIconUrl}")
                .into(fragmentComponent.imageViewConditionIcon)
        })
    }


    private fun updateBackground(code:Int){
        fragmentComponent.backgroundImage.setImageResource(ImageSelector.backgroundImage(code))
    }

    private fun chooseLocalizedUnitAbbreviation(metric:String, imperial: String):String{
        return if(viewModel.isMetricUnit) metric else imperial
    }

    private fun hideActionBar(){
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    private fun updateLocation(location: String){
        fragmentComponent.textViewLocation.text = location
    }

    private fun updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature: Double,feelsLike: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "°C", "°F")
        fragmentComponent.textViewTemperature.text = "${temperature.toInt()}$unitAbbreviation"
        fragmentComponent.feelsLikeTemperature.text = "${feelsLike.toInt()}$unitAbbreviation"
    }

    private fun updateCondition(condition:String){
        fragmentComponent.textViewCondition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "mm", "in")
        fragmentComponent.precipitation.text = "${precipitationVolume.toInt()} $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "kph", "mph")
        fragmentComponent.speed.text = "${windSpeed.toInt()} $unitAbbreviation"
        fragmentComponent.direction.text = windDirection
    }

    private fun updateVisibility(visibilityDistance: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation( "km", "mi")
        fragmentComponent.visibility.text = "${visibilityDistance.toInt()} $unitAbbreviation"
    }
    private fun updateHumidity(humidity: Int){
        fragmentComponent.humidity.text = "${humidity}"
    }
    private fun updateUv(uv: Double){
        fragmentComponent.uv.text = "${uv.toInt()}"
    }


}