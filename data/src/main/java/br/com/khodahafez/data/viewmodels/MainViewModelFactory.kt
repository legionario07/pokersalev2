package br.com.khodahafez.data.viewmodels

import androidx.annotation.NonNull
import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.khodahafez.data.repository.firebase.FirebaseDatabaseConstants
import br.com.khodahafez.data.repository.firebase.PokerSaleV2FirebaseDataSourceImpl
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@RestrictTo(RestrictTo.Scope.LIBRARY)
class MainViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    @NonNull
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbReferences = Firebase.database.getReference(FirebaseDatabaseConstants.REFERENCIES.DATABASE_LOCALE)

        val pokerSaleV2DataSource = PokerSaleV2FirebaseDataSourceImpl(dbReferences)

        return MainActivityViewModel(
            pokerSaleV2DataSource
        ) as T
    }
}
