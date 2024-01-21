package br.com.khodahafez.domain.mapper

import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.model.database.PlayerDb
import br.com.khodahafez.domain.model.dto.PlayerDto

interface PlayerMapper {
    fun toDomain(playerDb: PlayerDb): Player
    fun toDomain(playerDto: PlayerDto): Player
    fun toEntityDto(player: Player): PlayerDto
    fun toEntityDb(player: Player): PlayerDb
}
