@file:Suppress("UNCHECKED_CAST")

package br.com.khodahafez.pokersale.ui.views.login

import android.content.Context
import br.com.khodahafez.pokersale.di.FirebaseModule
import br.com.khodahafez.pokersale.di.MapperProvide
import br.com.khodahafez.pokersale.di.RepositoryModule
import br.com.khodahafez.pokersale.di.UseCaseModule
import br.com.khodahafez.pokersale.di.UseCaseModule.provideLoginPreferencesUseCase

object LoginViewModelFactory {

    private var viewModel: LoginViewModel? = null

    fun create(context: Context): LoginViewModel? {
        if (viewModel == null) {
            val dbReferences = FirebaseModule.provideFirebaseReference("/users")

            val playerRepository = RepositoryModule.providePlayerRepository(dbReferences)

            val mapper = MapperProvide.providePlayerMapper()

            val loginUseCase = UseCaseModule.provideLoginUseCase(
                playerRepository,
                mapper
            )

            viewModel = LoginViewModel(
                loginUseCase = loginUseCase,
                loginPreferencesUseCase = provideLoginPreferencesUseCase(
                    context = context
                )
            )
        }

        return viewModel
    }
}
