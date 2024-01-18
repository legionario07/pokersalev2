package br.com.khodahafez.pokersale.ui.views.balance.my_balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.PokerSaleConstants
import br.com.khodahafez.domain.model.Expenses
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.usecase.expenses.GetExpenseByPlayerUseCase
import br.com.khodahafez.domain.model.screen.ExpenseUiHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyBalanceViewModel(
    private val useCase: GetExpenseByPlayerUseCase,
    private val playerLogged: Player?
) : ViewModel() {


    private val _stateUI =
        MutableStateFlow<MyBalanceStateUI>(MyBalanceStateUI.Initial)
    val stateUI: StateFlow<MyBalanceStateUI> = _stateUI

    init {
        getExpensesByPlayer()
    }

    private fun getExpensesByPlayer() {
        viewModelScope.launch {
            useCase.getExpensesByPlayer(playerLogged)
                .catch {
                    _stateUI.update {
                        MyBalanceStateUI.Error(PokerSaleConstants.ErrorMessage.GENERIC_ERROR)
                    }
                }.collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            _stateUI.update {
                                MyBalanceStateUI.GetSuccessfulBalanceStateUI(
                                    getExpenseUi(resultOf.response)
                                )
                            }
                        }

                        is ResultOf.Failure -> {
                            _stateUI.update {
                                MyBalanceStateUI.Error(resultOf.error.message)
                            }
                        }

                        else -> {
                            _stateUI.update {
                                MyBalanceStateUI.Loading
                            }
                        }
                    }
                }
        }
    }

    private fun getExpenseUi(list: List<Expenses>): ExpenseUiHelper {
        var totalPrize = 0.0
        var totalEntries = 0.0
        list.forEach {
            totalPrize += it.cashPrize ?: 0.0
            totalEntries += it.totalEntries ?: 0.0
        }

        return ExpenseUiHelper(
            player = playerLogged,
            totalEntries = totalEntries,
            totalPrizes = totalPrize
        )

    }
}

sealed class MyBalanceStateUI {
    object Initial : MyBalanceStateUI()
    object Loading : MyBalanceStateUI()
    data class Error(val message: String?) : MyBalanceStateUI()
    data class GetSuccessfulBalanceStateUI(val expenseUiHelper: ExpenseUiHelper) :
        MyBalanceStateUI()
}
