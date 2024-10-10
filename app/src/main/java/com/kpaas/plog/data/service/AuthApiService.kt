package com.kpaas.plog.data.service

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.request.RequestSignUpDto
import com.kpaas.plog.data.dto.response.ResponseLoginDto
import com.kpaas.plog.data.dto.response.ResponseSignUpDto
import com.kpaas.plog.data.service.ApiKeyStorage.API
import com.kpaas.plog.data.service.ApiKeyStorage.COMPLETE
import com.kpaas.plog.data.service.ApiKeyStorage.LOGIN
import com.kpaas.plog.data.service.ApiKeyStorage.LOGOUT
import com.kpaas.plog.data.service.ApiKeyStorage.MEMBER_SERVICE
import com.kpaas.plog.data.service.ApiKeyStorage.PROVIDER
import com.kpaas.plog.data.service.ApiKeyStorage.SIGNOUT
import com.kpaas.plog.data.service.ApiKeyStorage.SIGNUP
import com.kpaas.plog.data.service.ApiKeyStorage.V1
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApiService {
    @POST("/$MEMBER_SERVICE/$API/$V1/$LOGIN/{$PROVIDER}")
    suspend fun postLogin(
        @Path("provider") provider: String,
        @Query("code") code: String
    ): BaseResponse<ResponseLoginDto>

    @Multipart
    @PATCH("/$MEMBER_SERVICE/$API/$V1/$SIGNUP/$COMPLETE")
    suspend fun patchSignUp(
        @Part("request") request: RequestBody,
        @Part file: MultipartBody.Part
    ): BaseResponse<ResponseSignUpDto>

    @DELETE("/$MEMBER_SERVICE/$API/$V1/$LOGOUT")
    suspend fun deleteLogout(
        @Header("RefreshToken") refreshToken: String
    ): BaseResponse<String?>

    @DELETE("/$MEMBER_SERVICE/$API/$V1/$SIGNOUT")
    suspend fun deleteSignOut(
        @Header("RefreshToken") refreshToken: String
    ): BaseResponse<String?>
}