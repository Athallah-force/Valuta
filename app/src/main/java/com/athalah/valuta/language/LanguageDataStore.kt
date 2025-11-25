package com.athalah.valuta.language

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

// EXTENSION untuk membuat DataStore
val Context.dataStore by preferencesDataStore(name = "settings")

object LanguageDataStore {

    private val KEY_LANGUAGE = stringPreferencesKey("app_language")

    // Simpan bahasa
    fun saveLanguage(context: Context, lang: String) = runBlocking {
        context.dataStore.edit { prefs ->
            prefs[KEY_LANGUAGE] = lang
        }
    }

    // Ambil bahasa (default en)
    fun getLanguage(context: Context): Flow<String> {
        return context.dataStore.data.map { prefs ->
            prefs[KEY_LANGUAGE] ?: "en"
        }
    }
}
