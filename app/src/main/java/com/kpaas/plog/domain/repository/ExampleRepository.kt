package com.kpaas.plog.domain.repository

import com.kpaas.plog.domain.entity.ExampleEntity

interface ExampleRepository {
    suspend fun getUsers(page: Int): Result<List<ExampleEntity>>
}