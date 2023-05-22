package br.com.khodahafez.domain.repository

interface PlayerRepository {
    fun logon(name: String, password: String): Any
    fun findAll(): List<Any>
}
