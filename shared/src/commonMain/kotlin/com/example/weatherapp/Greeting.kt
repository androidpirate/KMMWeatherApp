package com.example.weatherapp

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class Greeting {

    fun greeting(): String {
        return "Open Weather API Response: Test!"
    }
}