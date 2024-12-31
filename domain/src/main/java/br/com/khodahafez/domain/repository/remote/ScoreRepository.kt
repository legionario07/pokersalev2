package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.dto.ScoreDto
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {
    fun save(
        scoreDto: ScoreDto
    ): Flow<ResultOf<ScoreDto>>

    fun getByIdPlayer(
        idPlayer: String,
    ): Flow<ResultOf<List<ScoreDto>>>

    fun getAll(): Flow<ResultOf<List<ScoreDto>>>

    fun getAllByRanking(matches: List<String>): Flow<ResultOf<List<ScoreDto>>>
}