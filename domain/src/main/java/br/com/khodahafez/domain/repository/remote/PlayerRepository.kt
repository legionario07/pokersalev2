package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun save(
        player: Player
    ): Flow<ResultOf<Player>>

    fun update(
        player: Player
    ): Flow<ResultOf<Player>>

    fun get(
        login: String,
        password: String
    ): Flow<ResultOf<Player>>

    fun getAll(): Flow<ResultOf<List<Player>>>
}
