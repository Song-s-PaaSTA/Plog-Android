package com.kpaas.plog.domain.repository

import com.kpaas.plog.data.dto.response.Trash

interface TrashRepository {
    suspend fun getTrash(): Result<List<Trash>>
}