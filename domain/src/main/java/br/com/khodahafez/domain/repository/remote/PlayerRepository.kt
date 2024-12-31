package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.dto.PlayerDto
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun save(
        playerDto: PlayerDto
    ): Flow<ResultOf<PlayerDto>>

    fun update(
        playerDto: PlayerDto
    ): Flow<ResultOf<PlayerDto>>

    fun get(
        login: String,
        password: String
    ): Flow<ResultOf<PlayerDto>>

    fun getAll(): Flow<ResultOf<List<PlayerDto>>>
}
