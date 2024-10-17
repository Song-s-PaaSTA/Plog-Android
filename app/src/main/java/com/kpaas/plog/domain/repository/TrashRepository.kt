package com.kpaas.plog.domain.repository

import com.kpaas.plog.data.dto.response.Trash
import com.kpaas.plog.domain.entity.MarkerEntity

interface TrashRepository {
    suspend fun getTrash(): Result<List<MarkerEntity>>
}