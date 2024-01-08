package br.com.khodahafez.pokersale.ui.views.match.register.factory

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.pokersale.ui.views.match.register.RegisterMatchDataUserViewModel

class RegisterMatchDataUserViewModelFactory : ViewModelProvider.Factory {

    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return RegisterMatchDataUserViewModel() as T
    }
}
