package br.com.khodahafez.domain.state

import br.com.khodahafez.domain.errors.PokerSaleV2Error

sealed class ResultOf<out T> {
    object Empty : ResultOf<Nothing>()
    data class Success<out R>(val response: R) : ResultOf<R>()
    data class Loading<out R>(val isShowDialog: Boolean) : ResultOf<Nothing>()
    data class Failure(
        val error: PokerSaleV2Error
    ) : ResultOf<Nothing>()
}
