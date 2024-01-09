package br.com.khodahafez.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Expenses(
    val id: String? = null,
    val idPlayer: String? = null,
    val totalEntries: Double? = null,
    val cashPrize: Double? = null,
    val idMatchOfPoker: MatchOfPoker? = null
): Parcelable
