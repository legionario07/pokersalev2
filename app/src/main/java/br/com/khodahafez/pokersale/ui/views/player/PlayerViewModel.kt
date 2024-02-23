package br.com.khodahafez.pokersale.ui.views.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.extensions.converterToStringDate
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.usecase.player.SavePlayerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class PlayerViewModel(
    private val savePlayerUseCase: SavePlayerUseCase
) : ViewModel() {

    private val _playerStateUI = MutableStateFlow<PlayerStateUI>(PlayerStateUI.InitialState)
    val playerStateUI: StateFlow<PlayerStateUI> = _playerStateUI

    fun save(
        name: String,
        login: String,
        password: String,
        isAdmin: Boolean = false
    ) {
        _playerStateUI.update {
            PlayerStateUI.Loading
        }
        viewModelScope.launch {
            savePlayerUseCase.save(
                Player(
                    name = name,
                    login = login,
                    password = password,
                    isAdmin = isAdmin,
                    createdAt = Calendar.getInstance().timeInMillis.converterToStringDate(),
                    lastAccess = Calendar.getInstance().timeInMillis.converterToStringDate()
                )
            )
                .catch { error ->
                    _playerStateUI.update {
                        PlayerStateUI.Error(error.message)
                    }
                }.collect { _ ->
                    _playerStateUI.update { PlayerStateUI.SaveSuccessful }
                }
        }
    }

    fun clearState() {
        _playerStateUI.update {
            PlayerStateUI.InitialState
        }
    }
}

sealed class PlayerStateUI {
    object InitialState : PlayerStateUI()
    object Loading : PlayerStateUI()
    data class Error(val message: String?) : PlayerStateUI()
    object SaveSuccessful : PlayerStateUI()
}
