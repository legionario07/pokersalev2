package br.com.khodahafez.domain.model

data class MatchOfPoker(
    val id: String? = null,
    val dateTime: Long? = null,
    val isSpecialMatch: Boolean,
    val locale: Locale? = null,
    val scores: Set<Score> = setOf(),
    val expenses: Set<Expenses> = setOf(),
)
