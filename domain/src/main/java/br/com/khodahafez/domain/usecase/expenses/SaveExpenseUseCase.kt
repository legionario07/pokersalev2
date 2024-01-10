package br.com.khodahafez.domain.usecase.expenses

import br.com.khodahafez.domain.model.Expenses
import br.com.khodahafez.domain.model.MatchOfPokerType
import br.com.khodahafez.domain.repository.remote.ExpensesRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class SaveExpensesUseCase(
    private val scope: CoroutineContext,
    private val repository: ExpensesRepository,
) {
    fun save(
        expenses: Expenses,
    ): Flow<ResultOf<Expenses>> {
        return repository.save(expenses)
            .onStart {
                emit(ResultOf.Loading(true))
            }.flowOn(scope)
    }
}
