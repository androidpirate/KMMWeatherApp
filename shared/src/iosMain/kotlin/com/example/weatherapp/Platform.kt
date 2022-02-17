package com.example.weatherapp

import com.example.weatherapp.network.WeatherApiServiceImp
import com.example.weatherapp.repo.WeatherRepository
import io.ktor.client.*
import io.ktor.client.engine.ios.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

actual class Platform actual constructor() {

    actual suspend fun fetchCurrentWeather(city: String): WeatherRepository.UIModel {
        val client = httpClient()
        val repo = WeatherRepository(WeatherApiServiceImp(client), city)
        return repo.fetchCurrentWeather()
    }
}

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(Ios) {
    config(this)

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

    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}
