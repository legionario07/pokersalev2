package br.com.khodahafez.domain.usecase.balance

import br.com.khodahafez.domain.model.Balance
import br.com.khodahafez.domain.repository.remote.BalanceRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class SaveBalanceUseCase(
    private val scope: CoroutineContext,
    private val repository: BalanceRepository,
) {

    fun save(balance: Balance): Flow<ResultOf<Balance>> {
        return repository.save(balance)
            .onStart {
                emit(ResultOf.Loading(true))
            }.flowOn(scope)
    }
}
