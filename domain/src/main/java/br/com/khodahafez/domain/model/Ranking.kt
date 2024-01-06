package br.com.khodahafez.domain.model

data class Ranking(
    val id: String? = null,
    val rankingNumber: Int,
    val matches: Set<MatchOfPoker> = setOf()
)
