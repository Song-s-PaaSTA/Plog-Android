package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.AuthDataSource
import com.kpaas.plog.data.dto.request.RequestSignUpDto
import com.kpaas.plog.data.mapper.toLoginEntity
import com.kpaas.plog.domain.entity.LoginEntity
import com.kpaas.plog.domain.repository.AuthRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun postLogin(provider: String, code: String): Result<LoginEntity> {
        return runCatching {
            authDataSource.postLogin(provider, code).message?.toLoginEntity()
                ?: throw Exception("Data is null")
        }
    }

    override suspend fun patchSignUp(nickname: String, profileImage: File): Result<Unit> {
        return runCatching {
            val nicknameBody = nickname.toRequestBody("text/plain".toMediaTypeOrNull())
            val filePart = profileImage.let {
                val requestBody = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("profileImage", it.name, requestBody)
            }

            authDataSource.patchSignUp(nicknameBody, filePart).message?.let {}
                ?: throw Exception("Data is null")
        }
    }


    override suspend fun deleteLogout(): Result<String?> {
        return runCatching {
            authDataSource.deleteLogout().message ?: ""
        }
    }

    override suspend fun deleteSignOut(): Result<String?> {
        return runCatching {
            authDataSource.deleteSignOut().message ?: ""
        }
    }
}