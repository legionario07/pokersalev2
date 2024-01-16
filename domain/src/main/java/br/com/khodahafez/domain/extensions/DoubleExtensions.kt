package br.com.khodahafez.domain.extensions

import java.text.NumberFormat
import java.util.Locale

fun Double?.toMonetary(): String {
    val ptBr = Locale("pt", "BR")
    return NumberFormat.getCurrencyInstance(ptBr).format(this)
}
