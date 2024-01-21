package br.com.khodahafez.domain.usecase.score

import br.com.khodahafez.domain.model.BountyType
import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.model.PositionScoreType
import br.com.khodahafez.domain.model.dto.ScoreDto
import br.com.khodahafez.domain.repository.remote.ScoreRepository
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class SaveScoreUseCase(
    private val scope: CoroutineContext,
    private val repository: ScoreRepository,
) {
    fun save(
        scoreDto: ScoreDto,
        matchOfPoker: MatchOfPoker?
    ): Flow<ResultOf<ScoreDto>> {

        scoreDto.bountiesPoints =
            BountyType.getBountyByMatchType(matchOfPoker?.isSpecialMatch == true).points * scoreDto.totalBounties
        scoreDto.difficultyScore = matchOfPoker?.players?.size ?: 0
        scoreDto.pointsForPosition =
            PositionScoreType.getPointsForPosition(scoreDto.positionInTheMatch).points

        return repository.save(scoreDto)
            .onStart {
                emit(ResultOf.Loading(true))
            }.flowOn(scope)
    }
}
