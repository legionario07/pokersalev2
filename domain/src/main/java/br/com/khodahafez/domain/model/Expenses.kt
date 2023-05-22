package br.com.khodahafez.domain.model

data class Expenses(
    val id: Int,
    val player: Player? = null,
    val buyIn: Double? = null,
    val reBuy: Double? = null,
    val addOn: Double? = null,
    val double: Double? = null,
    val award: Double? = null
)
