package br.com.khodahafez.pokersale

import android.app.Application
import br.com.khodahafez.pokersale.di.AppDatabase
import br.com.khodahafez.pokersale.di.provideDataBase

class PokerSaleApplication: Application() {

    companion object {
        var database: AppDatabase? = null
    }
    override fun onCreate() {
        super.onCreate()

        // ROOM
        database = provideDataBase(this)
    }
}