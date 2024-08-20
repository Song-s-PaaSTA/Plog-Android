package com.kpaas.plog.data.datasource

import com.kpaas.plog.data.dto.ExampleBaseResponse
import com.kpaas.plog.data.dto.response.ResponseExampleDto

interface ExampleDataSource {
    suspend fun getUsers(page: Int) : ExampleBaseResponse<List<ResponseExampleDto>>
}