package br.com.khodahafez.domain.usecase.player

import br.com.khodahafez.domain.mapper.PlayerMapper
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.utils.EncryptUtils
import br.com.khodahafez.domain.utils.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

class SavePlayerUseCase(
    private val scope: CoroutineContext,
    private val playerRepository: PlayerRepository,
    private val mapper: PlayerMapper,
    private val encryptUtil: EncryptUtils,
    private val session: Session
) {

    fun save(player: Player): Flow<ResultOf<Player>> {
        val passwordEncrypted = encryptUtil.encrypt(player.password)
        player.password = passwordEncrypted
        return playerRepository.save(
            mapper.toEntityDto(player)
        ).map { resultOf ->
            when (resultOf) {
                is ResultOf.Success -> {
                    updateSessionForGetNextPlayersInRemote()
                    ResultOf.Success(mapper.toDomain(resultOf.response))
                }

                is ResultOf.Failure -> {
                    throw resultOf.error
                }

                is ResultOf.Loading -> {
                    resultOf
                }
            }
        }
            .onStart { emit(ResultOf.Loading(true)) }
            .flowOn(scope)
    }

    private fun updateSessionForGetNextPlayersInRemote() {
        session.shouldGetPlayersInRemoteDatabase = true
    }
}
