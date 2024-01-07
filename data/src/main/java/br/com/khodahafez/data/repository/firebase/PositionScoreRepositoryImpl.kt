package br.com.khodahafez.data.repository.firebase

import br.com.khodahafez.domain.errors.NotFoundEntityException
import br.com.khodahafez.domain.model.PositionScore
import br.com.khodahafez.domain.repository.remote.PositionScoreRepository
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

class PositionScoreRepositoryImpl(
    private val databaseReference: DatabaseReference
) : PositionScoreRepository {
    override fun save(positionScore: PositionScore): Flow<ResultOf<PositionScore>> {
        return flow {
            kotlin.runCatching {

                positionScore.id = databaseReference.push().key

                databaseReference.child(positionScore.id!!).setValue(positionScore)
                emit(ResultOf.Success(positionScore))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun update(positionScore: PositionScore): Flow<ResultOf<PositionScore>> {
        return flow {
            kotlin.runCatching {
                databaseReference.child(positionScore.id!!).setValue(positionScore)
                emit(ResultOf.Success(positionScore))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun getById(id: String): Flow<ResultOf<PositionScore>> {
        return callbackFlow {
            kotlin.runCatching {
                val query = databaseReference.orderByChild("id").equalTo(id)
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            trySend(ResultOf.Success(dataSnapshot.children.first().getValue<PositionScore>()!!))
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

    override fun getAll(): Flow<ResultOf<List<PositionScore>>> {
        return callbackFlow {
            kotlin.runCatching {
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            trySend(ResultOf.Success(dataSnapshot.children as List<PositionScore>))
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

