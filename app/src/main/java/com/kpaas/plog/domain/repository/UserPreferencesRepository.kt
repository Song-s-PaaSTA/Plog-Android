package com.kpaas.plog.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun saveUserAccessToken(accessToken: String)

    fun getUserAccessToken(): Flow<String?>

    suspend fun saveCheckLogin(checkLogin: Boolean)

    fun getCheckLogin(): Flow<Boolean>

    suspend fun clear()
}