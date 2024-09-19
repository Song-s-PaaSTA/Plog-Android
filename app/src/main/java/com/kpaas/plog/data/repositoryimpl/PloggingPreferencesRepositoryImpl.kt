package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.PloggingPreferencesDataSource
import com.kpaas.plog.domain.repository.PloggingPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PloggingPreferencesRepositoryImpl @Inject constructor(
    private val dataSource: PloggingPreferencesDataSource
) : PloggingPreferencesRepository {
    override suspend fun saveButtonText(text: String) {
        dataSource.saveButtonText(text)
    }

    override fun getButtonText(): Flow<String> {
        return dataSource.getButtonText()
    }

    override suspend fun saveStartTime(startTime: Long) {
        return dataSource.saveStartTime(startTime)
    }

    override fun getStartTime(): Flow<Long> {
        return dataSource.getStartTime()
    }

    override suspend fun saveStart(start: String) {
        dataSource.saveStart(start)
    }

    override fun getStart(): Flow<String> {
        return dataSource.getStart()
    }

    override suspend fun saveDestination(destination: String) {
        dataSource.saveDestination(destination)
    }

    override fun getDestination(): Flow<String> {
        return dataSource.getDestination()
    }

    override suspend fun saveStopover(stopover: String) {
        dataSource.saveStopover(stopover)
    }

    override fun getStopover(): Flow<String> {
        return dataSource.getStopover()
    }

    override suspend fun saveSearchTextFieldVisible(visibility: Boolean) {
        dataSource.saveSearchTextFieldVisible(visibility)
    }

    override fun getSearchTextFieldVisible(): Flow<Boolean> {
        return dataSource.getSearchTextFieldVisible()
    }

    override suspend fun saveStopoverTextFieldVisible(visibility: Boolean) {
        dataSource.saveStopoverTextFieldVisible(visibility)
    }

    override fun getStopoverTextFieldVisible(): Flow<Boolean> {
        return dataSource.getStopoverTextFieldVisible()
    }

    override suspend fun saveAll(
        buttonText: String,
        startTime: Long,
        start: String,
        destination: String,
        stopover: String,
        searchTextFieldVisible: Boolean,
        stopoverTextFieldVisible: Boolean
    ) {
        dataSource.saveAll(
            buttonText,
            startTime,
            start,
            destination,
            stopover,
            searchTextFieldVisible,
            stopoverTextFieldVisible
        )
    }

    override suspend fun clear() {
        dataSource.clear()
    }
}