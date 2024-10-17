package com.kpaas.plog.data.datasourceimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.kpaas.plog.app.di.PloggingPreferences
import com.kpaas.plog.data.datasource.PloggingPreferencesDataSource
import com.kpaas.plog.domain.entity.LatLngEntity
import com.kpaas.plog.util.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PloggingPreferencesDataSourceImpl @Inject constructor(
    @PloggingPreferences private val dataStore: DataStore<Preferences>
) : PloggingPreferencesDataSource {
    private val gson = Gson()

    override suspend fun saveButtonText(text: String) {
        dataStore.edit { preferences ->
            preferences[BUTTON_TEXT] = text
        }
    }

    override fun getButtonText(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[BUTTON_TEXT] ?: "경로 추천받기"
        }
    }

    override suspend fun saveStartTime(startTime: Long) {
        dataStore.edit { preferences ->
            preferences[START_TIME] = startTime
        }
    }

    override fun getStartTime(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[START_TIME] ?: 0L
        }
    }

    override suspend fun saveStart(start: Location?) {
        dataStore.edit { preferences ->
            preferences[START] = gson.toJson(start)
        }
    }

    override fun getStart(): Flow<Location?> {
        return dataStore.data.map { preferences ->
            preferences[START]?.let { gson.fromJson(it, Location::class.java) }
        }
    }

    override suspend fun saveDestination(destination: Location?) {
        dataStore.edit { preferences ->
            preferences[DESTINATION] = gson.toJson(destination)
        }
    }

    override fun getDestination(): Flow<Location?> {
        return dataStore.data.map { preferences ->
            preferences[DESTINATION]?.let { gson.fromJson(it, Location::class.java) }
        }
    }

    override suspend fun saveStopover(stopover: Location?) {
        dataStore.edit { preferences ->
            preferences[STOPOVER] = gson.toJson(stopover)
        }
    }

    override fun getStopover(): Flow<Location?> {
        return dataStore.data.map { preferences ->
            preferences[STOPOVER]?.let { gson.fromJson(it, Location::class.java) }
        }
    }

    override suspend fun saveSearchTextFieldVisible(visibility: Boolean) {
        dataStore.edit { preferences ->
            preferences[SEARCH_TEXT_FIELD_VISIBLE] = visibility
        }
    }

    override fun getSearchTextFieldVisible(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[SEARCH_TEXT_FIELD_VISIBLE] ?: true
        }
    }

    override suspend fun saveStopoverTextFieldVisible(visibility: Boolean) {
        dataStore.edit { preferences ->
            preferences[STOPOVER_TEXT_FIELD_VISIBLE] = visibility
        }
    }

    override fun getStopoverTextFieldVisible(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[STOPOVER_TEXT_FIELD_VISIBLE] ?: false
        }
    }

    override suspend fun saveRoute(route: List<LatLngEntity>) {
        dataStore.edit { preferences ->
            preferences[ROUTE] = gson.toJson(route)
        }
    }

    override fun getRoute(): Flow<List<LatLngEntity>> {
        return dataStore.data.map { preferences ->
            preferences[ROUTE]?.let { gson.fromJson(it, Array<LatLngEntity>::class.java).toList() } ?: emptyList()
        }
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
        dataStore.edit { preferences ->
            preferences[BUTTON_TEXT] = buttonText
            preferences[START_TIME] = startTime
            preferences[START] = gson.toJson(start)
            preferences[DESTINATION] = gson.toJson(destination)
            preferences[STOPOVER] = gson.toJson(stopover)
            preferences[SEARCH_TEXT_FIELD_VISIBLE] = searchTextFieldVisible
            preferences[STOPOVER_TEXT_FIELD_VISIBLE] = stopoverTextFieldVisible
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.remove(BUTTON_TEXT)
            preferences.remove(START_TIME)
            preferences.remove(START)
            preferences.remove(DESTINATION)
            preferences.remove(STOPOVER)
            preferences.remove(ROUTE)
            preferences.remove(SEARCH_TEXT_FIELD_VISIBLE)
            preferences.remove(STOPOVER_TEXT_FIELD_VISIBLE)
        }
    }

    companion object {
        val BUTTON_TEXT = stringPreferencesKey("button_text")
        val START_TIME = longPreferencesKey("start_time")
        val START = stringPreferencesKey("start")
        val DESTINATION = stringPreferencesKey("destination")
        val STOPOVER = stringPreferencesKey("stopover")
        val ROUTE = stringPreferencesKey("route")
        val SEARCH_TEXT_FIELD_VISIBLE = booleanPreferencesKey("search_text_field_visible")
        val STOPOVER_TEXT_FIELD_VISIBLE = booleanPreferencesKey("stopover_text_field_visible")
    }
}