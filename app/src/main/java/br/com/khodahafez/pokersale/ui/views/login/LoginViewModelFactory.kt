package br.com.khodahafez.pokersale.ui.views.login

import androidx.annotation.NonNull
import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.data.repository.firebase.FirebaseDatabaseConstants
import br.com.khodahafez.data.repository.firebase.PlayerRepositoryImpl
import br.com.khodahafez.data.repository.firebase.PokerSaleV2FirebaseDataSourceImpl
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
class LoginViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val firebase = FirebaseDatabase.getInstance()
        val dbReferences = firebase.getReference(FirebaseDatabaseConstants.REFERENCIES.DATABASE_POKER_SALE.plus("/users"))

        val playerRepository = RepositoryModule.providePlayerRepository(dbReferences)

        val loginUseCase = UseCaseModule.provideLoginUseCase(playerRepository)
        val playerUseCase = UseCaseModule.providePlayerSaveUseCase(playerRepository)

        return LoginViewModel(
            loginUseCase = loginUseCase,
            playerSaveUseCase = playerUseCase
        ) as T
    }
}
