package br.com.khodahafez.pokersale.ui.views.bounty_type

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khodahafez.domain.model.BountyType
import br.com.khodahafez.domain.usecase.bounty_type.SaveBountyTypeUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BountyTypeViewModel(
    private val saveUseCase: SaveBountyTypeUseCase
) : ViewModel() {

    fun save(
        type: String,
        points: Int
    ) {

        viewModelScope.launch {
            saveUseCase.save(
                BountyType(
                    type = type,
                    points = points
                )
            ).catch {
                println(it.message)
            }.collect {
                println(it)
            }
        }
    }
}
