package com.example.weatherapp

import com.example.weatherapp.network.WeatherApiServiceImp
import com.example.weatherapp.repo.WeatherRepository
import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    actual suspend fun fetchCurrentWeather(city: String): WeatherRepository.UIModel {
        val service = WeatherApiServiceImp()
        val repo = WeatherRepository(service, city)
        return repo.fetchCurrentWeather()
    }
}