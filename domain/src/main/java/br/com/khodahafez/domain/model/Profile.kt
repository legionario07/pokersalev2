package br.com.khodahafez.domain.model

import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING

data class Profile(
    var id: String? = null,
    var login: String = EMPTY_STRING,
    var password: String = EMPTY_STRING
)
