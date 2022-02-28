package com.example.weatherapp.repo

import com.example.weatherapp.network.WeatherResponse
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
    private fun toUIModel(response: WeatherResponse): UIModel{
        return UIModel(
            response.name,
            response.temperatureInfo.temp.toInt(),
            response.weatherCondition[0].weatherConditionName,
            response.weatherCondition[0].weatherConditionDescription,
            ICON_BASE_URI
                    + response.weatherCondition[0].weatherConditionIcon
                    + ICON_URI_SUFFIX,
            response.temperatureInfo.temp_min.toInt(),
            response.temperatureInfo.temp_max.toInt())
    }

    // TODO: This data class should be implemented in the ViewModel class instead
    data class UIModel(
        val city: String,
        val temp: Int,
        val weatherCondition: String,
        val weatherConditionDesc: String,
        val weatherConditionIconUri: String,
        val minTemp: Int,
        val maxTemp: Int
    )

    companion object {
        const val ICON_BASE_URI = "https://openweathermap.org/img/wn/"
        const val ICON_URI_SUFFIX = "@2x.png"
    }

}