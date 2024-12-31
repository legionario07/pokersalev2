package br.com.khodahafez.pokersale.di

import br.com.khodahafez.data.repository.PlayerRepositoryDataSourceImpl
import br.com.khodahafez.data.repository.ScoreRepositoryDataSourceImpl
import br.com.khodahafez.domain.mapper.PlayerMapper
import br.com.khodahafez.domain.mapper.ScoreMapper
import br.com.khodahafez.domain.repository.PlayerRepositoryDataSource
import br.com.khodahafez.domain.repository.ScoreRepositoryDataSource
import br.com.khodahafez.domain.repository.local.PlayerDao
import br.com.khodahafez.domain.repository.local.ScoreDao
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.repository.remote.ScoreRepository
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
            session = session,
            mapper = mapper
        )
    }

    fun provideScoreRepositoryDataSource(
        mapper: ScoreMapper,
        scoreRepository: ScoreRepository,
        scoreDao: ScoreDao,
        session: Session
    ): ScoreRepositoryDataSource {
        return ScoreRepositoryDataSourceImpl(
            scoreRepository = scoreRepository,
            scoreDao = scoreDao,
            session = session,
            mapper = mapper
        )
    }
}
