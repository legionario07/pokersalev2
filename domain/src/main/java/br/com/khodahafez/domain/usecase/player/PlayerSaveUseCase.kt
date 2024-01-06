package br.com.khodahafez.domain.usecase.player

import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.utils.EncryptUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class PlayerSaveUseCase(
    private val scope: CoroutineContext,
    private val playerRepository: PlayerRepository,
    private val encryptUtil: EncryptUtils
) {

    fun save(player: Player): Flow<Player> {
        val passwordEncrypted = encryptUtil.encrypt(player.password)
        player.password = passwordEncrypted
        return playerRepository.save(player).map {
            when (it) {
                is ResultOf.Success -> {
                    it.response
                }

                else -> {
                    throw Exception()
                }
            }
        }.flowOn(scope)
    }
}
