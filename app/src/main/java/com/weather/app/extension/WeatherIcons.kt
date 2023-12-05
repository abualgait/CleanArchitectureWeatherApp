package com.weather.app.extension

import com.weather.app.R

fun Int.getDrawable(): Int {
    return when (this) {
        1, 2, 3 -> R.drawable.sunny
        4, 5, 6, 7, 20, 21, 22, 23, 24 -> R.drawable.cloudy
        8, 11, 30, 31, 32, 19 -> R.drawable.clouds
        12, 18, 25, 26, 29 -> R.drawable.rainy
        13, 14 -> R.drawable.partly_cloudy
        15, 16, 17, 39, 40, 41, 42 -> R.drawable.rain_lightning
        33, 34, 35 -> R.drawable.sunny_night
        36, 37, 38, 43, 44 -> R.drawable.cloudy_night
        else -> {
            R.drawable.ic_launcher_foreground
        }
    }
}