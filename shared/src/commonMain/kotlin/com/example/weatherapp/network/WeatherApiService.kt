package com.example.weatherapp.network

interface WeatherApiService {
    suspend fun fetchCurrentWeather(city: String): WeatherResponse
}