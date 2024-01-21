package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.dto.ExpensesDto
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {
    fun save(
        expensesDto: ExpensesDto
    ): Flow<ResultOf<ExpensesDto>>

    fun getByIdPlayer(
        idPlayer: String,
    ): Flow<ResultOf<List<ExpensesDto>>>

    fun getAll(): Flow<ResultOf<List<ExpensesDto>>>
}