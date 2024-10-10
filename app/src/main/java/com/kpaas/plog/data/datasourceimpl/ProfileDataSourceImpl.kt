package com.kpaas.plog.data.datasourceimpl

import com.kpaas.plog.data.datasource.ProfileDataSource
import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseMyPloggingListDto
import com.kpaas.plog.data.dto.response.ResponseSignUpDto
import com.kpaas.plog.data.service.ProfileApiService
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val profileApiService: ProfileApiService
) : ProfileDataSource {
    override suspend fun getProfile(): BaseResponse<ResponseSignUpDto> {
        return profileApiService.getProfile()
    }

    override suspend fun getPlogging(): BaseResponse<ResponseMyPloggingListDto> {
        return profileApiService.getPlogging()
    }
}