package com.kpaas.plog.data.datasourceimpl

import com.kpaas.plog.data.datasource.ExampleDataSource
import com.kpaas.plog.data.dto.ExampleBaseResponse
import com.kpaas.plog.data.dto.response.ResponseExampleDto
import com.kpaas.plog.data.service.ExampleApiService
import javax.inject.Inject

class ExampleDataSourceImpl @Inject constructor(
    private val exampleApiService: ExampleApiService
) : ExampleDataSource {
    override suspend fun getUsers(page: Int): ExampleBaseResponse<List<ResponseExampleDto>> {
        return exampleApiService.getUsers(page)
    }
}