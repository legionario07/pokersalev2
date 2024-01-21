package br.com.khodahafez.domain.usecase.expenses

import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.model.dto.ExpensesDto
import br.com.khodahafez.domain.repository.remote.ExpensesRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class GetExpenseByPlayerUseCase(
    private val scope: CoroutineContext,
    private val repository: ExpensesRepository,
) {
    fun getExpensesByPlayer(
        player: Player?,
    ): Flow<ResultOf<List<ExpensesDto>>> {
        return repository.getByIdPlayer(idPlayer = player?.id.orEmpty())
            .onStart {
                emit(ResultOf.Loading(true))
            }.flowOn(scope)
    }
}
