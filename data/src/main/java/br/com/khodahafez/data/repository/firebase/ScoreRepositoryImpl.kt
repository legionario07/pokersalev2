package br.com.khodahafez.data.repository.firebase

import br.com.khodahafez.domain.errors.NotFoundEntityException
import br.com.khodahafez.domain.model.Score
import br.com.khodahafez.domain.repository.remote.ScoreRepository
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

class ScoreRepositoryImpl(
    private val databaseReference: DatabaseReference
) : ScoreRepository {
    override fun save(score: Score): Flow<ResultOf<Score>> {
        return flow {
            kotlin.runCatching {

                score.id = databaseReference.push().key

                databaseReference.child(score.id!!).setValue(score)
                emit(ResultOf.Success(score))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun getByIdPlayer(idPlayer: String): Flow<ResultOf<List<Score>>> {
        return callbackFlow {
            kotlin.runCatching {
                val query = databaseReference.orderByChild("idPlayer").equalTo(idPlayer)
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val list: MutableList<Score> = dataSnapshot.children.map {
                                it.getValue<Score>()!!
                            }.toMutableList()
                            trySend(
                                ResultOf.Success(list)
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

    override fun getAll(): Flow<ResultOf<List<Score>>> {
        return callbackFlow {
            kotlin.runCatching {
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val list: MutableList<Score> = dataSnapshot.children.map {
                                it.getValue<Score>()!!
                            }.toMutableList()
                            trySend(
                                ResultOf.Success(list)
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

