package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.SearchDataSource
import com.kpaas.plog.data.mapper.toSearchResultListEntity
import com.kpaas.plog.domain.entity.SearchResultListEntity
import com.kpaas.plog.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource
) : SearchRepository {
    override suspend fun getPlace(query: String): Result<List<SearchResultListEntity>> {
        return runCatching {
            searchDataSource.getPlace(query).message?.searchedPlace?.map { it.toSearchResultListEntity() }
                ?: emptyList()
        }
    }
}