package br.com.khodahafez.domain.usecase.match.register

import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.repository.remote.MatchRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class GetMatchUseCase(
    private val scope: CoroutineContext,
    private val repository: MatchRepository,
) {

    fun getById(id: String): Flow<ResultOf<MatchOfPoker>> {
        return repository.getById(id)
            .onStart {
                emit(ResultOf.Loading(true))
            }
            .flowOn(scope)
    }

    fun getByRanking(rankingNumber: Int): Flow<ResultOf<List<MatchOfPoker>>> {
        return repository.getByRankingNumber(rankingNumber)
            .onStart {
                emit(ResultOf.Loading(true))
            }
            .flowOn(scope)
    }
}