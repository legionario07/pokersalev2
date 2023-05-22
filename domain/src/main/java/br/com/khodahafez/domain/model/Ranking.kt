package br.com.khodahafez.domain.model

data class Ranking(
    val id: Int,
    val rankingNumber: Int,
    val matches: Set<MatchOfPoker> = setOf()
)
