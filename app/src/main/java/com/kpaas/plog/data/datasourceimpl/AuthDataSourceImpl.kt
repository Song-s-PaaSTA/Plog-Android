package com.kpaas.plog.data.datasourceimpl

import com.kpaas.plog.data.datasource.AuthDataSource
import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.request.RequestSignUpDto
import com.kpaas.plog.data.dto.response.ResponseLoginDto
import com.kpaas.plog.data.dto.response.ResponseRenewDto
import com.kpaas.plog.data.dto.response.ResponseSignUpDto
import com.kpaas.plog.data.service.AuthApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthDataSource {
    override suspend fun postLogin(provider: String, code: String): BaseResponse<ResponseLoginDto> {
        return authApiService.postLogin(provider, code)
    }

    override suspend fun patchSignUp(
        request: RequestBody,
        file: MultipartBody.Part
    ): BaseResponse<ResponseSignUpDto> {
        return authApiService.patchSignUp(request, file)
    }

    override suspend fun deleteLogout(): BaseResponse<String?> {
        return authApiService.deleteLogout()
    }

    override suspend fun deleteSignOut(
    ): BaseResponse<String?> {
        return authApiService.deleteSignOut()
    }

    override suspend fun postRenew(): BaseResponse<ResponseRenewDto> {
        return authApiService.postRenew()
    }

}