package br.com.khodahafez.pokersale.ui.views.player

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.MapperProvide
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule

class PlayerViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferences = FirebaseModule.provideFirebaseReference("/users")

        val playerRepository = RepositoryModule.providePlayerRepository(dbReferences)
        val mapper = MapperProvide.providePlayerMapper()

        val playerUseCase = UseCaseModule.providePlayerSaveUseCase(
            playerRepository,
            mapper
        )

        return PlayerViewModel(
            savePlayerUseCase = playerUseCase
        ) as T
    }
}
