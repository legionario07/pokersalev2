package br.com.khodahafez.data.repository.firebase

import br.com.khodahafez.domain.errors.NotFoundEntityException
import br.com.khodahafez.domain.model.BountyValues
import br.com.khodahafez.domain.repository.remote.BountyTypeRepository
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

class BountyTypeRepositoryImpl(
    private val databaseReference: DatabaseReference
) : BountyTypeRepository {
    override fun save(bountyValues: BountyValues): Flow<ResultOf<BountyValues>> {
        return flow {
            kotlin.runCatching {

                bountyValues.id = databaseReference.push().key

                databaseReference.child(bountyValues.id!!).setValue(bountyValues)
                emit(ResultOf.Success(bountyValues))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun update(bountyValues: BountyValues): Flow<ResultOf<BountyValues>> {
        return flow {
            kotlin.runCatching {
                databaseReference.child(bountyValues.id!!).setValue(bountyValues)
                emit(ResultOf.Success(bountyValues))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun getById(id: String): Flow<ResultOf<BountyValues>> {
        return callbackFlow {
            kotlin.runCatching {
                val query = databaseReference.orderByChild("id").equalTo(id)
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            trySend(ResultOf.Success(dataSnapshot.children.first().getValue<BountyValues>()!!))
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

    override fun getAll(): Flow<ResultOf<List<BountyValues>>> {
        return callbackFlow {
            kotlin.runCatching {
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            trySend(ResultOf.Success(dataSnapshot.children as List<BountyValues>))
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

