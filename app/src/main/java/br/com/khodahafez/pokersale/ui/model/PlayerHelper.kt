package br.com.khodahafez.pokersale.ui.model

import br.com.khodahafez.domain.model.Player

data class PlayerHelper(
    val player: Player,
    val pointsTotal: Int,
    val bounties: Int
)
