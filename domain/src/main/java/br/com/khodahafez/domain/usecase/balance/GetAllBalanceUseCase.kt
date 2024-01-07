package br.com.khodahafez.domain.usecase.balance

import br.com.khodahafez.domain.model.Balance
import br.com.khodahafez.domain.repository.remote.BalanceRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class GetAllBalanceUseCase(
    private val scope: CoroutineContext,
    private val repository: BalanceRepository,
) {
    fun getAll(): Flow<List<Balance>> {
        return repository.getAll().map {
            when (it) {
                is ResultOf.Success -> {
                    it.response
                }

                is ResultOf.Failure -> {
                    throw it.error
                }

                else -> {
                    null!!
                }
            }
        }.flowOn(scope)
    }
}
