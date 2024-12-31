package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.dto.BalanceDto
import br.com.khodahafez.domain.model.BankType
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
    fun save(
        balanceDto: BalanceDto
    ): Flow<ResultOf<BalanceDto>>

    fun getByOperationType(
        operationType: BankType,
    ): Flow<ResultOf<List<BalanceDto>>>

    fun getAll(): Flow<ResultOf<List<BalanceDto>>>
}