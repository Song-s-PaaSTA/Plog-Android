package com.kpaas.plog.domain.repository

import com.kpaas.plog.data.dto.response.PloggingList
import com.kpaas.plog.data.dto.response.ResponseMyPloggingListDto
import com.kpaas.plog.data.dto.response.ResponseSignUpDto
import com.kpaas.plog.domain.entity.MyPloggingListEntity

interface ProfileRepository {
    suspend fun getProfile(): Result<ResponseSignUpDto>
    suspend fun getPlogging(): Result<List<MyPloggingListEntity>>
}