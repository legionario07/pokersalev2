@file:Suppress("UNCHECKED_CAST")

package br.com.khodahafez.pokersale.ui.views.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.MapperProvide
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule
import br.com.khodahafez.pokersale.di.UseCaseModule.provideLoginPreferencesUseCase

class LoginViewModelFactory(private val activity: Activity) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferences = FirebaseModule.provideFirebaseReference("/users")

        val playerRepository = RepositoryModule.providePlayerRepository(dbReferences)

        val mapper = MapperProvide.providePlayerMapper()

        val loginUseCase = UseCaseModule.provideLoginUseCase(
            playerRepository,
            mapper
        )

        return LoginViewModel(
            loginUseCase = loginUseCase,
            loginPreferencesUseCase = provideLoginPreferencesUseCase(
                activity = activity
            )
        ) as T
    }
}
