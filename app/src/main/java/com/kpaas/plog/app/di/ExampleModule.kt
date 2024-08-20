package com.kpaas.plog.app.di

import com.kpaas.plog.data.datasource.ExampleDataSource
import com.kpaas.plog.data.datasourceimpl.ExampleDataSourceImpl
import com.kpaas.plog.data.repositoryimpl.ExampleRepositoryImpl
import com.kpaas.plog.data.service.ExampleApiService
import com.kpaas.plog.domain.repository.ExampleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExampleModule {
    @Provides
    @Singleton
    fun provideExampleService(
        @PlogRetrofit retrofit: Retrofit,
    ): ExampleApiService = retrofit.create(ExampleApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        @Singleton
        fun bindsExampleRepository(repositoryImpl: ExampleRepositoryImpl): ExampleRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Binds
        @Singleton
        fun providesExampleDataSource(dataSourceImpl: ExampleDataSourceImpl): ExampleDataSource
    }
}