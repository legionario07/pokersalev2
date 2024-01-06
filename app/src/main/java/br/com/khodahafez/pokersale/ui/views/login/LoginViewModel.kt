package br.com.khodahafez.pokersale.ui.views.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.PokerSaleConstants.ErrorMessage.GENERIC_ERROR
import br.com.khodahafez.domain.errors.NotFoundUserException
import br.com.khodahafez.domain.errors.PasswordUserException
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.usecase.login.LoginUseCase
import br.com.khodahafez.domain.usecase.player.PlayerSaveUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val playerSaveUseCase: PlayerSaveUseCase
) : ViewModel() {

    private val _loginStateUI = MutableStateFlow<LoginStateUI>(LoginStateUI.InitialState)
    val loginStateUI: StateFlow<LoginStateUI> = _loginStateUI

    fun login(
        login: String,
        password: String
    ) {
        viewModelScope.launch {
            loginUseCase.login(
                login = login,
                password = password
            ).catch {
                _loginStateUI.emit(
                    when (it) {
                        is PasswordUserException,
                        is NotFoundUserException -> {
                            LoginStateUI.Error(it.message)
                        }

                        else -> {
                            LoginStateUI.Error(GENERIC_ERROR)
                        }
                    }
                )
            }.collect {
               _loginStateUI.emit(LoginStateUI.LoginSuccessful(it))
            }
        }
    }

    fun saveForTesting() {
        val player = Player(
            name = "Paulinho",
            login = "paulinho",
            password = "pokersale123"
        )

        viewModelScope.launch {
            playerSaveUseCase.save(player)
                .catch {
                    println(it.message)
                }
                .collect {
                    println(it)
                }
        }
    }
}

sealed class LoginStateUI {
    object InitialState : LoginStateUI()
    data class Error(val message: String?) : LoginStateUI()
    data class LoginSuccessful(val player: Player) : LoginStateUI()
}
