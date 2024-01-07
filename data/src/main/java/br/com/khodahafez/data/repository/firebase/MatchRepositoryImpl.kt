package br.com.khodahafez.data.repository.firebase

import br.com.khodahafez.domain.errors.NotFoundEntityException
import br.com.khodahafez.domain.model.MatchOfPoker
import br.com.khodahafez.domain.model.Player
import br.com.khodahafez.domain.repository.remote.MatchRepository
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

class MatchRepositoryImpl(
    private val databaseReference: DatabaseReference
) : MatchRepository {
    override fun save(matchOfPoker: MatchOfPoker): Flow<ResultOf<MatchOfPoker>> {
        return flow {
            kotlin.runCatching {

                matchOfPoker.id = databaseReference.push().key

                databaseReference.child(matchOfPoker.id!!).setValue(matchOfPoker)
                emit(ResultOf.Success(matchOfPoker))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun getByPlayer(player: Player): Flow<ResultOf<List<MatchOfPoker>>> {
        return callbackFlow {
            kotlin.runCatching {
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val listItems = dataSnapshot.children.filter { item ->
                                item.getValue<MatchOfPoker>()?.players?.filter { itemPlayer ->
                                    itemPlayer.id == player.id
                                }?.isNotEmpty() ?: false
                            }
                            trySend(ResultOf.Success(listItems as List<MatchOfPoker>))
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

                databaseReference.addListenerForSingleValueEvent(listener)

                awaitClose {
                    databaseReference.removeEventListener(listener)
                }
            }.onFailure {
                trySend((ResultOf.Failure(it)))
            }
        }
    }

    override fun getById(id: String): Flow<ResultOf<MatchOfPoker>> {
        return callbackFlow {
            kotlin.runCatching {
                val query = databaseReference.orderByChild("id").equalTo(id)
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            trySend(ResultOf.Success(dataSnapshot.children.first().getValue<MatchOfPoker>()!!))
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

    override fun getAll(): Flow<ResultOf<List<MatchOfPoker>>> {
        return callbackFlow {
            kotlin.runCatching {
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            trySend(ResultOf.Success(dataSnapshot.children as List<MatchOfPoker>))
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

                databaseReference.addListenerForSingleValueEvent(listener)

                awaitClose {
                    databaseReference.removeEventListener(listener)
                }
            }.onFailure {
                trySend((ResultOf.Failure(it)))
            }
        }
    }
}
