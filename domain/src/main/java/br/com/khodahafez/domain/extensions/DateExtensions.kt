package br.com.khodahafez.domain.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.converterToStringDate(): String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    return simpleDateFormat.format(Date(this))
}
