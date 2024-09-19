package com.kpaas.plog.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.kpaas.plog.data.datasource.PloggingPreferencesDataSource
import com.kpaas.plog.data.datasourceimpl.PloggingPreferencesDataSourceImpl
import com.kpaas.plog.data.repositoryimpl.PloggingPreferencesRepositoryImpl
import com.kpaas.plog.domain.repository.PloggingPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PloggingPreferencesModule {
    private const val PLOGGING_PREFERENCES = "plogging_preferences"
    private val Context.ploggingDataStore by preferencesDataStore(name = PLOGGING_PREFERENCES)

    @Provides
    @Singleton
    @PloggingPreferences
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.ploggingDataStore

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Singleton
        @Binds
        fun provideDatastore(datastore: PloggingPreferencesDataSourceImpl): PloggingPreferencesDataSource
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsPloggingPreferencesRepository(RepositoryImpl: PloggingPreferencesRepositoryImpl): PloggingPreferencesRepository
    }
}