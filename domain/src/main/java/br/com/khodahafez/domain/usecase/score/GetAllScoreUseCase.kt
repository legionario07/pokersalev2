package br.com.khodahafez.domain.usecase.score

import br.com.khodahafez.domain.model.Score
import br.com.khodahafez.domain.repository.ScoreRepositoryDataSource
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class GetAllScoreUseCase(
    private val scope: CoroutineContext,
    private val repositoryDataSource: ScoreRepositoryDataSource,
) {
    fun getAll(): Flow<ResultOf<List<Score>>> {
        return repositoryDataSource.getAll()
            .onStart {
                emit(ResultOf.Loading(true))
            }.flowOn(scope)
    }

    fun getAllByRanking(matches: List<String>): Flow<ResultOf<List<Score>>> {
        return repositoryDataSource.getAllByRanking(matches)
            .onStart {
                emit(ResultOf.Loading(true))
            }.flowOn(scope)
    }
}
