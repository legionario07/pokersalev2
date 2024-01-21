package br.com.khodahafez.data.repository

import br.com.khodahafez.domain.mapper.ScoreMapper
import br.com.khodahafez.domain.model.Score
import br.com.khodahafez.domain.repository.ScoreRepositoryDataSource
import br.com.khodahafez.domain.repository.local.ScoreDao
import br.com.khodahafez.domain.repository.remote.ScoreRepository
import br.com.khodahafez.domain.state.ResultOf
import br.com.khodahafez.domain.utils.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ScoreRepositoryDataSourceImpl(
    private val scoreRepository: ScoreRepository,
    private val scoreDao: ScoreDao,
    private val session: Session,
    private val mapper: ScoreMapper
) : ScoreRepositoryDataSource {

    override fun getAll(): Flow<ResultOf<List<Score>>> {
        return flow {
            if (session.shouldGetPlayersInRemoteDatabase) {

                scoreRepository.getAll().map { resultOf ->
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
                        scoreDao.getAll().map {
                            mapper.toDomain(it)
                        }
                    )
                )
            }
        }
    }

    private fun setValueForSearchingNextInLocal(session: Session) {
        session.shouldGetScoreInRemoteDatabase = false
    }

    private fun saveInDataBaseLocal(domain: Score) {
        scoreDao.insertAll(mapper.toEntityDb(domain))
    }
}
