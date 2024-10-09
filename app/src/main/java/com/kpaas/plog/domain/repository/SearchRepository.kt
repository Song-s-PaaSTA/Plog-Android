package com.kpaas.plog.domain.repository

import com.kpaas.plog.domain.entity.SearchResultListEntity

interface SearchRepository {
    suspend fun getPlace(query: String): Result<List<SearchResultListEntity>>
}