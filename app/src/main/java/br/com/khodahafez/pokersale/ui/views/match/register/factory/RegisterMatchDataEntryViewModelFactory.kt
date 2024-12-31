package br.com.khodahafez.pokersale.ui.views.match.register.factory

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.domain.utils.Session
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchDataEntryViewModel

class RegisterMatchDataEntryViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferences = FirebaseModule.provideFirebaseReference("/matches")

        val repository = RepositoryModule.provideMatchOfPokerRepository(dbReferences)

        val saveUseCase = UseCaseModule.provideSaveMatchOfPokerUseCase(repository)

        return RegisterMatchDataEntryViewModel(
            playerLogged = Session.player,
            saveUseCase = saveUseCase
        ) as T
    }
}
