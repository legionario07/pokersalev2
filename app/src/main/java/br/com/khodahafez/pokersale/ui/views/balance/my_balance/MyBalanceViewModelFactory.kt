package br.com.khodahafez.pokersale.ui.views.balance.my_balance

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.domain.utils.Session
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule

class MyBalanceViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferences = FirebaseModule.provideFirebaseReference("/expenses")

        val repository = RepositoryModule.provideExpensesRepository(dbReferences)

        val useCase =
            UseCaseModule.provideGetExpensesByPlayerUseCase(repository)

        return MyBalanceViewModel(
            useCase = useCase,
            playerLogged = Session.player
        ) as T
    }
}
