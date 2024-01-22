package br.com.khodahafez.pokersale.ui.views.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.PokerSaleConstants.ErrorMessage.GENERIC_ERROR
import br.com.khodahafez.domain.errors.NotFoundUserException
import br.com.khodahafez.domain.errors.PasswordUserException
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.usecase.login.LoginUseCase
import br.com.khodahafez.domain.usecase.player.SavePlayerUseCase
import br.com.khodahafez.domain.utils.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val savePlayerUseCase: SavePlayerUseCase
) : ViewModel() {

    private val _loginStateUI = MutableStateFlow<LoginStateUI>(LoginStateUI.InitialState)
    val loginStateUI: StateFlow<LoginStateUI> = _loginStateUI

    fun login(
        login: String,
        password: String
    ) {
        _loginStateUI.update {
            LoginStateUI.Loading
        }
        viewModelScope.launch {
            loginUseCase.login(
                login = login.trim(),
                password = password
            ).catch { error ->
                _loginStateUI.update {
                    when (error) {
                        is PasswordUserException,
                        is NotFoundUserException -> {
                            LoginStateUI.Error(error.message)
                        }

                        else -> {
                            LoginStateUI.Error(GENERIC_ERROR)
                        }
                    }
                }
            }.collect { player ->
                Session.player = player
                _loginStateUI.update {
                    LoginStateUI.LoginSuccessful(player)
                }
            }
        }
    }

    fun clearStateUI() {
        _loginStateUI.update {
            LoginStateUI.InitialState
        }
    }
}

sealed class LoginStateUI {
    object InitialState : LoginStateUI()
    object Loading : LoginStateUI()
    data class Error(val message: String?) : LoginStateUI()
    data class LoginSuccessful(val player: Player) : LoginStateUI()
}
