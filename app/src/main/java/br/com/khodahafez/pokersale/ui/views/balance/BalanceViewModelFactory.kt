package br.com.khodahafez.pokersale.ui.views.balance

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule

class BalanceViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferences = FirebaseModule.provideFirebaseReference("/balances")

        val repository = RepositoryModule.provideBalanceRepository(dbReferences)

        val saveUseCase =
            UseCaseModule.provideSaveBalanceUseCase(repository)

        val getAllUseCase =
            UseCaseModule.provideGetAllBalanceUseCase(repository)

        return BalanceViewModel(
            saveUseCase = saveUseCase,
            getAllUseCase = getAllUseCase
        ) as T
    }
}
