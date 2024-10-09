package com.kpaas.plog.data.service

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseTrashDto
import com.kpaas.plog.data.service.ApiKeyStorage.API
import com.kpaas.plog.data.service.ApiKeyStorage.TRASH
import com.kpaas.plog.data.service.ApiKeyStorage.TRASH_SERVICE
import com.kpaas.plog.data.service.ApiKeyStorage.V1
import retrofit2.http.GET

interface TrashApiService {
    @GET("/$TRASH_SERVICE/$API/$V1/$TRASH")
    suspend fun getTrash(): BaseResponse<ResponseTrashDto>
}