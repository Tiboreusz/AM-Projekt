package com.example.am_projekt.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.*

fun updateLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}