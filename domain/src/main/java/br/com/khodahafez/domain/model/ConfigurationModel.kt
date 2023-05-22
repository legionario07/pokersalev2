package br.com.khodahafez.domain.model

data class ConfigurationModel(
    val id: Int,
    val property: String? = null,
    val value: String? = null
)
