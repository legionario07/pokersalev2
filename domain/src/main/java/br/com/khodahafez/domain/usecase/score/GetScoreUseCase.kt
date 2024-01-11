package br.com.khodahafez.domain.usecase.score

import br.com.khodahafez.domain.model.Score
import br.com.khodahafez.domain.repository.remote.ScoreRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class GetScoreUseCase(
    private val scope: CoroutineContext,
    private val repository: ScoreRepository,
) {
    fun getAll(): Flow<ResultOf<List<Score>>> {

        return repository.getAll()
            .onStart {
                emit(ResultOf.Loading(true))
            }.flowOn(scope)
    }
}
