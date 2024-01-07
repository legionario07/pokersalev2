package br.com.khodahafez.domain.repository.remote

import br.com.khodahafez.domain.model.BountyType
import br.com.khodahafez.domain.state.ResultOf
import kotlinx.coroutines.flow.Flow

interface BountyTypeRepository {

    fun save(
        bountyType: BountyType
    ): Flow<ResultOf<BountyType>>

    fun update(
        bountyType: BountyType
    ): Flow<ResultOf<BountyType>>

    fun getById(
        id: String,
    ): Flow<ResultOf<BountyType>>

    fun getAll(): Flow<ResultOf<List<BountyType>>>
}
