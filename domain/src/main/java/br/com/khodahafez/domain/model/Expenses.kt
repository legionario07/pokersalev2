package br.com.khodahafez.domain.model

data class Expenses(
    val id: String? = null,
    val player: Player? = null,
    val totalEntries: Double? = null,
    val cashPrize: Double? = null,
    val matchOfPoker: MatchOfPoker
)
