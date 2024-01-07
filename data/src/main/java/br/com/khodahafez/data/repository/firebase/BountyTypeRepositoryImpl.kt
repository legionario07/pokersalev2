package br.com.khodahafez.data.repository.firebase

import br.com.khodahafez.domain.errors.NotFoundEntityException
import br.com.khodahafez.domain.model.BountyType
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
    override fun save(bountyType: BountyType): Flow<ResultOf<BountyType>> {
        return flow {
            kotlin.runCatching {

                bountyType.id = databaseReference.push().key

                databaseReference.child(bountyType.id!!).setValue(bountyType)
                emit(ResultOf.Success(bountyType))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun update(bountyType: BountyType): Flow<ResultOf<BountyType>> {
        return flow {
            kotlin.runCatching {
                databaseReference.child(bountyType.id!!).setValue(bountyType)
                emit(ResultOf.Success(bountyType))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun getById(id: String): Flow<ResultOf<BountyType>> {
        return callbackFlow {
            kotlin.runCatching {
                val query = databaseReference.orderByChild("id").equalTo(id)
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            trySend(ResultOf.Success(dataSnapshot.children.first().getValue<BountyType>()!!))
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

    override fun getAll(): Flow<ResultOf<List<BountyType>>> {
        return callbackFlow {
            kotlin.runCatching {
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            trySend(ResultOf.Success(dataSnapshot.children as List<BountyType>))
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

