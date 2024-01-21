package br.com.khodahafez.domain.usecase.player

import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.repository.PlayerRepositoryDataSource
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class GetAllPlayerUseCase(
    private val scope: CoroutineContext,
    private val repositoryDataSource: PlayerRepositoryDataSource
) {

    fun getAll(): Flow<ResultOf<List<Player>>> {
        return repositoryDataSource.getAll()
            .onStart {
                emit(ResultOf.Loading(true))
            }
            .flowOn(scope)
    }
}
