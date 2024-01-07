package br.com.khodahafez.domain.usecase.bounty_type

import br.com.khodahafez.domain.model.BountyType
import br.com.khodahafez.domain.repository.remote.BountyTypeRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class SaveBountyTypeUseCase(
    private val scope: CoroutineContext,
    private val repository: BountyTypeRepository,
) {

    fun save(bountyType: BountyType): Flow<BountyType> {
        return repository.save(bountyType).map {
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
