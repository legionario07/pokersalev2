package br.com.khodahafez.domain.usecase.balance

import br.com.khodahafez.domain.model.dto.BalanceDto
import br.com.khodahafez.domain.repository.remote.BalanceRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class SaveBalanceUseCase(
    private val scope: CoroutineContext,
    private val repository: BalanceRepository,
) {

    fun save(balanceDto: BalanceDto): Flow<ResultOf<BalanceDto>> {
        return repository.save(balanceDto)
            .onStart {
                emit(ResultOf.Loading(true))
            }.flowOn(scope)
    }
}
