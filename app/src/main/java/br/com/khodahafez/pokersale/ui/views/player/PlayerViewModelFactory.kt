package br.com.khodahafez.pokersale.ui.views.player

import androidx.annotation.NonNull
import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.data.repository.firebase.FirebaseDatabaseConstants
import br.com.khodahafez.data.repository.firebase.PlayerRepositoryImpl
import br.com.khodahafez.data.repository.firebase.PokerSaleV2FirebaseDataSourceImpl
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.MapperProvide
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
            playerSaveUseCase = playerUseCase
        ) as T
    }
}
