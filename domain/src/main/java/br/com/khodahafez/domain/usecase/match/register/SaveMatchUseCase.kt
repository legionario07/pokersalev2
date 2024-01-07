package br.com.khodahafez.domain.usecase.match.register

import br.com.khodahafez.domain.model.BountyType
import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.repository.remote.BountyTypeRepository
import br.com.khodahafez.domain.repository.remote.MatchRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class SaveMatchUseCase(
    private val scope: CoroutineContext,
    private val repository: MatchRepository,
) {

    fun save(matchOfPoker: MatchOfPoker): Flow<MatchOfPoker> {
        return repository.save(matchOfPoker).map {
            when (it) {
                is ResultOf.Success -> {
                    it.response
                }

                is ResultOf.Failure -> {
                    throw it.error
                }

                else -> {
                    null!!
                }
            }
        }.flowOn(scope)
    }
}
