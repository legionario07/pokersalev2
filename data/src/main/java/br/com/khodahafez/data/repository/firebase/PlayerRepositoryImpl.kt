package br.com.khodahafez.data.repository.firebase

import br.com.khodahafez.domain.errors.NotFoundEntityException
import br.com.khodahafez.domain.errors.NotFoundUserException
import br.com.khodahafez.domain.errors.PasswordUserException
import br.com.khodahafez.domain.errors.PokerSaleV2Error
import br.com.khodahafez.domain.model.Balance
import br.com.khodahafez.domain.model.Player
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
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class PlayerRepositoryImpl(
    private val databaseReference: DatabaseReference
) : PlayerRepository {
    override fun save(player: Player): Flow<ResultOf<Player>> {
        return flow {
            kotlin.runCatching {

                player.id = databaseReference.push().key

                databaseReference.child(player.id!!).setValue(player)
                emit(ResultOf.Success(player))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun update(player: Player): Flow<ResultOf<Player>> {
        return flow {
            kotlin.runCatching {
                databaseReference.child(player.id!!).setValue(player)
                emit(ResultOf.Success(player))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun get(login: String, password: String): Flow<ResultOf<Player>> {
        return callbackFlow {
            kotlin.runCatching {
                val query = databaseReference.orderByChild("login").equalTo(login)
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {

                            var player: Player? = null
                            for (item in dataSnapshot.children) {
                                val playerTemp = item.getValue<Player>()
                                if (playerTemp?.password == password) {
                                    player = playerTemp
                                    trySend(ResultOf.Success(player))
                                }
                            }

                            if (player == null) {
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

    override fun getAll(): Flow<ResultOf<List<Player>>> {
        return callbackFlow {
            kotlin.runCatching {
                val query = databaseReference.orderByChild("nome")
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                                    val listPlayer: MutableList<Player> = dataSnapshot.children.map {
                                        it.getValue<Player>()!!
                                    }.toMutableList()
                            trySend(
                                ResultOf.Success(listPlayer)
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
