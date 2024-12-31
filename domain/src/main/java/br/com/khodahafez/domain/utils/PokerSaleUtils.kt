package br.com.khodahafez.domain.utils

import java.text.DateFormat
import java.util.Calendar
import java.util.Date

object PokerSaleUtils {
    fun checkIfDateAllowed(dateString: String, expectedYear: Int): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = DateFormat.getInstance().parse(dateString) as Date

        val actualYear = calendar.get(Calendar.YEAR)

        return actualYear == expectedYear
    }
}
