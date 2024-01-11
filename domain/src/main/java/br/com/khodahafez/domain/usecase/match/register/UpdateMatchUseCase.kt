package br.com.khodahafez.domain.usecase.match.register

import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.repository.remote.MatchRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class UpdateMatchUseCase(
    private val scope: CoroutineContext,
    private val repository: MatchRepository,
) {

    fun update(matchOfPoker: MatchOfPoker): Flow<ResultOf<MatchOfPoker>> {
        return repository.update(matchOfPoker)
            .onStart {
                emit(ResultOf.Loading(true))
            }
            .flowOn(scope)
    }
}
