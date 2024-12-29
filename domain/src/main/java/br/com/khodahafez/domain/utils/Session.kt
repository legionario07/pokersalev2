package br.com.khodahafez.domain.utils

import br.com.khodahafez.domain.model.Player

object Session {
    var player: Player? = null
    var shouldGetPlayersInRemoteDatabase = true
    var shouldGetScoreInRemoteDatabase = true
    var yearSelected = 0
}
