package br.com.khodahafez.domain.usecase.expenses

import br.com.khodahafez.domain.model.Expenses
import br.com.khodahafez.domain.repository.remote.ExpensesRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class GetAllExpensesUseCase(
    private val scope: CoroutineContext,
    private val repository: ExpensesRepository,
) {
    fun getAll(): Flow<ResultOf<List<Expenses>>> {
        return repository.getAll().flowOn(scope)
    }
}
