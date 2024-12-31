package br.com.khodahafez.domain.state

import br.com.khodahafez.domain.errors.PokerSaleV2Error

sealed class ResultOf<out T> {
    data class Success<out R>(val response: R) : ResultOf<R>()
    data class Loading(val isShowDialog: Boolean) : ResultOf<Nothing>()
    data class Failure(
        val error: Throwable
    ) : ResultOf<Nothing>()
}
