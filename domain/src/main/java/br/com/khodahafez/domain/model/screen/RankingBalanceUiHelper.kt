package br.com.khodahafez.domain.model.screen

import br.com.khodahafez.domain.model.Player

data class RankingBalanceUiHelper(
    val idPlayer: String? = null,
    var namePlayer: String? = null,
    val totalEntries: Double? = null,
    val totalPrizes: Double? = null,
    val profit: Double = 0.0
)
