package com.emix.tempest

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.emix.tempest.data.database.ForecastDatabase
import com.emix.tempest.data.network.*
import com.emix.tempest.data.provider.LocationProvider
import com.emix.tempest.data.provider.LocationProviderImpl
import com.emix.tempest.data.provider.UnitProvider
import com.emix.tempest.data.provider.UnitProviderImpl
import com.emix.tempest.data.repository.ForecastRepository
import com.emix.tempest.data.repository.ForecastRepositoryImpl
import com.emix.tempest.ui.weather.current.CurrentWeatherViewModelFactory
import com.emix.tempest.ui.weather.future.detail.FutureDetailWeatherViewModelFactory
import com.emix.tempest.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

//Dependency injection this class will inject dependency when a class need one
class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao()}
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(),instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance(), instance())}
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
        bind() from factory{ detailDate: LocalDate -> FutureDetailWeatherViewModelFactory(detailDate,instance(),instance())}
    }

    // to initialize library we use onCreate
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}