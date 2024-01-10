package br.com.khodahafez.pokersale.ui.views.balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.PokerSaleConstants
import br.com.khodahafez.domain.extensions.converterToStringDate
import br.com.khodahafez.domain.model.Balance
import br.com.khodahafez.domain.model.BankType
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.usecase.balance.SaveBalanceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class BalanceViewModel(
    private val saveUseCase: SaveBalanceUseCase,
    private val playerLogged: Player?
) : ViewModel() {

    private val _stateUI =
        MutableStateFlow<BalanceStateUI>(BalanceStateUI.Initial)
    val stateUI: StateFlow<BalanceStateUI> = _stateUI

    fun save(
        bankType: BankType,
        value: String,
        reason: String
    ) {
        viewModelScope.launch {
            saveUseCase.save(
                Balance(
                    namePlayer = playerLogged?.name.orEmpty(),
                    operationType = bankType,
                    value = value.toDouble(),
                    reason = reason,
                    date = Calendar.getInstance().timeInMillis.converterToStringDate()
                )
            ).catch {
                _stateUI.update {
                    BalanceStateUI.Error(PokerSaleConstants.ErrorMessage.GENERIC_ERROR)
                }
            }.collect { resultOf ->
                when (resultOf) {
                    is ResultOf.Success -> {
                        _stateUI.update {
                            BalanceStateUI.SaveSuccessfulBalanceStateUI
                        }
                    }

                    is ResultOf.Failure -> {
                        _stateUI.update {
                            BalanceStateUI.Error(resultOf.error.message)
                        }
                    }

                    else -> {
                        _stateUI.update {
                            BalanceStateUI.Loading
                        }
                    }
                }
            }
        }
    }

    fun clearState() {
        _stateUI.update {
            BalanceStateUI.Initial
        }
    }
}

sealed class BalanceStateUI {
    object Initial : BalanceStateUI()
    object Loading : BalanceStateUI()
    data class Error(val message: String?) : BalanceStateUI()
    object SaveSuccessfulBalanceStateUI : BalanceStateUI()
}
