package br.com.khodahafez.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Expenses(
    var id: String? = null,
    val idPlayer: String? = null,
    val totalEntries: Double? = null,
    val cashPrize: Double? = null,
    val idMatchOfPoker: String? = null
): Parcelable
