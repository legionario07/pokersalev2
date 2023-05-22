package br.com.khodahafez.domain.model

data class Profile(
    val id: Int,
    val name: String? = null,
    val password: String? = null
)
