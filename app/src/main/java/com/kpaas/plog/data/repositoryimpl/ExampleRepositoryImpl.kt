package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.ExampleDataSource
import com.kpaas.plog.data.mapper.toExampleEntity
import com.kpaas.plog.domain.entity.ExampleEntity
import com.kpaas.plog.domain.repository.ExampleRepository
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(
    private val exampleDataSource: ExampleDataSource
) : ExampleRepository {
    override suspend fun getUsers(page: Int): Result<List<ExampleEntity>> {
        return runCatching {
            exampleDataSource.getUsers(page).data?.map { it.toExampleEntity() } ?: emptyList()
        }
    }
}