package br.com.khodahafez.pokersale.di

import br.com.khodahafez.data.repository.PlayerRepositoryDataSourceImpl
import br.com.khodahafez.domain.mapper.PlayerMapper
import br.com.khodahafez.domain.repository.PlayerRepositoryDataSource
import br.com.khodahafez.domain.repository.local.PlayerDao
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.utils.Session

object RepositoryDataSourceProvide {
    fun providePlayerRepositoryDataSource(
        mapper: PlayerMapper,
        playerRepository: PlayerRepository,
        playerDao: PlayerDao,
        session: Session
    ): PlayerRepositoryDataSource {
        return PlayerRepositoryDataSourceImpl(
            playerRepository = playerRepository,
            playerDao = playerDao,
            session = Session,
            mapper = mapper
        )
    }
}
