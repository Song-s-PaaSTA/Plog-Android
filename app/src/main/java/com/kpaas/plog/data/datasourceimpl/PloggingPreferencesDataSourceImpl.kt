package com.kpaas.plog.data.datasourceimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kpaas.plog.app.di.PloggingPreferences
import com.kpaas.plog.data.datasource.PloggingPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PloggingPreferencesDataSourceImpl @Inject constructor(
    @PloggingPreferences private val dataStore: DataStore<Preferences>
) : PloggingPreferencesDataSource {
    override suspend fun saveButtonText(text: String) {
        dataStore.edit { preferences ->
            preferences[BUTTON_TEXT] = text
        }
    }

    override fun getButtonText(): Flow<String> = dataStore.data.map { preferences ->
        preferences[BUTTON_TEXT] ?: "시작하기"
    }


    override suspend fun saveStartTime(startTime: Long) {
        dataStore.edit { preferences ->
            preferences[START_TIME] = startTime
        }
    }

    override fun getStartTime(): Flow<Long> = dataStore.data.map { preferences ->
        preferences[START_TIME] ?: 0L
    }

    override suspend fun saveStart(start: String) {
        dataStore.edit { preferences ->
            preferences[START] = start
        }
    }

    override fun getStart(): Flow<String> = dataStore.data.map { preferences ->
        preferences[START] ?: ""
    }

    override suspend fun saveDestination(destination: String) {
        dataStore.edit { preferences ->
            preferences[DESTINATION] = destination
        }
    }

    override fun getDestination(): Flow<String> = dataStore.data.map { preferences ->
        preferences[DESTINATION] ?: ""
    }

    override suspend fun saveStopover(stopover: String) {
        dataStore.edit { preferences ->
            preferences[STOPOVER] = stopover
        }
    }

    override fun getStopover(): Flow<String> = dataStore.data.map { preferences ->
        preferences[STOPOVER] ?: ""
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

    override suspend fun saveAll(
        buttonText: String,
        startTime: Long,
        start: String,
        destination: String,
        stopover: String,
        searchTextFieldVisible: Boolean,
        stopoverTextFieldVisible: Boolean
    ) {
        dataStore.edit { preferences ->
            preferences[BUTTON_TEXT] = buttonText
            preferences[START_TIME] = startTime
            preferences[START] = start
            preferences[DESTINATION] = destination
            preferences[STOPOVER] = stopover
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
        val SEARCH_TEXT_FIELD_VISIBLE = booleanPreferencesKey("search_text_field_visible")
        val STOPOVER_TEXT_FIELD_VISIBLE = booleanPreferencesKey("stopover_text_field_visible")
    }
}