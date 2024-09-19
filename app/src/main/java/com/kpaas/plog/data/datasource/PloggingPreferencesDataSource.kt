package com.kpaas.plog.data.datasource

import kotlinx.coroutines.flow.Flow

interface PloggingPreferencesDataSource {
    suspend fun saveButtonText(text: String)
    fun getButtonText(): Flow<String>

    suspend fun saveStartTime(startTime: Long)
    fun getStartTime(): Flow<Long>

    suspend fun saveStart(start: String)
    fun getStart(): Flow<String>

    suspend fun saveDestination(destination: String)
    fun getDestination(): Flow<String>

    suspend fun saveStopover(stopover: String)
    fun getStopover(): Flow<String>

    suspend fun saveSearchTextFieldVisible(visibility: Boolean)
    fun getSearchTextFieldVisible(): Flow<Boolean>

    suspend fun saveStopoverTextFieldVisible(visibility: Boolean)
    fun getStopoverTextFieldVisible(): Flow<Boolean>

    suspend fun saveAll(
        buttonText: String,
        startTime: Long,
        start: String,
        destination: String,
        stopover: String,
        searchTextFieldVisible: Boolean,
        stopoverTextFieldVisible: Boolean
    )

    suspend fun clear()
}