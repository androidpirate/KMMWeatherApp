package com.example.weatherapp.network

import kotlinx.serialization.Serializable

// This file might be extended to cover all the fields of the JSON response

@Serializable
data class ApiResponse(
    val coord: Coord,
    val main: Main,
    val name: String
)
@Serializable
data class Coord(
    val lon: Float,
    val lat: Float
)
@Serializable
data class Main(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float
)