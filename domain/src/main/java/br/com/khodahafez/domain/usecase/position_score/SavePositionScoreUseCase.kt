package br.com.khodahafez.domain.usecase.position_score

import br.com.khodahafez.domain.model.PositionScore
import br.com.khodahafez.domain.repository.remote.PositionScoreRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class SavePositionScoreUseCase(
    private val scope: CoroutineContext,
    private val positionScoreRepository: PositionScoreRepository,
) {
    fun save(positionScore: PositionScore): Flow<ResultOf<PositionScore>> {
        return positionScoreRepository.save(positionScore)
            .onStart {
                emit(ResultOf.Loading(true))
            }
            .flowOn(scope)
    }
}
