package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.PositionScore
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface PositionScoreRepository {

    fun save(
        positionScore: PositionScore
    ): Flow<ResultOf<PositionScore>>

    fun update(
        positionScore: PositionScore
    ): Flow<ResultOf<PositionScore>>

    fun getById(
        id: String,
    ): Flow<ResultOf<PositionScore>>

    fun getAll(): Flow<ResultOf<List<PositionScore>>>
}
