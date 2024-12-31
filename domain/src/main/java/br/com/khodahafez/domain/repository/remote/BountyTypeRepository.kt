package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.BountyValues
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface BountyTypeRepository {

    fun save(
        bountyValues: BountyValues
    ): Flow<ResultOf<BountyValues>>

    fun update(
        bountyValues: BountyValues
    ): Flow<ResultOf<BountyValues>>

    fun getById(
        id: String,
    ): Flow<ResultOf<BountyValues>>

    fun getAll(): Flow<ResultOf<List<BountyValues>>>
}
