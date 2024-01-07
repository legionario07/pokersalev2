package br.com.khodahafez.domain.model

import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING

data class Balance(
    var id: String? = null,
    var date: String,
    var operationType: BankType,
    var reason: String = EMPTY_STRING
)
