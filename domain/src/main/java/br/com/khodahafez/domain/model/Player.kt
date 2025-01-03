package br.com.khodahafez.domain.model

import android.os.Parcelable
import br.com.khodahafez.domain.PokerSaleConstants
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    var id: String? = null,
    val name: String = PokerSaleConstants.EMPTY_STRING,
    val lastAccess: String = PokerSaleConstants.EMPTY_STRING,
    val createdAt: String = PokerSaleConstants.EMPTY_STRING,
    var login: String = PokerSaleConstants.EMPTY_STRING,
    var password: String = PokerSaleConstants.EMPTY_STRING,
    var isAdmin: Boolean = false
): Parcelable
