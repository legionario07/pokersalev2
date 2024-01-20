package br.com.khodahafez.pokersale.ui.views.balance.ranking_balance

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule

class RankingBalanceViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val dbReferences = FirebaseModule.provideFirebaseReference("/expenses")
        val dbReferencesPlayers = FirebaseModule.provideFirebaseReference("/users")

        val repository = RepositoryModule.provideExpensesRepository(dbReferences)
        val playerRepository = RepositoryModule.providePlayerRepository(dbReferencesPlayers)

        val useCase =
            UseCaseModule.provideGetAllExpensesUseCase(repository)
        val getPlayerUseCase = UseCaseModule.provideGetAllPlayerUseCase(playerRepository)

        return RankingBalanceViewModel(
            getAllExpensesUseCase = useCase,
            getAllPlayerUseCase = getPlayerUseCase
        ) as T
    }
}
