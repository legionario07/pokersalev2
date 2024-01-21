package br.com.khodahafez.data.repository

import br.com.khodahafez.domain.mapper.PlayerMapper
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.model.dto.PlayerDto
import br.com.khodahafez.domain.repository.PlayerRepositoryDataSource
import br.com.khodahafez.domain.repository.local.PlayerDao
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.utils.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PlayerRepositoryDataSourceImpl(
    private val playerRepository: PlayerRepository,
    private val playerDao: PlayerDao,
    private val session: Session,
    private val mapper: PlayerMapper
) : PlayerRepositoryDataSource {

    override fun getAll(): Flow<ResultOf<List<Player>>> {
        return flow {
            if (session.shouldGetInRemoteDatabase) {
                playerRepository.getAll().map { resultOf ->
                    when (resultOf) {
                        is ResultOf.Success -> {
                            setValueForSearchingNextInLocal(session)
                            emit(
                                ResultOf.Success(resultOf.response.map {
                                    val domain = mapper.toDomain(it)
                                    saveInDataBaseLocal(domain)
                                    domain
                                })
                            )
                        }

                        is ResultOf.Failure -> {
                            emit(resultOf)
                        }

                        else -> {
                            // Do Nothing
                        }
                    }
                }.collect()
            } else {
                emit(
                    ResultOf.Success(
                        playerDao.getAll().map {
                            mapper.toDomain(it)
                        }
                    )
                )
            }
        }
    }

    override fun save(vararg entity: Player): Flow<ResultOf<PlayerDto>> {
        TODO("Not yet implemented")
    }

    private fun setValueForSearchingNextInLocal(session: Session) {
        session.shouldGetInRemoteDatabase = false
    }

    private fun saveInDataBaseLocal(domain: Player) {
        playerDao.insertAll(mapper.toEntityDb(domain))
    }
}
