package com.athalah.valuta.language

import android.content.Context
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import java.util.Locale

object LocaleHelper {

    var currentLocale = mutableStateOf(Locale("en"))

    fun updateLocale(context: Context, language: String) {
        val locale = Locale(language)
        currentLocale.value = locale

        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)

    }

    fun wrap(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

}


