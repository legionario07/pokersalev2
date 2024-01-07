package br.com.khodahafez.pokersale.ui.views.balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.usecase.balance.GetAllBalanceUseCase
import br.com.khodahafez.domain.usecase.balance.SaveBalanceUseCase
import kotlinx.coroutines.launch

class BalanceViewModel(
    private val saveUseCase: SaveBalanceUseCase,
    private val getAllUseCase: GetAllBalanceUseCase
) : ViewModel() {

    fun save() {

        viewModelScope.launch {
//            saveUseCase.save(
//                Balance()
//            ).catch {
//                println(it.message)
//            }.collect {
//                println(it)
//            }
        }
    }
}