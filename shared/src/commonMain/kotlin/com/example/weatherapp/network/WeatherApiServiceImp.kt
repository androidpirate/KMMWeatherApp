package com.example.weatherapp.network

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*

class WeatherApiServiceImp: WeatherApiService {

    private val httpClient by lazy {
        HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    override
    suspend fun fetchCurrentWeather(city: String): ApiResponse {
        return httpClient.get(BASE_URL) {
            parameter(LOCATION_PARAM, city)
            parameter(UNITS_PARAM, UNITS_METRIC)
            parameter(API_PARAM, API_KEY)
            // TODO: Remove printing url string later
            println(this.url.build().toString())
        }
    }

    companion object {
        private const val BASE_URL: String = "https://api.openweathermap.org/data/2.5/weather"
        private const val LOCATION_PARAM: String = "q"
        private const val API_PARAM: String = "appid"
        private const val UNITS_PARAM: String = "units"
        private const val UNITS_METRIC: String = "metric"
        private const val UNITS_IMPERIAL: String = "imperial"
        private const val API_KEY: String = "7cdc4e78dd55e59dc24737c85f35151a"

    }
}