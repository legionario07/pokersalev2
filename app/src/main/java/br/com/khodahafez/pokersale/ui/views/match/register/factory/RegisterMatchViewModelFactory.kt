package br.com.khodahafez.pokersale.ui.views.match.register.factory

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.domain.utils.Session
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchViewModel

class RegisterMatchViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferences = FirebaseModule.provideFirebaseReference("/matches")
        val dbReferencesPlayers = FirebaseModule.provideFirebaseReference("/users")

        val repository = RepositoryModule.provideMatchOfPokerRepository(dbReferences)
        val repositoryPlayer = RepositoryModule.providePlayerRepository(dbReferencesPlayers)

        val saveUseCase = UseCaseModule.provideSaveMatchOfPokerUseCase(repository)

        val getAllPlayerUseCase = UseCaseModule.provideGetAllPlayerUseCase(repositoryPlayer)

        return RegisterMatchViewModel(
            playerLogged = Session.player,
            saveUseCase = saveUseCase,
            getAllPlayerUseCase = getAllPlayerUseCase,
        ) as T
    }
}
