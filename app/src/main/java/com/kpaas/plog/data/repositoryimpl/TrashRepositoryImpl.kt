package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.TrashDataSource
import com.kpaas.plog.data.mapper.toMarkerEntity
import com.kpaas.plog.domain.entity.MarkerEntity
import com.kpaas.plog.domain.repository.TrashRepository
import javax.inject.Inject

class TrashRepositoryImpl @Inject constructor(
    private val trashDataSource: TrashDataSource
) : TrashRepository {
    override suspend fun getTrash(): Result<List<MarkerEntity>> {
        return runCatching {
            trashDataSource.getTrash().message?.trashPlaces?.map { it.toMarkerEntity() }
                ?: emptyList()
        }
    }
}