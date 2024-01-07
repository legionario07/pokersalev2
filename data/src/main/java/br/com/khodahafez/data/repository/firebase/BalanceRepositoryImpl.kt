package br.com.khodahafez.data.repository.firebase

import br.com.khodahafez.domain.errors.NotFoundEntityException
import br.com.khodahafez.domain.model.Balance
import br.com.khodahafez.domain.model.BankType
import br.com.khodahafez.domain.repository.remote.BalanceRepository
import br.com.khodahafez.domain.state.ResultOf
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class BalanceRepositoryImpl(
    private val databaseReference: DatabaseReference
) : BalanceRepository {
    override fun save(balance: Balance): Flow<ResultOf<Balance>> {
        return flow {
            kotlin.runCatching {

                balance.id = databaseReference.push().key

                databaseReference.child(balance.id!!).setValue(balance)
                emit(ResultOf.Success(balance))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun getByOperationType(operationType: BankType): Flow<ResultOf<List<Balance>>> {
        return callbackFlow {
            kotlin.runCatching {
                val query = databaseReference.orderByChild("operationType").equalTo(operationType.name)
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            trySend(ResultOf.Success(dataSnapshot.children as List<Balance>))
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

    override fun getAll(): Flow<ResultOf<List<Balance>>> {
        return callbackFlow {
            kotlin.runCatching {
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            trySend(ResultOf.Success(dataSnapshot.children as List<Balance>))
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

