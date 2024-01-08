package br.com.khodahafez.domain.model

import android.os.Parcelable
import br.com.khodahafez.domain.PokerSaleConstants.EMPTY_STRING
import br.com.khodahafez.domain.PokerSaleConstants.MatchValues.ADDON
import br.com.khodahafez.domain.PokerSaleConstants.MatchValues.BUY_IN
import br.com.khodahafez.domain.PokerSaleConstants.MatchValues.DOUBLE_RE_BUY
import br.com.khodahafez.domain.PokerSaleConstants.MatchValues.RE_BUY
import br.com.khodahafez.domain.PokerSaleConstants.MatchValues.TAX
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchOfPoker(
    var id: String? = null,
    val date: String? = EMPTY_STRING,
    val isSpecialMatch: Boolean = false,
    val players: List<Player> = mutableListOf(),
    val ranking: Int = 1,
    val matchOfPokerType: MatchOfPokerType = MatchOfPokerType.IN_PROGRESS,
    var registeredBy: Player? = null,
    var buyInValue: Double = BUY_IN.toDouble(),
    var reBuyValue: Double = RE_BUY.toDouble(),
    var doubleReBuyValue: Double = DOUBLE_RE_BUY.toDouble(),
    var addonValue: Double = ADDON.toDouble(),
    var taxValue: Double = TAX.toDouble()
) : Parcelable
