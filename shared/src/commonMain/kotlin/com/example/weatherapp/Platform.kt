package com.example.weatherapp

import com.example.weatherapp.network.ApiResponse
import com.example.weatherapp.repo.WeatherRepository

expect class Platform() {
    val platform: String

    suspend fun fetchCurrentWeather(city: String): WeatherRepository.UIModel
}