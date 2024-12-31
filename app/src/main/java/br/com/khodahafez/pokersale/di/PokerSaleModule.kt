package br.com.khodahafez.pokersale.di

import br.com.khodahafez.data.mapper.PlayerMapperImpl
import br.com.khodahafez.data.mapper.ScoreMapperImpl
import br.com.khodahafez.data.repository.firebase.FirebaseDatabaseConstants.REFERENCIES.DATABASE_POKER_SALE
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseModule {
    fun provideFirebaseReference(
        key: String
    ): DatabaseReference {
        val firebase = FirebaseDatabase.getInstance()
        return firebase.getReference(DATABASE_POKER_SALE.plus(key))
    }
}

object MapperProvide {
    fun providePlayerMapper() = PlayerMapperImpl()
    fun provideScoreMapper() = ScoreMapperImpl()
}
