package com.kpaas.plog.domain.repository

import com.kpaas.plog.domain.entity.LatLngEntity
import com.kpaas.plog.util.Location
import kotlinx.coroutines.flow.Flow

interface PloggingPreferencesRepository {
    suspend fun saveButtonText(text: String)
    fun getButtonText(): Flow<String>

    suspend fun saveStartTime(startTime: Long)
    fun getStartTime(): Flow<Long>

    suspend fun saveStart(start: Location?)
    fun getStart(): Flow<Location?>

    suspend fun saveDestination(destination: Location?)
    fun getDestination(): Flow<Location?>

    suspend fun saveStopover(stopover: Location?)
    fun getStopover(): Flow<Location?>

    suspend fun saveSearchTextFieldVisible(visibility: Boolean)
    fun getSearchTextFieldVisible(): Flow<Boolean>

    suspend fun saveStopoverTextFieldVisible(visibility: Boolean)
    fun getStopoverTextFieldVisible(): Flow<Boolean>

    suspend fun saveRoute(route: List<LatLngEntity>)
    fun getRoute(): Flow<List<LatLngEntity>>

    suspend fun saveAll(
        buttonText: String,
        startTime: Long,
        start: Location?,
        destination: Location?,
        stopover: Location?,
        searchTextFieldVisible: Boolean,
        stopoverTextFieldVisible: Boolean
    )

    suspend fun clear()
}