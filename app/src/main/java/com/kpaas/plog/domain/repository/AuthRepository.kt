package com.kpaas.plog.domain.repository

import com.kpaas.plog.domain.entity.LoginEntity
import java.io.File

interface AuthRepository {
    suspend fun postLogin(provider: String, code: String): Result<LoginEntity>
    suspend fun patchSignUp(nickname: String, file: File): Result<Unit>
    suspend fun deleteLogout(refreshToken: String): Result<String?>
    suspend fun deleteSignOut(refreshToken: String): Result<String?>
}