package com.kpaas.plog.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.kpaas.plog.data.datasource.UserPreferencesDataSource
import com.kpaas.plog.data.datasourceimpl.UserPreferencesDataSourceImpl
import com.kpaas.plog.data.repositoryimpl.UserPreferencesRepositoryImpl
import com.kpaas.plog.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserPreferencesModule {
    private const val PREFERENCE_NAME = "user_preferences"
    private val Context.dataStore by preferencesDataStore(name = PREFERENCE_NAME)

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.dataStore

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Singleton
        @Binds
        fun provideDatastore(datastore: UserPreferencesDataSourceImpl): UserPreferencesDataSource
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsUserPreferencesRepository(RepositoryImpl: UserPreferencesRepositoryImpl): UserPreferencesRepository
    }
}