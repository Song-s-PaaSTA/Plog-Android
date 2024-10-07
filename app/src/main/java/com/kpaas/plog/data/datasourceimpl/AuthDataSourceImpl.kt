package com.kpaas.plog.data.datasourceimpl

import com.kpaas.plog.data.datasource.AuthDataSource
import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.request.RequestSignUpDto
import com.kpaas.plog.data.dto.response.ResponseLoginDto
import com.kpaas.plog.data.dto.response.ResponseSignUpDto
import com.kpaas.plog.data.service.AuthApiService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthDataSource {
    override suspend fun postLogin(provider: String): BaseResponse<ResponseLoginDto> {
        return authApiService.postLogin(provider)
    }

    override suspend fun patchSignUp(requestSignUpDto: RequestSignUpDto): BaseResponse<ResponseSignUpDto> {
        return authApiService.patchSignUp(requestSignUpDto)
    }

    override suspend fun deleteLogout(): BaseResponse<String> {
        return authApiService.deleteLogout()
    }

    override suspend fun deleteSignOut(): BaseResponse<String> {
        return authApiService.deleteSignOut()
    }

}