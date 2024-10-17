package com.kpaas.plog.data.datasourceimpl

import com.kpaas.plog.data.datasource.TrashDataSource
import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseTrashDto
import com.kpaas.plog.data.service.TrashApiService
import javax.inject.Inject

class TrashDataSourceImpl @Inject constructor(
    private val trashApiService: TrashApiService
) : TrashDataSource {
    override suspend fun getTrash(): BaseResponse<ResponseTrashDto> {
        return trashApiService.getTrash()
    }
}