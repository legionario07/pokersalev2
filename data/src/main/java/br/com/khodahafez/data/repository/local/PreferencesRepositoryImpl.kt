package br.com.khodahafez.data.repository.local

import android.content.SharedPreferences
import br.com.khodahafez.domain.repository.local.Preferences
import com.google.gson.Gson

class PreferencesRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : Preferences {
    companion object {
        private const val DEFAULT_VALUE = ""
    }

    override fun <T> save(key: String, value: T) {
        sharedPreferences.edit().apply {
            putString(
                key,
                gson.toJson(value)
            )
            commit()
        }
    }

    override fun <T> get(key: String, classType: Class<T>): T? {
        return gson.fromJson(
            sharedPreferences.getString(key, DEFAULT_VALUE),
            classType
        )
    }
}
