package br.com.khodahafez.data.mapper

import br.com.khodahafez.domain.mapper.PlayerMapper
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.model.database.PlayerDb
import br.com.khodahafez.domain.model.dto.PlayerDto

class PlayerMapperImpl : PlayerMapper {

    override fun toDomain(entity: PlayerDb): Player {
        return Player(
            id = entity.id,
            name = entity.name,
            lastAccess = entity.lastAccess,
            password = entity.password,
            login = entity.login,
            createdAt = entity.createdAt,
            isAdmin = entity.isAdmin,
        )
    }

    override fun toDomain(entity: PlayerDto): Player {
        return Player(
            id = entity.id,
            name = entity.name,
            lastAccess = entity.lastAccess,
            password = entity.password,
            login = entity.login,
            createdAt = entity.createdAt,
            isAdmin = entity.isAdmin,
        )
    }

    override fun toEntityDto(domain: Player): PlayerDto {
        return PlayerDto(
            id = domain.id,
            name = domain.name,
            lastAccess = domain.lastAccess,
            password = domain.password,
            login = domain.login,
            createdAt = domain.createdAt,
            isAdmin = domain.isAdmin,
        )
    }

    override fun toEntityDb(domain: Player): PlayerDb {
        return PlayerDb(
            id = domain.id.orEmpty(),
            name = domain.name,
            lastAccess = domain.lastAccess,
            password = domain.password,
            login = domain.login,
            createdAt = domain.createdAt,
            isAdmin = domain.isAdmin,
        )
    }
}
