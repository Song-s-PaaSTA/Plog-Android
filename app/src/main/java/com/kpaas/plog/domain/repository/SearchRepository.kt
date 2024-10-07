package com.kpaas.plog.domain.repository

import com.kpaas.plog.data.dto.response.Place

interface SearchRepository {
    suspend fun getPlace(query: String): Result<List<Place>>
}