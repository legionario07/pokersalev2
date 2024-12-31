package br.com.khodahafez.pokersale.ui.views.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING
import br.com.khodahafez.domain.PokerSaleConstants.ErrorMessage.GENERIC_ERROR
import br.com.khodahafez.domain.PokerSaleConstants.PreferencesKeys.REMEMBER_LOGIN_CHECKED
import br.com.khodahafez.domain.PokerSaleConstants.PreferencesKeys.REMEMBER_LOGIN_PLAYER
import br.com.khodahafez.domain.errors.NotFoundUserException
import br.com.khodahafez.domain.errors.PasswordUserException
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.usecase.login.LoginUseCase
import br.com.khodahafez.domain.usecase.player.SavePlayerUseCase
import br.com.khodahafez.domain.usecase.preferences.LoginPreferencesUseCase
import br.com.khodahafez.domain.utils.EncryptUtils
import br.com.khodahafez.domain.utils.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val loginPreferencesUseCase: LoginPreferencesUseCase
) : ViewModel() {

    private val _loginStateUI = MutableStateFlow<LoginStateUI>(LoginStateUI.InitialState)
    val loginStateUI: StateFlow<LoginStateUI> = _loginStateUI

    var isCheckedToRemember = false
    var login = EMPTY_STRING
    var password = EMPTY_STRING

    init {
        checkIfHasPlayerInPreferences()
    }

    private fun checkIfHasPlayerInPreferences() {

        if (isLoginCheckedToRemember() == true) {
            isCheckedToRemember = true
            val player = getPlayerInPreferences()


            login = player?.login.orEmpty()
            password = getPasswordDecrypt(player?.password.orEmpty())

            _loginStateUI.update {
                LoginStateUI.LoginByPreferencesState(player)
            }
        } else {
            isCheckedToRemember = false
        }
    }

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
                checkIfLoginIsCheckedToRemember(
                    isChecked = isCheckedToRemember,
                    player = player
                )
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

    private fun isLoginCheckedToRemember() =
        loginPreferencesUseCase.get(REMEMBER_LOGIN_CHECKED, Boolean::class.java)

    private fun checkIfLoginIsCheckedToRemember(isChecked: Boolean, player: Player) {
        loginPreferencesUseCase.save(REMEMBER_LOGIN_CHECKED, isChecked)
        if (isChecked) {
            loginPreferencesUseCase.save(REMEMBER_LOGIN_PLAYER, player)
        }
    }

    private fun getPlayerInPreferences() =
        loginPreferencesUseCase.get(REMEMBER_LOGIN_PLAYER, Player::class.java)

    private fun getPasswordDecrypt(password: String) =
        EncryptUtils.decrypt(password)
}

sealed class LoginStateUI {
    object InitialState : LoginStateUI()
    object Loading : LoginStateUI()
    data class Error(val message: String?) : LoginStateUI()
    data class LoginSuccessful(val player: Player) : LoginStateUI()
    data class LoginByPreferencesState(val player: Player?) : LoginStateUI()
}
