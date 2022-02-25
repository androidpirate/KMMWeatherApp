package com.example.weatherapp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.weatherapp.Platform
import com.example.weatherapp.repo.WeatherRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val platform = Platform()

    private val coroutineScope = MainScope()

    private lateinit var error: TextView
    private lateinit var  city: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var weatherCondition: TextView
    private lateinit var weatherConditionDesc: TextView
    private lateinit var temp: TextView
    private lateinit var minTemp: TextView
    private lateinit var maxTemp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        error = findViewById(R.id.error)
        city = findViewById(R.id.city)
        weatherIcon = findViewById(R.id.conditionIcon)
        weatherCondition = findViewById(R.id.weatherCondition)
        weatherConditionDesc = findViewById(R.id.weatherConditionDesc)
        temp = findViewById(R.id.currentWeather)
        minTemp = findViewById(R.id.minTemp)
        maxTemp= findViewById(R.id.maxTemp)

        city.text = "Loading..."

        coroutineScope.launch {
            kotlin.runCatching {
                platform.fetchCurrentWeather("Los Angeles")
            }.onSuccess {
                displayTemp(it)
            }.onFailure {
                displayError(it)
            }
        }
    }

    private fun displayTemp(data: WeatherRepository.UIModel) {
        error.visibility = View.GONE
        city.visibility = View.VISIBLE
        weatherIcon.visibility = View.VISIBLE
        weatherCondition.visibility = View.VISIBLE
        weatherConditionDesc.visibility = View.VISIBLE
        temp.visibility = View.VISIBLE
        minTemp.visibility = View.VISIBLE
        maxTemp.visibility = View.VISIBLE
        city.text = data.city
        temp.text = data.temp.toString()
        loadWeatherIcon(data.weatherConditionIconUri)
        weatherCondition.text = data.weatherCondition
        weatherConditionDesc.text = data.weatherConditionDesc.uppercase()
        minTemp.text = data.minTemp.toString()
        maxTemp.text = data.maxTemp.toString()
    }

    private fun displayError(ex: Throwable) {
        error.visibility = View.VISIBLE
        city.visibility = View.GONE
        weatherIcon.visibility = View.GONE
        weatherCondition.visibility = View.GONE
        weatherConditionDesc.visibility = View.GONE
        temp.visibility = View.GONE
        minTemp.visibility = View.GONE
        maxTemp.visibility = View.GONE
        val errorMessage = "Error ${ex.localizedMessage}"
        error.text = errorMessage
        Log.e("MainActivity", "HTTP request exception!")
    }

    private fun loadWeatherIcon(iconUri: String) {
        // Load weather icon
        if(iconUri.isNotEmpty()) {
            Glide.with(this).load(iconUri).into(weatherIcon)
        }
    }
}
