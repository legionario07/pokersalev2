package br.com.khodahafez.domain.errors

sealed class PokerSaleV2Error {
    data class GenericError(val error: Throwable) : PokerSaleV2Error()
}
