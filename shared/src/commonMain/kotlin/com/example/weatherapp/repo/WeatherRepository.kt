package com.example.weatherapp.repo

import com.example.weatherapp.network.ApiResponse
import com.example.weatherapp.network.WeatherApiServiceImp

class WeatherRepository(private val service: WeatherApiServiceImp, private val city: String) {

    suspend fun fetchCurrentWeather(): UIModel {
        val response = service.fetchCurrentWeather(city)
        // TODO: Data can be persist first and then shared through there as an Observable
        return toUIModel(response)
    }

    suspend fun persistCurrentWeather() {
        // TODO: Persist the fetched weather data into local database
    }

    // TODO: This method should be implemented in the ViewModel class instead
    private suspend fun toUIModel(response: ApiResponse): UIModel{
        return UIModel(
            response.name,
            response.main.temp.toInt(),
            response.main.temp_min.toInt(),
            response.main.temp_max.toInt())
    }

    // TODO: This data class should be implemented in the ViewModel class instead
    data class UIModel(
        val city: String,
        val temp: Int,
        val minTemp: Int,
        val maxTemp: Int
    )

}