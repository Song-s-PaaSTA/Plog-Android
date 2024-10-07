package com.kpaas.plog.app.di

import com.kpaas.plog.data.datasource.PloggingDataSource
import com.kpaas.plog.data.datasourceimpl.PloggingDataSourceImpl
import com.kpaas.plog.data.repositoryimpl.PloggingRepositoryImpl
import com.kpaas.plog.data.service.PloggingApiService
import com.kpaas.plog.domain.repository.PloggingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PloggingModule {
    @Provides
    @Singleton
    fun providePloggingApiService(
        @PlogRetrofit retrofit: Retrofit,
    ): PloggingApiService = retrofit.create(PloggingApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsPloggingRepository(repositoryImpl: PloggingRepositoryImpl): PloggingRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Binds
        @Singleton
        fun providesPloggingDataSource(dataSourceImpl: PloggingDataSourceImpl): PloggingDataSource
    }
}