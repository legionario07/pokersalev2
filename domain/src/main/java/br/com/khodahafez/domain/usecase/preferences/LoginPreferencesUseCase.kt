package br.com.khodahafez.domain.usecase.preferences

import br.com.khodahafez.domain.repository.local.Preferences

class LoginPreferencesUseCase(
    private val preferences: Preferences
) {
    fun <T> save(key: String, value: T) {
        preferences.save(
            key = key,
            value = value
        )
    }

    fun <T> get(key: String, clazz: Class<T>) =
        preferences.get(key, clazz)
}
