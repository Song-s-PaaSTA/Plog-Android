package com.kpaas.plog.data.datasource

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.request.RequestSignUpDto
import com.kpaas.plog.data.dto.response.ResponseLoginDto
import com.kpaas.plog.data.dto.response.ResponseSignUpDto

interface AuthDataSource {
    suspend fun postLogin(provider: String): BaseResponse<ResponseLoginDto>
    suspend fun patchSignUp(requestSignUpDto: RequestSignUpDto): BaseResponse<ResponseSignUpDto>
    suspend fun deleteLogout(): BaseResponse<String>
    suspend fun deleteSignOut(): BaseResponse<String>
}