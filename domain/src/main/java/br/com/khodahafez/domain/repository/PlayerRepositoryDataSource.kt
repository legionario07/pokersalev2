package br.com.khodahafez.domain.repository

import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface PlayerRepositoryDataSource {
    fun getAll(): Flow<ResultOf<List<Player>>>
}
