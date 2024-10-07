package com.kpaas.plog.app.di

import com.kpaas.plog.data.datasource.SearchDataSource
import com.kpaas.plog.data.datasourceimpl.SearchDataSourceImpl
import com.kpaas.plog.data.repositoryimpl.SearchRepositoryImpl
import com.kpaas.plog.data.service.SearchApiService
import com.kpaas.plog.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Provides
    @Singleton
    fun provideSearchApiService(
        @PlogRetrofit retrofit: Retrofit,
    ): SearchApiService = retrofit.create(SearchApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsSearchRepository(repositoryImpl: SearchRepositoryImpl): SearchRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Binds
        @Singleton
        fun providesSearchDataSource(dataSourceImpl: SearchDataSourceImpl): SearchDataSource
    }
}