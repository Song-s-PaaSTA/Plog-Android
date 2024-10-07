package com.kpaas.plog.data.datasource

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseTrashDto

interface TrashDataSource {
    suspend fun getTrash(): BaseResponse<ResponseTrashDto>
}