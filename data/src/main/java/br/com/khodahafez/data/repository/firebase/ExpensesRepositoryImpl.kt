package br.com.khodahafez.data.repository.firebase

import br.com.khodahafez.domain.errors.NotFoundEntityException
import br.com.khodahafez.domain.model.Expenses
import br.com.khodahafez.domain.repository.remote.ExpensesRepository
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

class ExpensesRepositoryImpl(
    private val databaseReference: DatabaseReference
) : ExpensesRepository {
    override fun save(expenses: Expenses): Flow<ResultOf<Expenses>> {
        return flow {
            kotlin.runCatching {

                expenses.id = databaseReference.push().key

                databaseReference.child(expenses.id!!).setValue(expenses)
                emit(ResultOf.Success(expenses))
            }.onFailure {
                emit(ResultOf.Failure(it))
            }
        }
    }

    override fun getByIdPlayer(idPlayer: String): Flow<ResultOf<List<Expenses>>> {
        return callbackFlow {
            kotlin.runCatching {
                val query = databaseReference.orderByChild("idPlayer").equalTo(idPlayer)
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val list: MutableList<Expenses> = dataSnapshot.children.map {
                                it.getValue<Expenses>()!!
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

    override fun getAll(): Flow<ResultOf<List<Expenses>>> {
        return callbackFlow {
            kotlin.runCatching {
                val listener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val list: MutableList<Expenses> = dataSnapshot.children.map {
                                it.getValue<Expenses>()!!
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

