package br.com.khodahafez.domain.model.screen

import br.com.khodahafez.domain.model.Player

data class ExpenseUiHelper(
    val player: Player? = null,
    val totalEntries: Double? = null,
    val totalPrizes: Double? = null,
)
