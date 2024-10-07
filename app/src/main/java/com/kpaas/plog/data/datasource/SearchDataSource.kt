package com.kpaas.plog.data.datasource

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseSearchDto

interface SearchDataSource {
    suspend fun getPlace(query: String): BaseResponse<ResponseSearchDto>
}