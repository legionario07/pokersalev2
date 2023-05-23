package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.Locale
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface PokerSaleV2DataSource {
    fun addLocale(
        scopeIo: CoroutineContext,
        locale: Locale
    ): Flow<ResultOf<Locale>>

    fun updateLocale(
        scopeIo: CoroutineContext,
        locale: Locale
    ): Flow<ResultOf<Locale>>
}
