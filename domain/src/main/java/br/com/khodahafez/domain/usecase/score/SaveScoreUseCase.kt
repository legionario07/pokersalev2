package br.com.khodahafez.domain.usecase.score

import br.com.khodahafez.domain.model.BountyType
import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.model.PositionScoreType
import br.com.khodahafez.domain.model.Score
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
        score: Score,
        matchOfPoker: MatchOfPoker?
    ): Flow<ResultOf<Score>> {

        score.bountiesPoints =
            BountyType.getBountyByMatchType(matchOfPoker?.isSpecialMatch == true).points * score.totalBounties
        score.difficultyScore = matchOfPoker?.players?.size ?: 0
        score.pointsForPosition =
            PositionScoreType.getPointsForPosition(score.positionInTheMatch).points

        return repository.save(score)
            .onStart {
                emit(ResultOf.Loading(true))
            }.flowOn(scope)
    }
}
