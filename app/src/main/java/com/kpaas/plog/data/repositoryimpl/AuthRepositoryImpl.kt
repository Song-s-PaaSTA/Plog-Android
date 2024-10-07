package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.AuthDataSource
import com.kpaas.plog.data.dto.request.RequestSignUpDto
import com.kpaas.plog.data.mapper.toLoginEntity
import com.kpaas.plog.domain.entity.LoginEntity
import com.kpaas.plog.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun postLogin(provider: String): Result<LoginEntity> {
        return runCatching {
            authDataSource.postLogin(provider).content?.toLoginEntity()
                ?: throw Exception("Data is null")
        }
    }

    override suspend fun patchSignUp(nickname: String): Result<Unit> {
        return runCatching {
            authDataSource.patchSignUp(
                requestSignUpDto = RequestSignUpDto(
                    nickname = nickname
                )
            ).content
        }
    }

    override suspend fun deleteLogout(): Result<String> {
        return runCatching {
            authDataSource.deleteLogout().content ?: ""
        }
    }

    override suspend fun deleteSignOut(): Result<String> {
        return runCatching {
            authDataSource.deleteSignOut().content ?: ""
        }
    }
}