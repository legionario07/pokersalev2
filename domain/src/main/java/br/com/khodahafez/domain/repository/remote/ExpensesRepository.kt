package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.Balance
import br.com.khodahafez.domain.model.BankType
import br.com.khodahafez.domain.model.Expenses
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {
    fun save(
        expenses: Expenses
    ): Flow<ResultOf<Expenses>>

    fun getByIdPlayer(
        idPlayer: String,
    ): Flow<ResultOf<List<Expenses>>>

    fun getAll(): Flow<ResultOf<List<Expenses>>>
}