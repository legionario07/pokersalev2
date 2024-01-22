package br.com.khodahafez.pokersale.ui.views.login

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.MapperProvide
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule

class LoginViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferences = FirebaseModule.provideFirebaseReference("/users")

        val playerRepository = RepositoryModule.providePlayerRepository(dbReferences)

        val mapper = MapperProvide.providePlayerMapper()

        val loginUseCase = UseCaseModule.provideLoginUseCase(
            playerRepository,
            mapper
        )
        val playerUseCase = UseCaseModule.providePlayerSaveUseCase(
            playerRepository,
            mapper
        )

        return LoginViewModel(
            loginUseCase = loginUseCase,
            savePlayerUseCase = playerUseCase
        ) as T
    }
}
