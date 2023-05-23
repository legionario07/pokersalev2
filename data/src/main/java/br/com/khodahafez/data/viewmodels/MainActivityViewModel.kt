package br.com.khodahafez.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.model.Locale
import br.com.khodahafez.domain.repository.remote.PokerSaleV2DataSource
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val firebaseDataSource: PokerSaleV2DataSource
) : ViewModel() {

    fun saveLocale(locale: Locale) {
        viewModelScope.launch {
            firebaseDataSource.addLocale(
                Dispatchers.IO,
                locale
            ).catch {
                println(this)
            }.collect {
                when (it) {
                    is ResultOf.Success -> {
                        println(it.response)
                    }

                    else -> {
                        println(it)
                    }
                }
            }
        }
    }
}