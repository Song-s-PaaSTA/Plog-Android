package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.PloggingPreferencesDataSource
import com.kpaas.plog.domain.entity.LatLngEntity
import com.kpaas.plog.domain.repository.PloggingPreferencesRepository
import com.kpaas.plog.util.Location
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

    override suspend fun saveStart(start: Location?) {
        dataSource.saveStart(start)
    }

    override fun getStart(): Flow<Location?> {
        return dataSource.getStart()
    }

    override suspend fun saveDestination(destination: Location?) {
        dataSource.saveDestination(destination)
    }

    override fun getDestination(): Flow<Location?> {
        return dataSource.getDestination()
    }

    override suspend fun saveStopover(stopover: Location?) {
        dataSource.saveStopover(stopover)
    }

    override fun getStopover(): Flow<Location?> {
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

    override suspend fun saveRoute(route: List<LatLngEntity>) {
        dataSource.saveRoute(route)
    }

    override fun getRoute(): Flow<List<LatLngEntity>> {
        return dataSource.getRoute()
    }

    override suspend fun saveAll(
        buttonText: String,
        startTime: Long,
        start: Location?,
        destination: Location?,
        stopover: Location?,
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