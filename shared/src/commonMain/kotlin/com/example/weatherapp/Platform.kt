package com.example.weatherapp

import com.example.weatherapp.repo.WeatherRepository
import io.ktor.client.*

expect class Platform() {

    suspend fun fetchCurrentWeather(city: String): WeatherRepository.UIModel
}

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient