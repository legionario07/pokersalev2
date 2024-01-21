package br.com.khodahafez.domain.repository

import br.com.khodahafez.domain.model.Score
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface ScoreRepositoryDataSource {
    fun getAll(): Flow<ResultOf<List<Score>>>
}
