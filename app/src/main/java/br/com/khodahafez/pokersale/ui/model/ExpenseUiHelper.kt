package br.com.khodahafez.pokersale.ui.model

import br.com.khodahafez.domain.model.Player

data class ExpenseUiHelper(
    val player: Player? = null,
    val totalEntries: Double? = null,
    val totalPrizes: Double? = null,
)
