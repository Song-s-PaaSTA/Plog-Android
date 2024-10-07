package com.kpaas.plog.data.service

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.request.RequestSignUpDto
import com.kpaas.plog.data.dto.response.ResponseLoginDto
import com.kpaas.plog.data.dto.response.ResponseSignUpDto
import com.kpaas.plog.data.service.ApiKeyStorage.API
import com.kpaas.plog.data.service.ApiKeyStorage.COMPLETE
import com.kpaas.plog.data.service.ApiKeyStorage.LOGIN
import com.kpaas.plog.data.service.ApiKeyStorage.LOGOUT
import com.kpaas.plog.data.service.ApiKeyStorage.PROVIDER
import com.kpaas.plog.data.service.ApiKeyStorage.SIGNOUT
import com.kpaas.plog.data.service.ApiKeyStorage.SIGNUP
import com.kpaas.plog.data.service.ApiKeyStorage.V1
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApiService {
    @POST("/$API/$LOGIN/{$PROVIDER}")
    suspend fun postLogin(
        @Path("provider") provider: String,
    ): BaseResponse<ResponseLoginDto>

    @PATCH("/$API/$V1/$SIGNUP/$COMPLETE")
    suspend fun patchSignUp(
        requestSignUpDto: RequestSignUpDto
    ): BaseResponse<ResponseSignUpDto>

    @DELETE("/$API/$V1/$LOGOUT")
    suspend fun deleteLogout(): BaseResponse<String>

    @DELETE("/$API/$V1/$SIGNOUT")
    suspend fun deleteSignOut(): BaseResponse<String>
}