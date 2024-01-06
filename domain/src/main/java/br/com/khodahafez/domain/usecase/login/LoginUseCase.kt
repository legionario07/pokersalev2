package br.com.khodahafez.domain.usecase.login

import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.utils.EncryptUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class LoginUseCase(
    private val scope: CoroutineContext,
    private val playerRepository: PlayerRepository,
    private val encryptUtil: EncryptUtils
) {

    fun login(login: String, password: String): Flow<Player> {
        val passwordEncrypted = encryptUtil.encrypt(password)
        return playerRepository.get(login, passwordEncrypted).map {
            when(it) {
                is ResultOf.Success -> {
                    it.response
                }
                is ResultOf.Failure -> {
                    throw it.error
                }

                else -> {
                    null!!
                }
            }
        }.flowOn(scope)
    }
}
