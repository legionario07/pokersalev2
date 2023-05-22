package br.com.khodahafez.domain.model

data class Player(
    val id: Int,
    val name: String? = null,
    val profile: Profile? = null,
)
