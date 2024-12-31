package br.com.khodahafez.pokersale.ui.views.bounty_type

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule

class BountyTypeViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferences = FirebaseModule.provideFirebaseReference("/bounties_types")

        val repository = RepositoryModule.provideBountyTypeRepository(dbReferences)

        val saveUseCase =
            UseCaseModule.provideSaveBountyTypeUseCase(repository)

        return BountyTypeViewModel(
            saveUseCase = saveUseCase,
        ) as T
    }
}
