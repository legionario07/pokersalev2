package br.com.khodahafez.domain.model

import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING
import br.com.khodahafez.domain.model.BankType

data class Balance(
    var id: String? = null,
    var namePlayer: String = EMPTY_STRING,
    var value: Double = 0.0,
    var date: String? = null,
    var operationType: BankType? = null,
    var reason: String = EMPTY_STRING
)
