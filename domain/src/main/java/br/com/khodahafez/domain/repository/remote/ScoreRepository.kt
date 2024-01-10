package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.Score
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {
    fun save(
        score: Score
    ): Flow<ResultOf<Score>>

    fun getByIdPlayer(
        idPlayer: String,
    ): Flow<ResultOf<List<Score>>>

    fun getAll(): Flow<ResultOf<List<Score>>>
}