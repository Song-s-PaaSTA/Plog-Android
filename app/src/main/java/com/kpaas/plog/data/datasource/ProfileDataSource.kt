package com.kpaas.plog.data.datasource

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseMyPloggingListDto
import com.kpaas.plog.data.dto.response.ResponseSignUpDto

interface ProfileDataSource {
    suspend fun getProfile(): BaseResponse<ResponseSignUpDto>
    suspend fun getPlogging(): BaseResponse<ResponseMyPloggingListDto>
}