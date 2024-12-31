package br.com.khodahafez.data.repository.firebase

import br.com.khodahafez.domain.errors.NotFoundEntityException
import br.com.khodahafez.domain.errors.NotFoundUserException
import br.com.khodahafez.domain.errors.PasswordUserException
import br.com.khodahafez.domain.model.dto.PlayerDto
import br.com.khodahafez.domain.repository.remote.PlayerRepository
import br.com.khodahafez.domain.state.ResultOf
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class PlayerRepositoryImpl(
    private val databaseReference: DatabaseReference
) : PlayerRepository {
    override fun save(playerDto: PlayerDto): Flow<ResultOf<PlayerDto>> {
        return flow {
            kotlin.runCatching {

                playerDto.id = databaseReference.push().key

                databaseReference.child(playerDto.id!!).setValue(playerDto)
                emit(ResultOf.Success(playerDto))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun update(playerDto: PlayerDto): Flow<ResultOf<PlayerDto>> {
        return flow {
            kotlin.runCatching {
                databaseReference.child(playerDto.id!!).setValue(playerDto)
                emit(ResultOf.Success(playerDto))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun get(login: String, password: String): Flow<ResultOf<PlayerDto>> {
        return callbackFlow {
            kotlin.runCatching {
                val query = databaseReference.orderByChild("login").equalTo(login)
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {

                            var playerDto: PlayerDto? = null
                            for (item in dataSnapshot.children) {
                                val playerDtoTemp = item.getValue<PlayerDto>()
                                if (playerDtoTemp?.password == password) {
                                    playerDto = playerDtoTemp
                                    trySend(ResultOf.Success(playerDto))
                                }
                            }

                            if (playerDto == null) {
                                trySend(
                                    ResultOf.Failure(
                                        PasswordUserException()
                                    )
                                )
                            }
                        } else {
                            trySend(
                                ResultOf.Failure(
                                    NotFoundUserException()
                                )
                            )
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                }

                query.addListenerForSingleValueEvent(listener)

                awaitClose {
                    query.removeEventListener(listener)
                }
            }.onFailure {
                trySend((ResultOf.Failure(it)))
            }
        }
    }

    override fun getAll(): Flow<ResultOf<List<PlayerDto>>> {
        return callbackFlow {
            kotlin.runCatching {
                val query = databaseReference.orderByChild("nome")
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                                    val listPlayerDtos: MutableList<PlayerDto> = dataSnapshot.children.map {
                                        it.getValue<PlayerDto>()!!
                                    }.toMutableList()
                            trySend(
                                ResultOf.Success(listPlayerDtos)
                            )
                        } else {
                            trySend(
                                ResultOf.Failure(
                                    NotFoundEntityException()
                                )
                            )
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                }

                query.addListenerForSingleValueEvent(listener)

                awaitClose {
                    query.removeEventListener(listener)
                }
            }.onFailure {
                trySend((ResultOf.Failure(it)))
            }
        }
    }
}
