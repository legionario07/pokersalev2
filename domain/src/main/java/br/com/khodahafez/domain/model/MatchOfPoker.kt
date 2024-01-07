package br.com.khodahafez.domain.model

import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING

data class MatchOfPoker(
    val id: String? = null,
    val date: String? = EMPTY_STRING,
    val isSpecialMatch: Boolean = false,
    val players: Set<Player> = mutableSetOf(),
    val ranking: Int = 1,
    val matchOfPokerType: MatchOfPokerType = MatchOfPokerType.IN_PROGRESS
)
