package com.kpaas.plog.data.datasourceimpl

import com.kpaas.plog.data.datasource.SearchDataSource
import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseSearchDto
import com.kpaas.plog.data.service.SearchApiService
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : SearchDataSource {
    override suspend fun getPlace(query: String): BaseResponse<ResponseSearchDto> {
        return searchApiService.getPlace(query)
    }
}