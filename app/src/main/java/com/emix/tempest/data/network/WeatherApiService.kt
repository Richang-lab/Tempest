package com.emix.tempest.data.network

import com.emix.tempest.data.network.ConnectivityInterceptor
import com.emix.tempest.data.network.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "98d58c25e3a94b8286242312220303"

//http://api.weatherapi.com/v1/current.json?key=98d58c25e3a94b8286242312220303&q=London&aqi=no

interface WeatherApiService {

    /**
     * It is get request and
       Deferred is used to execute this function with await() to do this asynchronously
       It returns CurrentWeatherResponse as it wraps location and currentWeatherEntry classes which is representation of api structure in kotlin
     */
    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location:String
    ) : Deferred<CurrentWeatherResponse>

    //Static function
    companion object{
        /**
         * Interceptor modifies and observe url
         *
         */
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): WeatherApiService{
            val requestInterceptor = Interceptor{ chain->
                // to make use of key
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                //adding key to request url it contains updated url
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            //to intercept every call we add interceptor with okHttpClient
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            /**
             * to return Weather api service, use Retrofit
             * because we are using Deferred we need adapterFactory
             * to parse json to Gson add ConverterFactory with GSON ConverterFactory
             * Create implementation of the return type
             */
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.weatherapi.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }

}