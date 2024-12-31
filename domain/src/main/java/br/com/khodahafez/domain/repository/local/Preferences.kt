package br.com.khodahafez.domain.repository.local

interface Preferences {

    fun <T> save(key: String, value: T)
    fun <T> get(key: String, classType: Class<T>): T?
}
