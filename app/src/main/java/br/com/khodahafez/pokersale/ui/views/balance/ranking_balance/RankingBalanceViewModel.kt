package br.com.khodahafez.pokersale.ui.views.balance.ranking_balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.data.mapper.ExpensesMapper
import br.com.khodahafez.domain.mapper.PokerSaleMapper
import br.com.khodahafez.domain.model.Expenses
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.usecase.expenses.GetAllExpensesUseCase
import br.com.khodahafez.domain.model.screen.ExpenseUiHelper
import br.com.khodahafez.domain.model.screen.RankingBalanceUiHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RankingBalanceViewModel(
    private val getAllExpensesUseCase: GetAllExpensesUseCase,
    private val mapper: PokerSaleMapper<Expenses, ExpenseUiHelper>
) : ViewModel() {

    private val _stateUI =
        MutableStateFlow<RankingBalanceStateUI>(RankingBalanceStateUI.Initial)
    val stateUI: StateFlow<RankingBalanceStateUI> = _stateUI

    init {
        getAllExpenses()
    }

    private fun getAllExpenses() {
        viewModelScope.launch {
            getAllExpensesUseCase.getAll()
                .catch { error ->
                    _stateUI.update {
                        RankingBalanceStateUI.Error(error.message)
                    }
                }.collect { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            // TODO implementar
                            _stateUI.update {
                                RankingBalanceStateUI.GetAllBalances(mutableListOf())
                            }
                        }

                        is ResultOf.Failure -> {
                            _stateUI.update {
                                RankingBalanceStateUI.Error(resultOf.error.message)
                            }
                        }

                        else -> {
                            _stateUI.update {
                                RankingBalanceStateUI.Loading
                            }
                        }
                    }
                }
        }
    }

    fun clearState() {
        _stateUI.update {
            RankingBalanceStateUI.Initial
        }
    }
}

sealed class RankingBalanceStateUI {
    object Initial : RankingBalanceStateUI()
    object Loading : RankingBalanceStateUI()
    data class Error(val message: String?) : RankingBalanceStateUI()
    data class GetAllBalances(val expenses: List<RankingBalanceUiHelper>) : RankingBalanceStateUI()
}
