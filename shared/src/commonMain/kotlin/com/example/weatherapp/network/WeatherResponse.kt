package com.example.weatherapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// This file might be extended to cover all the fields of the JSON response

@Serializable
data class WeatherResponse(
    val coord: Coord,
    @SerialName("main")
    val temperatureInfo: TemperatureInfo,
    @SerialName("weather")
    val weatherCondition: List<WeatherCondition>,
    val name: String,
)
@Serializable
data class Coord(
    val lon: Float,
    val lat: Float
)
@Serializable
data class WeatherCondition(
    @SerialName("id")
    val weatherConditionId: Int,
    @SerialName("main")
    val weatherConditionName: String,
    @SerialName("description")
    val weatherConditionDescription: String,
    @SerialName("icon")
    val weatherConditionIcon: String
)
@Serializable
data class TemperatureInfo(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float
)