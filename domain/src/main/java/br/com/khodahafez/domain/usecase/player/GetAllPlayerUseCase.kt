package br.com.khodahafez.domain.usecase.player

import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.utils.EncryptUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class GetAllPlayerUseCase(
    private val scope: CoroutineContext,
    private val repository: PlayerRepository,
) {

    fun getAll(): Flow<ResultOf<List<Player>>> {
        return repository.getAll()
            .onStart {
                emit(ResultOf.Loading(true))
            }
            .flowOn(scope)
    }
}
