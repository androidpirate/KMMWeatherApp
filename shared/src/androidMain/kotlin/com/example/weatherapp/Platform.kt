package com.example.weatherapp

import com.example.weatherapp.network.WeatherApiServiceImp
import com.example.weatherapp.repo.WeatherRepository

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"

    actual suspend fun fetchCurrentWeather(city: String): WeatherRepository.UIModel {
        val repo = WeatherRepository(WeatherApiServiceImp(), city)
        return repo.fetchCurrentWeather()
    }
}