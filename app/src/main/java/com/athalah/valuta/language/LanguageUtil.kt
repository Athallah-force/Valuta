package com.athalah.valuta

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LanguageUtil {

    fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun setLocaleActivity(activity: Activity, language: String) {
        setLocale(activity, language)
        activity.recreate()    // refresh UI
    }
}
