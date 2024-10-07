package com.kpaas.plog.domain.repository

import com.kpaas.plog.domain.entity.LoginEntity

interface AuthRepository {
    suspend fun postLogin(provider: String): Result<LoginEntity>
    suspend fun patchSignUp(nickname: String): Result<Unit>
    suspend fun deleteLogout(): Result<String>
    suspend fun deleteSignOut(): Result<String>
}