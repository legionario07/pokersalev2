package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.Balance
import br.com.khodahafez.domain.model.BankType
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
    fun save(
        balance: Balance
    ): Flow<ResultOf<Balance>>

    fun getByOperationType(
        operationType: BankType,
    ): Flow<ResultOf<List<Balance>>>

    fun getAll(): Flow<ResultOf<List<Balance>>>
}