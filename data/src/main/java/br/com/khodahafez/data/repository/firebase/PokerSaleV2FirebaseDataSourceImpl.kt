package br.com.khodahafez.data.repository.firebase

import br.com.khodahafez.domain.errors.PokerSaleV2Error
import br.com.khodahafez.domain.model.Locale
import br.com.khodahafez.domain.repository.remote.PokerSaleV2DataSource
import br.com.khodahafez.domain.state.ResultOf
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class PokerSaleV2FirebaseDataSourceImpl(
    private val dbReference: DatabaseReference
) : PokerSaleV2DataSource {

    override fun addLocale(scopeIo: CoroutineContext, locale: Locale): Flow<ResultOf<Locale>> {
        return flow {
            kotlin.runCatching {

                locale.id = dbReference.push().key

                dbReference.child(locale.id!!).setValue(locale)
                emit(ResultOf.Success(locale))
            }.onFailure {
                emit(ResultOf.Failure(PokerSaleV2Error.GenericError(it)))
            }
        }.flowOn(scopeIo)
    }

    override fun updateLocale(scopeIo: CoroutineContext, locale: Locale): Flow<ResultOf<Locale>> {
        return flow {
            kotlin.runCatching {
                dbReference.child(locale.id!!).setValue(locale)
                emit(ResultOf.Success(locale))
            }.onFailure {
                emit(ResultOf.Failure(PokerSaleV2Error.GenericError(it)))
            }
        }.flowOn(scopeIo)
    }
}
