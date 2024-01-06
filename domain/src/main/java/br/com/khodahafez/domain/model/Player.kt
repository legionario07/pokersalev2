package br.com.khodahafez.domain.model

import br.com.khodahafez.domain.PokerSaleConstants

data class Player(
    var id: String? = null,
    val name: String = "",
    var login: String = PokerSaleConstants.EMPTY_STRING,
    var password: String = PokerSaleConstants.EMPTY_STRING
)
