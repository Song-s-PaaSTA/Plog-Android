package com.kpaas.plog.data.datasource

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.request.RequestSignUpDto
import com.kpaas.plog.data.dto.response.ResponseLoginDto
import com.kpaas.plog.data.dto.response.ResponseSignUpDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthDataSource {
    suspend fun postLogin(provider: String, code: String): BaseResponse<ResponseLoginDto>
    suspend fun patchSignUp(
        request: RequestBody,
        file: MultipartBody.Part
    ): BaseResponse<ResponseSignUpDto>

    suspend fun deleteLogout(): BaseResponse<String?>
    suspend fun deleteSignOut(): BaseResponse<String?>
}