package br.com.khodahafez.domain.model

import br.com.khodahafez.domain.PokerSaleConstants

data class Player(
    var id: String? = null,
    val name: String = PokerSaleConstants.EMPTY_STRING,
    val lastAccess: String = PokerSaleConstants.EMPTY_STRING,
    val createdAt: String = PokerSaleConstants.EMPTY_STRING,
    var login: String = PokerSaleConstants.EMPTY_STRING,
    var password: String = PokerSaleConstants.EMPTY_STRING
)
