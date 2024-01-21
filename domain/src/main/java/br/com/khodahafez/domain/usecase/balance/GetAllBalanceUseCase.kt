package br.com.khodahafez.domain.usecase.balance

import br.com.khodahafez.domain.model.Balance
import br.com.khodahafez.domain.repository.remote.BalanceRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class GetAllBalanceUseCase(
    private val scope: CoroutineContext,
    private val repository: BalanceRepository,
) {
    fun getAll(): Flow<ResultOf<List<Balance>>> {
        return repository.getAll()
            .onStart {
                emit(ResultOf.Loading(true))
            }
            .flowOn(scope)
    }
}
