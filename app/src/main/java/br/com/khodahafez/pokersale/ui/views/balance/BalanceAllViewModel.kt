package br.com.khodahafez.pokersale.ui.views.balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.PokerSaleConstants
import br.com.khodahafez.domain.model.Balance
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.usecase.balance.GetAllBalanceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BalanceAllViewModel(
    private val getAllBalanceUseCase: GetAllBalanceUseCase
) : ViewModel() {

    private val _stateUI =
        MutableStateFlow<BalancePokerStateUI>(BalancePokerStateUI.Initial)
    val stateUI: StateFlow<BalancePokerStateUI> = _stateUI

    init {
        getAllBalances()
    }

    private fun getAllBalances() {
        viewModelScope.launch {
            getAllBalanceUseCase.getAll()
                .catch {
                    _stateUI.update {
                        BalancePokerStateUI.Error(PokerSaleConstants.ErrorMessage.GENERIC_ERROR)
                    }
                }.collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            _stateUI.update {
                                BalancePokerStateUI.GetAllBalances(
                                    resultOf.response
                                )
                            }
                        }

                        is ResultOf.Failure -> {
                            _stateUI.update {
                                BalancePokerStateUI.Error(resultOf.error.message)
                            }
                        }

                        else -> {
                            _stateUI.update {
                                BalancePokerStateUI.Loading
                            }
                        }
                    }
                }
        }
    }
}


sealed class BalancePokerStateUI {
    object Initial : BalancePokerStateUI()
    object Loading : BalancePokerStateUI()
    data class Error(val message: String?) : BalancePokerStateUI()
    data class GetAllBalances(val listBalances: List<Balance>) : BalancePokerStateUI()
}